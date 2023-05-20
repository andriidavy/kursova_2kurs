package com.example.registration.fragment.employeeFragments.customsInProgress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentEmployeeCustomInProgressBinding
import com.example.registration.model.custom.CustomProductDTO


class CustomInProgressProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeCustomInProgressBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeCustomInProgressBinding.inflate(inflater)

        adapter = CustomProductAdapter(emptyList())
        binding.employeeCustomInProgressRecyclerView.adapter = adapter
        binding.employeeCustomInProgressRecyclerView.layoutManager = LinearLayoutManager(activity)

        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("customInProgressProductList")

        val customProductDTOListLiveData: MutableLiveData<List<CustomProductDTO>> = MutableLiveData()
        customProductDTOListLiveData.value = customProductDTOList?.toList()

        customProductDTOListLiveData.observe(viewLifecycleOwner) { customProducts ->
            adapter.updateCustomProducts(customProducts)
        }


        return binding.root
    }
}