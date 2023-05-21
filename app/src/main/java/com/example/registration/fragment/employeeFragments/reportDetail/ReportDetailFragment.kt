package com.example.registration.fragment.employeeFragments.reportDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.registration.databinding.FragmentReportDetailBinding
import com.example.registration.databinding.ListReportItemBinding
import com.example.registration.model.custom.CustomProductDTO

class ReportDetailFragment : Fragment() {
    private lateinit var binding: FragmentReportDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportDetailBinding.inflate(inflater)

        val reportText: String? =
            arguments?.getString("reportText")

        binding.reportText.text = reportText

        return binding.root
    }

}