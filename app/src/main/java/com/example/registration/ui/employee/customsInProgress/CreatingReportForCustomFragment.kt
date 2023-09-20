package com.example.registration.ui.employee.customsInProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.databinding.FragmentCreatingReportForCustomBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatingReportForCustomFragment : Fragment() {

    private lateinit var binding: FragmentCreatingReportForCustomBinding
    private val viewModel by viewModels<CreatingReportForCustomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatingReportForCustomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() = with(binding) {
        buttonSendReport.setOnClickListener {
            val customId: Int? = arguments?.getInt("customInProgressId")
            reportId.text = customId.toString()

            customId?.let { id ->
                val reportText: String = reportText.text.toString()

                lifecycleScope.launch {
                    viewModel.createReport(id, reportText).collect { result ->
                        result.onSuccess {
                            ToastObj.shortToastMake("створено звіт для замовлення № $id", context)
                            buttonSendReport.visibility = View.GONE
                        }
                        result.onFailure {
                            ToastObj.shortToastMake("помилка: $it", context)
                        }
                    }
                }
            }
        }
    }
}