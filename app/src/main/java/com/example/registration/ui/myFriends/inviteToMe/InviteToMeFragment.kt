package com.example.registration.ui.myFriends.inviteToMe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.InviteToMeAdapter
import com.example.registration.adapter.SearchingFriendsAdapter
import com.example.registration.databinding.FragmentInviteFriendBinding
import com.example.registration.databinding.FragmentInviteToMeBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.friends.FriendItem
import com.example.registration.model.friends.SearchFriendItem
import com.example.registration.ui.myFriends.inviteFriend.InviteFriendViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InviteToMeFragment : Fragment() {
    private lateinit var binding: FragmentInviteToMeBinding
    private lateinit var navController: NavController
    private lateinit var adapter: InviteToMeAdapter
    private lateinit var friendsList: List<FriendItem>
    private val viewModel by viewModels<InviteToMeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInviteToMeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() = with(binding) {
        adapter = InviteToMeAdapter(
            emptyList(),
            itemAcceptClick(),
            itemRejectClick()
        )
        rvMyFriends.adapter = adapter
        rvMyFriends.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
        friendsListUpdate()
    }

    private fun friendsListUpdate() {
        lifecycleScope.launch {
            viewModel.getInviteToMe().collect { result ->
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

    private fun itemAcceptClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.objectId?.let { friendId ->
                lifecycleScope.launch {
                    viewModel.acceptInvite(friendId).collect { result ->
                        result.onSuccess {
                            ToastObj.shortToastMake("Запрошення прийнято!", context)
                            friendsListUpdate()
                        }
                        result.onFailure {
                            ToastObj.shortToastMake("Помилка!", context)
                        }
                    }
                }
            }
        }
    }

    private fun itemRejectClick(): (Int) -> Unit {
        return { position ->
            friendsList.getOrNull(position)?.objectId?.let { friendId ->
                lifecycleScope.launch {
                    viewModel.rejectInvite(friendId).collect { result ->
                        result.onSuccess {
                            ToastObj.shortToastMake("Запрошення відхилено!", context)
                            friendsListUpdate()
                        }
                        result.onFailure {
                            ToastObj.shortToastMake("Помилка!", context)
                        }
                    }
                }
            }
        }
    }
}