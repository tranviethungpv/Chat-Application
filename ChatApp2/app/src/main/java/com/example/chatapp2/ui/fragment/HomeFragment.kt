package com.example.chatapp2.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp1.Conversation
import com.example.chatapp2.ui.viewmodel.ConversationViewModel
import com.example.chatapp2.ui.viewmodel.UserViewModel
import com.example.chatapp2.databinding.FragmentHomeBinding
import com.example.chatapp2.ui.adapter.ConversationAdapter
import com.example.chatapp2.ui.adapter.UserHomeFragmentAdapter
import com.example.chatapp2.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userAdapter: UserHomeFragmentAdapter
    private lateinit var conversationAdapter: ConversationAdapter
    private val userViewModel: UserViewModel by viewModels()
    private val conversationViewModel: ConversationViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogout.setOnClickListener {
            sessionManager.clearLoginSession()
            findNavController().navigateUp()
        }

        fillUserInformation()
        userViewModel.getUsers()
        sessionManager.getUserInfo()
            ?.let { conversationViewModel.getMessageWithReceiverAndConversationInfo(it.id) }
        setupRecyclerView()
        observerListFriendsData()
        observerConversationsData()
    }

    override fun onStart() {
        super.onStart()
        sessionManager.getUserInfo()
            ?.let { conversationViewModel.getMessageWithReceiverAndConversationInfo(it.id) }
        conversationViewModel.conversationWithUsersAndLatestMessage.observe(viewLifecycleOwner) {
            conversationAdapter.submitList(it)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun fillUserInformation() {
        sessionManager.getUserInfo()?.let {
            binding.txtUsername.text = it.name

//            val resId = context?.resources?.getIdentifier(
//                it.avatar, "drawable", context?.packageName
//            )
//            Glide.with(this).load(resId).into(binding.imageMemojiBoysFortyOne)
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserHomeFragmentAdapter(onItemClick = { user ->
            conversationViewModel.getConversation(sessionManager.getUserInfo()!!.id, user.id)
                .observe(viewLifecycleOwner) { conversation ->
                    if (conversation != null) {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToMessageFragment(conversation)
                        )
                    } else {
                        lifecycleScope.launch {
                            val isSuccess = conversationViewModel.insertConversation(
                                Conversation(
                                    0,
                                    userId1 = sessionManager.getUserInfo()!!.id,
                                    userId2 = user.id
                                )
                            )
                            if (isSuccess) {
                                conversationViewModel.getConversation(
                                    sessionManager.getUserInfo()!!.id, user.id
                                ).observe(viewLifecycleOwner) { conversation ->
                                    if (conversation != null) {
                                        findNavController().navigate(
                                            HomeFragmentDirections.actionHomeFragmentToMessageFragment(
                                                conversation
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
        })

        conversationAdapter =
            ConversationAdapter(userId = sessionManager.getUserInfo()!!.id, onItemClick = {
                conversationViewModel.getConversation(
                    sessionManager.getUserInfo()!!.id, it.receiver.id
                ).observe(viewLifecycleOwner) { conversation ->
                    if (conversation != null) {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToMessageFragment(
                                conversation
                            )
                        )
                    } else {
                        lifecycleScope.launch {
                            val isSuccess = conversationViewModel.insertConversation(
                                Conversation(
                                    0,
                                    userId1 = sessionManager.getUserInfo()!!.id,
                                    userId2 = it.receiver.id
                                )
                            )
                            if (isSuccess) {
                                conversationViewModel.getConversation(
                                    sessionManager.getUserInfo()!!.id, it.receiver.id
                                ).observe(viewLifecycleOwner) { conversation ->
                                    if (conversation != null) {
                                        findNavController().navigate(
                                            HomeFragmentDirections.actionHomeFragmentToMessageFragment(
                                                conversation
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }, onLongItemClick = { conversationWithUserAndLatestMessage ->
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete")
                builder.setMessage("Are you sure to delete this conversation?")
                builder.setPositiveButton("OK") { dialog, _ ->
                    lifecycleScope.launch {
                        val isSuccess = conversationViewModel.deleteConversation(
                            conversationWithUserAndLatestMessage.conversation
                        )
                        if (isSuccess) {
                            sessionManager.getUserInfo()?.let {
                                conversationViewModel.getMessageWithReceiverAndConversationInfo(
                                    it.id
                                )
                            }
                            conversationViewModel.conversationWithUsersAndLatestMessage.observe(
                                viewLifecycleOwner
                            ) {
                                conversationAdapter.submitList(it)
                            }
                        }
                    }
                    dialog.dismiss()
                }
                builder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            })

        binding.rcvUsers.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = userAdapter
        }

        binding.rcvConversations.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = conversationAdapter
        }
    }

    private fun observerListFriendsData() {
        userViewModel.users.observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }
    }

    private fun observerConversationsData() {
        conversationViewModel.conversationWithUsersAndLatestMessage.observe(viewLifecycleOwner) {
            conversationAdapter.submitList(it)
        }
    }
}