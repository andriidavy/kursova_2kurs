package com.example.registration.ui.manager.products

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentAllProductListBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.product.ProductDTO
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductListFragment : Fragment() {

    private lateinit var binding: FragmentAllProductListBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<AllProductListViewModel>()
    private var searchStr = ""
    private var skipNextRefresh = false

    private val CAMERA_REQUEST_CODE = 101
    private val CAMERA_PERMISSION_REQUEST_CODE = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllProductListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        if (skipNextRefresh) {
            skipNextRefresh = false // сбрасываем после одного пропуска
            return
        }

        lifecycleScope.launch {
            delay(300)
            viewModel.getAllProductsPage(0)
            viewModel.currentPage = 0
            updatePageInfo(viewModel.currentPage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageBitmap?.let {
                scanBarcodeFromBitmap(it)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                ToastObj.longToastMake("Дозвіл на використання камери відхилено", context)
            }
        }
    }

    private fun setViews() = with(binding) {
        adapter = ProductAdapter(emptyList(), onItemClick())
        allProductListRecyclerView.adapter = adapter
        allProductListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
        etSearchProductField.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) {
                searchStr = text.toString()
            } else {
                searchStr = ""
            }
        }
        updatePageInfo(viewModel.currentPage)
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDTOArray.collect { products ->
                    adapter.updateProducts(products)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonAddProduct.setOnClickListener {
            navController.navigate(R.id.action_allProductListFragment_to_addProductFragment)
        }

        buttonSearchProduct.setOnClickListener {
            val searchStr: String = searchStr
            when {
                searchStr.isBlank() -> {
                    viewModel.getAllProductsPage(0)
                }
                searchStr.matches(Regex("^\\d+$")) -> {
                    viewModel.getProductsById(searchStr.toInt())
                }
                else -> {
                    viewModel.getProductsBySearch(searchStr, 0, 0)
                }
            }
            viewModel.currentPage = 0
            updatePageInfo(viewModel.currentPage)
        }

        btNext.setOnClickListener {
            if (searchStr.isBlank()) {
                if (viewModel.isLastPage()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPage()
                    updatePageInfo(viewModel.currentPage)
                }
            } else {
                if (viewModel.isLastPage()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPageBySearch()
                    updatePageInfo(viewModel.currentPage)
                }
            }
        }
        btPrevious.setOnClickListener {
            if (searchStr.isBlank()) {
                viewModel.loadPreviousPage()
                updatePageInfo(viewModel.currentPage)
            }else {
                viewModel.loadPreviousPageBySearch()
                updatePageInfo(viewModel.currentPage)
            }
        }

        buttonScanningProduct.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun scanBarcodeFromBitmap(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    val qrCode = barcodes.firstOrNull { it.format == com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE }

                    if (qrCode != null) {
                        val rawValue = qrCode.rawValue
                        binding.etSearchProductField.setText(rawValue ?: "")
                        rawValue?.let {
                            // Пример: если вы ожидаете числовой ID в QR-коде
                            if (it.matches(Regex("^\\d+$"))) {
                                try {
                                    skipNextRefresh = true
                                    viewModel.getProductsById(it.toInt())
                                } catch (e: NumberFormatException) {
                                    ToastObj.longToastMake("Занадто довге число: $it", context)
                                }
                            } else {
                                ToastObj.longToastMake("QR-код не містить коректний ID: $it", context)
                            }
                        }
                    } else {
                        ToastObj.longToastMake("Це не QR-код", context)
                    }
                } else {
                    ToastObj.longToastMake("QR-код не розпізнано", context)
                }
            }
            .addOnFailureListener {
                ToastObj.longToastMake("Помилка сканування", context)
            }
    }

    private fun updatePageInfo(page: Int) = with(binding) {
        val from = page * 10 + 1
        lifecycleScope.launch {
            delay(600)
            val to = from + viewModel.productDTOArray.value.size - 1
            tvPageInfo.text = "з $from по $to"
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val product: ProductDTO = viewModel.productDTOArray.value[position]
            bundle.putParcelable("updProduct", product)
            navController.navigate(
                R.id.action_allProductListFragment_to_updateProductFragment,
                bundle
            )
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } else {
            ToastObj.longToastMake("Камера недоступна", context)
        }
    }
}