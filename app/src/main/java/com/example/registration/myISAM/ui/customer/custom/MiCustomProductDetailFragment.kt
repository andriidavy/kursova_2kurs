package com.example.registration.myISAM.ui.customer.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentCustomProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiCustomProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentCustomProductDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomProductDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() = with(binding){
        adapter = CustomProductAdapter(emptyList())
        customProductDetailRecyclerView.adapter = adapter
        customProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        val customProductDTOList: List<CustomProductDTO> =
            requireArguments().getParcelableArrayList("customProductList")
                ?: emptyList()

        adapter.updateCustomProducts(customProductDTOList)
    }
}