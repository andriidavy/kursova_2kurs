package com.example.registration.fragment.managerFragments.createdCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentCreatedCustomsProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO


class CreatedCustomsProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentCreatedCustomsProductDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatedCustomsProductDetailBinding.inflate(inflater)

        adapter = CustomProductAdapter(emptyList())
        binding.createdCustomsProductDetailRecyclerView.adapter = adapter
        binding.createdCustomsProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("createdCustomProductList")

        val customProductDTOListLiveData: MutableLiveData<List<CustomProductDTO>> = MutableLiveData()
        customProductDTOListLiveData.value = customProductDTOList?.toList()

        customProductDTOListLiveData.observe(viewLifecycleOwner) { createdCustomProducts ->
            adapter.updateCustomProducts(createdCustomProducts)
        }


        return binding.root
    }

}