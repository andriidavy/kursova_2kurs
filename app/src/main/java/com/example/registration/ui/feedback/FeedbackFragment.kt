package com.example.registration.ui.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentFeedbackBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedbackFragment : Fragment() {
    private lateinit var binding: FragmentFeedbackBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<FeedbackViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btSend.setOnClickListener {
            val subject = etSubject.text.toString()
            val message = editTextTextMultiLine.text.toString()
            val checkedId = rgMessageType.checkedRadioButtonId

            when (checkedId) {
                R.id.rb_mistake -> {
                    val finalSubject = "[wrong] $subject"
                    if (subject.isNotBlank() && message.isNotBlank()) {
                        lifecycleScope.launch {
                            viewModel.sendEmail(finalSubject, message).collect { result ->
                                result.onSuccess { ToastObj.shortToastMake("Відправлено", context) }
                                result.onFailure { ToastObj.shortToastMake("Помилка", context) }
                            }
                        }
                    }
                }

                R.id.rb_advice -> {
                    val finalSubject = "[advice] $subject"
                    if (subject.isNotBlank() && message.isNotBlank()) {
                        lifecycleScope.launch {
                            viewModel.sendEmail(finalSubject, message).collect { result ->
                                result.onSuccess { ToastObj.shortToastMake("Відправлено", context) }
                                result.onFailure { ToastObj.shortToastMake("Помилка", context) }
                            }
                        }
                    }
                }
            }

        }
    }
}