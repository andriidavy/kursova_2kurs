//package com.example.registration.ui.manager.products.scanning
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.OptIn
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ExperimentalGetImage
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageProxy
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.core.content.ContextCompat
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
//import com.example.registration.R
//import com.example.registration.databinding.FragmentBarcodeScannerBinding
//import com.google.mlkit.vision.barcode.BarcodeScanning
//import com.google.mlkit.vision.common.InputImage
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class BarcodeScannerFragment : Fragment() {
//
//    private lateinit var binding: FragmentBarcodeScannerBinding
//    private lateinit var navController: NavController
//    private val barcodeScanner = BarcodeScanning.getClient()
//    private var isScanned = false
//
//    private val REQUEST_CAMERA_PERMISSION = 10
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        navController = findNavController()
//
//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            startCamera()
//        } else {
//            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startCamera()
//        } else {
//            // TODO: показать сообщение, что разрешение обязательно
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        isScanned = false
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
//
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also {
//                it.surfaceProvider = binding.viewFinder.surfaceProvider
//            }
//
//            val imageAnalysis = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//
//            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext())) { imageProxy ->
//                processImageProxy(imageProxy)
//            }
//
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageAnalysis)
//            } catch (exc: Exception) {
//                Log.e("BarcodeScannerFragment", "Binding camera use cases failed", exc)
//            }
//
//        }, ContextCompat.getMainExecutor(requireContext()))
//    }
//
//    @OptIn(ExperimentalGetImage::class)
//    private fun processImageProxy(imageProxy: ImageProxy) {
//        imageProxy.image?.let { mediaImage ->
//            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
//            barcodeScanner.process(image)
//                .addOnSuccessListener { barcodes ->
//                    for (barcode in barcodes) {
//                        barcode.rawValue?.let { scannedValue ->
//                            sendBarcodeResult(scannedValue)
//                            return@addOnSuccessListener
//                        }
//                    }
//                }
//                .addOnFailureListener {
//                    Log.e("BarcodeScannerFragment", "Barcode scanning failed", it)
//                }
//                .addOnCompleteListener {
//                    imageProxy.close()
//                }
//        } ?: imageProxy.close()
//    }
//
//    private fun sendBarcodeResult(barcode: String) {
//        if (isScanned) return
//        isScanned = true
//
//        val bundle = Bundle().apply { putString("barcode_result", barcode) }
//        navController.navigate(R.id.action_barcodeScannerFragment_to_allProductListFragment, bundle)
//    }
//}