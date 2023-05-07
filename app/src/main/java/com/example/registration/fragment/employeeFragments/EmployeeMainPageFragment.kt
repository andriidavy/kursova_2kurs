package com.example.registration.fragment.employeeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.registration.R
import com.example.registration.databinding.FragmentEmployeeMainPageBinding


class EmployeeMainPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEmployeeMainPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_main_page, container, false)
        return binding.root
    }


}