package com.example.registration.ui.myFriends

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
import com.example.registration.adapter.AcceptedFriendsAdapter
import com.example.registration.adapter.PlacesListAdapter
import com.example.registration.databinding.FragmentMyFriendsBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.friends.FriendItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFriendsFragment : Fragment() {
    private lateinit var binding: FragmentMyFriendsBinding
    private lateinit var navController: NavController
    private lateinit var adapter: AcceptedFriendsAdapter
    private lateinit var friendsList: List<FriendItem>
    private val viewModel by viewModels<MyFriendsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFriendsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }

    private fun setupViews() = with(binding) {
        adapter = AcceptedFriendsAdapter(
            emptyList(),
            itemRemovedClick(),
            itemLocationClick()
        )
        rvMyPlaces.adapter = adapter
        rvMyPlaces.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
        placesListUpdate()
    }

    private fun placesListUpdate() {
        lifecycleScope.launch {
            viewModel.getFriendsList().collect { result ->
                result.onSuccess { listOfFriends ->
                    friendsList = listOfFriends
                    adapter.updateCart(friendsList)
                }
                result.onFailure {
                    ToastObj.longToastMake("Помилка оновлення списку друзів", context)
                }
            }
        }
    }

    private fun itemRemovedClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.objectId?.let {

            }
        }
    }

    private fun itemLocationClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.location?.let {

            }
        }
    }

}