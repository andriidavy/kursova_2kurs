package com.example.registration.fragment.employeeFragments.customsProcessed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentCustomProcessedProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO

class CustomProcessedProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentCustomProcessedProductDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomProcessedProductDetailBinding.inflate(inflater)

        adapter = CustomProductAdapter(emptyList())
        binding.CustomsProcessedProductDetailRecyclerView.adapter = adapter
        binding.CustomsProcessedProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("customProcessedProductList")

        val customProductDTOListLiveData: MutableLiveData<List<CustomProductDTO>> =
            MutableLiveData()
        customProductDTOListLiveData.value = customProductDTOList?.toList()

        customProductDTOListLiveData.observe(viewLifecycleOwner) { customProducts ->
            adapter.updateCustomProducts(customProducts)
        }


        return binding.root
    }

}