package com.example.registration.myISAM.ui.manager.reportsInWaiting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.registration.databinding.FragmentManagerReportsInWaitingDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiManagerReportsInWaitingDetailFragment : Fragment() {

    private lateinit var binding: FragmentManagerReportsInWaitingDetailBinding
    private val viewModel by viewModels<MiManagerReportsInWaitingDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerReportsInWaitingDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() = with(binding) {
        reportText.text = arguments?.getString("reportText")
    }

    private fun setListeners() = with(binding) {
        val reportId: Int? = arguments?.getInt("reportId")

        buttonAcceptReport.setOnClickListener {
            reportId?.let { id ->
                viewModel.setReportAccepted(id)
                buttonAcceptReport.visibility = View.GONE
                buttonRejectReport.visibility = View.GONE
                Toast.makeText(context, "Звіт прийнято!", Toast.LENGTH_LONG).show()
            }
        }

        buttonRejectReport.setOnClickListener {
            reportId?.let { id ->
                viewModel.setReportRejected(id)
                buttonAcceptReport.visibility = View.GONE
                buttonRejectReport.visibility = View.GONE
                Toast.makeText(context, "Звіт відхилено!", Toast.LENGTH_LONG).show()
            }
        }
    }
}