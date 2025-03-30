package com.example.registration.ui.manager.allCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.MessagingAdapter
import com.example.registration.databinding.FragmentCustomerCustomChattingBinding
import com.example.registration.databinding.FragmentManagerCustomChattingBinding
import com.example.registration.ui.customer.custom.CustomerCustomChattingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagerCustomChattingFragment : Fragment() {
    private lateinit var binding: FragmentManagerCustomChattingBinding
    private lateinit var adapter: MessagingAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerCustomChattingViewModel>()

    private var customId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerCustomChattingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = MessagingAdapter(mutableListOf(), "MANAGER", messageListRecyclerView)
        messageListRecyclerView.adapter = adapter
        messageListRecyclerView.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()

        customId = arguments?.getInt("customId")
        customId?.let {
            viewModel.getMessageForCustom(it)
            viewModel.pollMessages(it)
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messageDTOArray.collect { messages ->
                    adapter.updateMessages(messages)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        btMessageSend.setOnClickListener {
            val message = etMessageField.text.toString()
            if (message.isNotBlank() && customId != null) {
                viewModel.sendMessageByManager(customId!!, message)
                etMessageField.text.clear()
            }
        }

        closeChat.setOnClickListener {
        if (customId != null)
            viewModel.closeChat(customId!!)
            navController.navigate(
                R.id.action_managerCustomChattingFragment_to_managerAllCustomsFragment
            )
        }
    }
}