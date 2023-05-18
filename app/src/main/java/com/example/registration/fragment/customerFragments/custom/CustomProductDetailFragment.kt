package com.example.registration.fragment.customerFragments.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.custom.CustomAdapter
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentCustomProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO


class CustomProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentCustomProductDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomProductDetailBinding.inflate(inflater)

        adapter = CustomProductAdapter(emptyList())
        binding.customProductDetailRecyclerView.adapter = adapter
        binding.customProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        binding.lifecycleOwner = this

        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("customProductList")

        val customProductDTOListLiveData: MutableLiveData<List<CustomProductDTO>> = MutableLiveData()
        customProductDTOListLiveData.value = customProductDTOList?.toList()

        customProductDTOListLiveData.observe(viewLifecycleOwner) { customProducts ->
            adapter.updateCustomProducts(customProducts)
        }

        return binding.root
    }

}