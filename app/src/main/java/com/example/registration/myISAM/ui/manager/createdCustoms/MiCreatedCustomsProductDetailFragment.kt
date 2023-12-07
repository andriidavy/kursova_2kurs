package com.example.registration.myISAM.ui.manager.createdCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentCreatedCustomsProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO


class MiCreatedCustomsProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentCreatedCustomsProductDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatedCustomsProductDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() = with(binding) {
        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("createdCustomProductList")
        customProductDTOList?.let {
            adapter = CustomProductAdapter(it)
        }
        createdCustomsProductDetailRecyclerView.adapter = adapter
        createdCustomsProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)
    }
}