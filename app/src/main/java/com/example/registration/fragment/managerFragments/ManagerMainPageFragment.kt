package com.example.registration.fragment.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.registration.R
import com.example.registration.databinding.FragmentEmployeeMainPageBinding
import com.example.registration.databinding.FragmentManagerMainPageBinding

class ManagerMainPageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentManagerMainPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_manager_main_page, container, false)
        return binding.root
    }

}