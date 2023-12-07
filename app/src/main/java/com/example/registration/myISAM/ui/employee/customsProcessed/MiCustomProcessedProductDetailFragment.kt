package com.example.registration.myISAM.ui.employee.customsProcessed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentCustomProcessedProductDetailBinding
import com.example.registration.model.custom.CustomProductDTO

class MiCustomProcessedProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentCustomProcessedProductDetailBinding
    private lateinit var adapter: CustomProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomProcessedProductDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() = with(binding) {
        val customProductDTOList: ArrayList<CustomProductDTO>? =
            arguments?.getParcelableArrayList("customProcessedProductList")

        customProductDTOList?.let { adapter = CustomProductAdapter(it) }
        CustomsProcessedProductDetailRecyclerView.adapter = adapter
        CustomsProcessedProductDetailRecyclerView.layoutManager = LinearLayoutManager(activity)
    }
}