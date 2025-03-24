package com.example.registration.ui.customer.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.custom.MessagingAdapter
import com.example.registration.databinding.FragmentCustomerCustomChattingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomerCustomChattingFragment : Fragment() {
    private lateinit var binding: FragmentCustomerCustomChattingBinding
    private lateinit var adapter: MessagingAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<CustomerCustomChattingViewModel>()
    val customId: Int? = arguments?.getInt("customId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerCustomChattingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = MessagingAdapter(emptyList(), "CUSTOMER")
        messageListRecyclerView.adapter = adapter
        messageListRecyclerView.layoutManager = LinearLayoutManager(activity)

        customId?.let { viewModel.getMessageForCustom(it) }
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
            if (message.isNotBlank()) {
                customId?.let { it1 -> viewModel.sendMessageByCustomer(it1, message) }
            }
        }
    }

}