package com.example.registration.fragment.managerFragments.allCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentAllCustomsDetailBinding
import com.example.registration.databinding.FragmentCreatedCustomsProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO

class AllCustomsDetailFragment : Fragment() {
    private lateinit var binding: FragmentAllCustomsDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCustomsDetailBinding.inflate(inflater)

        adapter = CustomProductAdapter(emptyList())
        binding.allCustomsProductDetailRecyclerView.adapter = adapter
        binding.allCustomsProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("allCustomProductList")

        val customProductDTOListLiveData: MutableLiveData<List<CustomProductDTO>> = MutableLiveData()
        customProductDTOListLiveData.value = customProductDTOList?.toList()

        customProductDTOListLiveData.observe(viewLifecycleOwner) { createdCustomProducts ->
            adapter.updateCustomProducts(createdCustomProducts)
        }


        return binding.root
    }
}