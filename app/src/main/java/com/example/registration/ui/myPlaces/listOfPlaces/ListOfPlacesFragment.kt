package com.example.registration.ui.myPlaces.listOfPlaces

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
import com.example.registration.adapter.PlacesListAdapter
import com.example.registration.adapter.ServerFilesListAdapter
import com.example.registration.databinding.FragmentListOfPlacesBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.directoryItem.ServerItem
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
    }

    private fun setupViews() = with(binding) {
        adapter = PlacesListAdapter(
            emptyList(),
            itemRemovedClick(),
            itemClick()
        )
        rvMyPlaces.adapter = adapter
        rvMyPlaces.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
        placesListUpdate()
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
//            placesList.getOrNull(position)?.name?.let { name ->
//                val newPath = if (pathToFolder.isNotEmpty()) {
//                    "$pathToFolder/$name"
//                } else {
//                    name
//                }
//                etWayToFolder.setText(newPath)
//                refreshPath()
//                itemListUpdate(pathToFolder)
//            }
//        }
        }
    }
}