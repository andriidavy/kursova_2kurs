package com.example.registration.fragment.managerFragments.adminMode.departments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.registration.R
import com.example.registration.databinding.FragmentEditDepartmentBinding


class EditDepartmentFragment : Fragment() {
    private lateinit var binding: FragmentEditDepartmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditDepartmentBinding.inflate(inflater)



        return binding.root
    }

}