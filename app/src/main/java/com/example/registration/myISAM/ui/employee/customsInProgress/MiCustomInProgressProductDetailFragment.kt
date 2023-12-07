package com.example.registration.myISAM.ui.employee.customsInProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentEmployeeCustomInProgressBinding
import com.example.registration.model.custom.CustomProductDTO

class MiCustomInProgressProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeCustomInProgressBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeCustomInProgressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() = with(binding) {
        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("customInProgressProductList")

        customProductDTOList?.let { adapter = CustomProductAdapter(it) }
        employeeCustomInProgressRecyclerView.adapter = adapter
        employeeCustomInProgressRecyclerView.layoutManager = LinearLayoutManager(activity)
    }
}