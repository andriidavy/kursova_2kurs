package com.example.registration.ui.manager.adminMode.departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.databinding.FragmentAddDepartBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddDepartFragment : Fragment() {

    private lateinit var binding: FragmentAddDepartBinding
    private val viewModel by viewModels<AddDepartsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddDepartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() = with(binding) {
        buttonAdd.setOnClickListener {
            val departmentName = etName.text.toString()
            if (departmentName.isNotBlank()) {
                lifecycleScope.launch {
                    viewModel.saveDepart(departmentName).collect { result ->
                        result.onSuccess {
                            etName.text.clear()
                            ToastObj.shortToastMake("Відділ додано успішно!", context)
                        }
                        result.onFailure {
                            ToastObj.shortToastMake("Відділ з такою назвою вже існує!", context)
                        }
                    }
                }
            } else {
                ToastObj.shortToastMake("Всі поля мають бути заповнені!", context)
            }
        }
    }
}