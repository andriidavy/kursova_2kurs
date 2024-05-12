package com.example.registration.ui.myPlaces.listOfPlaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.PlacesListAdapter
import com.example.registration.adapter.ServerFilesListAdapter
import com.example.registration.databinding.FragmentListOfPlacesBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.directoryItem.ServerItem
import com.example.registration.model.places.PlaceItem


class ListOfPlacesFragment : Fragment() {
    private lateinit var binding: FragmentListOfPlacesBinding
    private lateinit var adapter: PlacesListAdapter
    private lateinit var navController: NavController
    private lateinit var placesList: List<PlaceItem>
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

    private fun setupViews() = with(binding){
        adapter = PlacesListAdapter(
            emptyList(),
            itemRemovedClick(),
            itemClick()
        )
        rvMyPlaces.adapter = adapter
        rvMyPlaces.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
    }

    private fun itemRemovedClick(): (Int) -> Unit {
        return { position ->
            placesList.getOrNull(position)?.url?.let { url ->
                viewModel.deleteFile(url)
                itemListUpdate(pathToFolder)
                ToastObj.shortToastMake("Файл видалено!", context)
            }
        }
    }

    private fun itemClick(): (Int) -> Unit = with(binding) {
        return { position ->
            placesList.getOrNull(position)?.name?.let { name ->
                val newPath = if (pathToFolder.isNotEmpty()) {
                    "$pathToFolder/$name"
                } else {
                    name
                }
                etWayToFolder.setText(newPath)
                refreshPath()
                itemListUpdate(pathToFolder)
            }
        }
    }

}