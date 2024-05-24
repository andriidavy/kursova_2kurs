package com.example.registration.ui.myFriends.inviteFriend

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
import com.example.registration.adapter.SearchingFriendsAdapter
import com.example.registration.databinding.FragmentInviteFriendBinding
import com.example.registration.databinding.FragmentMyFriendsBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.friends.SearchFriendItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InviteFriendFragment : Fragment() {
    private lateinit var binding: FragmentInviteFriendBinding
    private lateinit var navController: NavController
    private lateinit var adapter: SearchingFriendsAdapter
    private lateinit var friendsList: List<SearchFriendItem>
    private val viewModel by viewModels<InviteFriendViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInviteFriendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = SearchingFriendsAdapter(
            emptyList(),
            itemAddClick()
        )
        rvMyFriends.adapter = adapter
        rvMyFriends.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btSearch.setOnClickListener {
            friendsListUpdate()
        }
    }

    private fun friendsListUpdate() {
        val searchLine = binding.etSearch.text.toString()
        lifecycleScope.launch {
            viewModel.getFriendByName(searchLine).collect { result ->
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

    private fun itemAddClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.let { item ->
                // TODO:
            }
        }
    }

}