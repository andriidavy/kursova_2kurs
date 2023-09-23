package com.example.registration.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentStartBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.ui.login.LoginViewModel
import com.example.registration.ui.start.loginscreens.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.startComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoginScreen(login())
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun login(): (String, String, Int) -> Unit {
        return { email, password, num ->
            lifecycleScope.launch {
                viewModel.login(email, password, num)?.collect { loginResult ->
                    loginResult.onSuccess { user ->
                        when (num) {
                            0 -> navController.navigate(R.id.action_startFragment_to_customerMainPageFragment)
                            1 -> navController.navigate(R.id.action_startFragment_to_employeeMainPageFragment)
                            2 -> navController.navigate(R.id.action_startFragment_to_managerMainPageFragment)
                        }

                        // установка ID користувача при вході
                        dataStoreViewModel.storeUserId(user.id)

                        ToastObj.longToastMake(
                            getString(
                                R.string.success_log,
                                user.name,
                                user.surname
                            ), context
                        )
                    }
                    loginResult.onFailure {
                        ToastObj.shortToastMake(getString(R.string.invalid_log), context)
                    }
                }
            }
        }
    }
}