package com.example.registration.ui.customer.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.customProductDetailRecyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(activity)
        }

        @Suppress("DEPRECATION")
        val customProductDTOList: List<CustomProductDTO> =
            requireArguments().getParcelableArrayList("customProductList")
                ?: emptyList()

        adapter.updateCustomProducts(customProductDTOList)

        return binding.root
    }

}