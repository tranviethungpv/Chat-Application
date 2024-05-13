package com.example.chatapp2.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp1.Message
import com.example.chatapp2.R
import com.example.chatapp2.ui.viewmodel.ConversationViewModel
import com.example.chatapp2.ui.viewmodel.MessageViewModel
import com.example.chatapp2.ui.viewmodel.UserViewModel
import com.example.chatapp2.databinding.FragmentMessageBinding
import com.example.chatapp2.ui.adapter.MessageAdapter
import com.example.chatapp2.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private val messageViewModel: MessageViewModel by viewModels()
    private val conversationViewModel: ConversationViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    @Inject
    lateinit var sessionManager: SessionManager
    private val args: MessageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ibDelete.setOnClickListener {
            lifecycleScope.launch {
                val isSuccess = conversationViewModel.deleteConversation(args.conversation)
                if (isSuccess) {
                    findNavController().navigateUp()
                }
            }
        }

        binding.ivSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotEmpty()) {
                lifecycleScope.launch {
                    val isSuccess = messageViewModel.insertMessage(
                        Message(
                            id = 0,
                            conversationId = args.conversation.id,
                            senderId = sessionManager.getUserInfo()?.id ?: 0,
                            text = message,
                            timestamp = Instant.now().toEpochMilli()
                        )
                    )
                    if (isSuccess) {
                        messageViewModel.getMessagesWithUsersAndConversation(
                            args.conversation.id
                        ).observe(viewLifecycleOwner) { messages ->
                            messageAdapter.submitList(messages)
                            binding.rcvListMessages.scrollToPosition(messages.size - 1)
                        }
                    }
                }
                binding.etMessage.text.clear()
            } else {
                binding.etMessage.error = getString(R.string.message_empty_error)
            }
        }

        fillUserInformation()
        setupRecyclerView()

        messageViewModel.getMessagesWithUsersAndConversation(
            args.conversation.id
        ).observe(viewLifecycleOwner) { messages ->
            messageAdapter.submitList(messages)
            binding.rcvListMessages.scrollToPosition(messages.size - 1)
        }
    }

    override fun onStart() {
        super.onStart()
        messageViewModel.getMessagesWithUsersAndConversation(
            args.conversation.id
        ).observe(viewLifecycleOwner) { messages ->
            messageAdapter.submitList(messages)
            binding.rcvListMessages.scrollToPosition(messages.size - 1)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun fillUserInformation() {
        if (sessionManager.getUserInfo()!!.id == args.conversation.userId1) {
            userViewModel.getUserById(args.conversation.userId2).observe(viewLifecycleOwner) {
                binding.tvName.text = it.name
                val resId = context?.resources?.getIdentifier(
                    it.avatar, "drawable", context?.packageName
                )
                Glide.with(this).load(resId).into(binding.civAvatar)
            }
        } else {
            userViewModel.getUserById(args.conversation.userId1).observe(viewLifecycleOwner) {
                binding.tvName.text = it.name
                val resId = context?.resources?.getIdentifier(
                    it.avatar, "drawable", context?.packageName
                )
                Glide.with(this).load(resId).into(binding.civAvatar)
            }
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(sessionManager.getUserInfo()?.id ?: 0)
        binding.rcvListMessages.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = messageAdapter
        }
    }
}