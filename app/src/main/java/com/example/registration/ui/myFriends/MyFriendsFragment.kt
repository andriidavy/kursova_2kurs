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
import com.example.registration.R
import com.example.registration.adapter.AcceptedFriendsAdapter
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
        setListeners()
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
        friendsListUpdate()
    }

    private fun setListeners() = with(binding) {
        btSearch.setOnClickListener {
            friendsListUpdate()
        }
        btAddFriends.setOnClickListener {
            navController.navigate(R.id.action_myFriendsFragment_to_inviteFriendFragment)
        }
        btInviteToMe.setOnClickListener {
            navController.navigate(R.id.action_myFriendsFragment_to_inviteToMeFragment)
        }
    }

    private fun friendsListUpdate() {
        val searchDistance = binding.etSearch.text.toString()
        lifecycleScope.launch {
            viewModel.getFriendsList(searchDistance).collect { result ->
                result.onSuccess { listOfFriends ->
                    friendsList = listOfFriends
                    adapter.updateCart(friendsList)
                }
                result.onFailure {
                    ToastObj.longToastMake("Список порожній", context)
                }
            }
        }
    }

    private fun itemRemovedClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.objectId?.let { friendsId ->
                lifecycleScope.launch {
                    viewModel.deleteFriend(friendsId).collect { result ->
                        result.onSuccess {
                            ToastObj.shortToastMake("Видалено!", context)
                            friendsListUpdate()
                        }
                        result.onFailure { ToastObj.shortToastMake("Помилка!", context) }
                    }
                }
                friendsListUpdate()
            }
        }
    }

    private fun itemLocationClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.let { item ->
                val location = item.location
                val title = item.name
                val bundle = Bundle()
                bundle.putSerializable("location", location)
                bundle.putString("title", title)
                navController.navigate(R.id.action_myFriendsFragment_to_mapsFragment, bundle)
            }
        }
    }

}