package com.example.chatapp1.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp1.R
import com.example.chatapp1.databinding.FragmentStartBinding
import com.example.chatapp1.ui.adapter.UserStartFragmentAdapter
import com.example.chatapp1.ui.viewmodel.UserViewModel
import com.example.chatapp1.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserStartFragmentAdapter

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launch {
//            val isSuccess = userViewModel.insertUser(
//                User(
//                    id = 0,
//                    name = "Trần Việt Hưng",
//                    avatar = "kim_ji_won"
//                )
//            )
//            if (isSuccess) {
//                userViewModel.getUsers()
//            }
//        }

        userViewModel.getUsers()
        setupRecyclerView()
        observerData()
    }

    private fun setupRecyclerView() {
        userAdapter = UserStartFragmentAdapter(onItemClick = {
            sessionManager.saveLoginSession(it)
            findNavController().navigate(R.id.action_startFragment_to_homeFragment)
        })
        binding.rcvListUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun observerData() {
        userViewModel.users.observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }
    }
}