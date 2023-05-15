package com.example.registration.fragment.customerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.registration.R
import com.example.registration.databinding.FragmentProductItemBinding

class ProductItemFragment : Fragment() {
    private lateinit var binding: FragmentProductItemBinding
//    companion object{
//        fun getNewInstance(args:Bundle?): ProductItemFragment{
//            val productItemFragment = ProductItemFragment()
//            productItemFragment.arguments = args
//            return productItemFragment
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProductItemBinding.inflate(inflater)
        binding.productName.text = arguments?.getString("name_product")
        binding.productDescription.text = arguments?.getString("description_product")
        binding.productId.text = arguments?.getInt("id_product").toString()
        binding.productQuantity.text = arguments?.getInt("quantity_product").toString()

        return binding.root
    }

}