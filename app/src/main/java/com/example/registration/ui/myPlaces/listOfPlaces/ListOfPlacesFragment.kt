package com.example.registration.ui.myPlaces.listOfPlaces

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.PlacesListAdapter
import com.example.registration.databinding.FragmentListOfPlacesBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.model.places.PlaceItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListOfPlacesFragment : Fragment() {
    private lateinit var binding: FragmentListOfPlacesBinding
    private lateinit var adapter: PlacesListAdapter
    private lateinit var navController: NavController
    private lateinit var placesList: List<PlaceItem>
    private val viewModel by viewModels<ListOfPlacesViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfPlacesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = PlacesListAdapter(
            emptyList(),
            itemRemovedClick(),
            itemLikeClick(),
            itemCancelLikeClick(),
            itemClick(),
            itemImageClick(),
            dataStoreViewModel.getUser()?.name ?: ""
        )
        rvMyPlaces.adapter = adapter
        rvMyPlaces.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
        placesListUpdate()
    }

    private fun setListeners() = with(binding) {
        btSearch.setOnClickListener {
            val searchLine = etSearch.text.toString()
            val checkedId = rgSearchType.checkedRadioButtonId

            when (checkedId) {
                R.id.rb_search_by_desc -> {
                    if (searchLine.isBlank()) {
                        placesListUpdate()
                    } else {
                        placesListByDescription(searchLine)
                    }
                }

                R.id.rb_search_by_tag -> {
                    if (searchLine.isBlank()) {
                        placesListUpdate()
                    } else {
                        placesListByTag(searchLine)
                    }
                }

                R.id.rb_search_by_distance -> {
                    if (searchLine.isBlank()) {
                        placesListUpdate()
                    } else {
                        placesListByDistance(searchLine)
                    }
                }
            }
        }
    }

    private fun placesListUpdate() {
        lifecycleScope.launch {
            viewModel.getAllPlaces().collect { result ->
                result.onSuccess { listOfPlaces ->
                    placesList = listOfPlaces
                    adapter.updateCart(placesList)
                }
                result.onFailure {
                    ToastObj.longToastMake("Помилка оновлення списку місць", context)
                }
            }
        }
    }

    private fun placesListByDescription(searchLine: String) {
        lifecycleScope.launch {
            viewModel.getPlacesByDescription(searchLine).collect { result ->
                result.onSuccess { listOfPlaces ->
                    placesList = listOfPlaces
                    adapter.updateCart(placesList)
                }
                result.onFailure {
                    ToastObj.longToastMake("Помилка оновлення списку місць", context)
                }
            }
        }
    }

    private fun placesListByTag(searchLine: String) {
        lifecycleScope.launch {
            viewModel.getPlacesByTag(searchLine).collect { result ->
                result.onSuccess { listOfPlaces ->
                    placesList = listOfPlaces
                    adapter.updateCart(placesList)
                }
                result.onFailure {
                    ToastObj.longToastMake("Помилка оновлення списку місць", context)
                }
            }
        }
    }

    private fun placesListByDistance(searchLine: String) {
        lifecycleScope.launch {
            viewModel.getPlacesByDistance(searchLine).collect { result ->
                result.onSuccess { listOfPlaces ->
                    placesList = listOfPlaces
                    adapter.updateCart(placesList)
                }
                result.onFailure {
                    ToastObj.longToastMake("Помилка оновлення списку місць", context)
                }
            }
        }
    }

    private fun itemLikeClick(): (Int) -> Unit {
        return { position ->
            placesList.getOrNull(position)?.objectId?.let { id ->
                lifecycleScope.launch {
                    viewModel.addLikeToPlace(id).collect { result ->
                        result.onSuccess { ToastObj.shortToastMake("Додано в улюблені", context) }
                        result.onFailure {
                            ToastObj.shortToastMake(
                                "Помилка додання в улюблені",
                                context
                            )
                        }
                    }
                }
            }
        }
    }

    private fun itemCancelLikeClick(): (Int) -> Unit {
        return { position ->
            placesList.getOrNull(position)?.likeId?.let { id ->
                lifecycleScope.launch {
                    viewModel.deleteLikeForPlace(id).collect { result ->
                        result.onSuccess {
                            ToastObj.shortToastMake(
                                "Видалено з улюблених",
                                context
                            )
                        }
                        result.onFailure {
                            ToastObj.shortToastMake(
                                "Помилка видалення з улюблених",
                                context
                            )
                        }
                    }
                }
            }
        }
    }

    private fun itemRemovedClick(): (Int) -> Unit {
        return { position ->
            placesList.getOrNull(position)?.objectId?.let { id ->
                lifecycleScope.launch {
                    viewModel.deletePlace(id).collect { result ->
                        result.onSuccess {
                            placesListUpdate()
                            ToastObj.longToastMake("Місце видалено!", context)
                        }
                        result.onFailure {
                            ToastObj.longToastMake("Помилка видалення місця!", context)
                        }
                    }
                }
            }
        }
    }

    private fun itemClick(): (Int) -> Unit = with(binding) {
        return { position ->
            ToastObj.shortToastMake("position: $position", context)
            placesList.getOrNull(position)?.let { item ->
                val bundle = Bundle()
                bundle.putSerializable("place", item) // replace here
                navController.navigate(R.id.action_listOfPlacesFragment_to_mapsFragment, bundle)
            }
        }
    }

    private fun itemImageClick(): (Int) -> Unit = with(binding) {
        return { position ->
            placesList.getOrNull(position)?.placePhotoUrl?.let { photoUrl ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(photoUrl))
                startActivity(intent)
            }
        }
    }
}