package com.example.registration.myISAM.ui.employee.reportDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.registration.databinding.FragmentReportDetailBinding

class MiReportDetailFragment : Fragment() {

    private lateinit var binding: FragmentReportDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() = with(binding) {
        val repText: String? =
            arguments?.getString("reportText")

        reportText.text = repText
    }
}