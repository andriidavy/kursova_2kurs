package com.example.registration.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentLoginBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    private var num: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.loginComposeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        navController = findNavController()

        //set Spinner
        val users = arrayOf("Customer", "Employee", "Manager")
        val spinner = spinnerChooseUserType
        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, users) }

        spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    num = position
                    textHaveNotRegistration.visibility =
                        if (position == 0) View.VISIBLE else View.GONE
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        // перехід на сторінку реєстрації
        textHaveNotRegistration.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        // логін
        buttonLog.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            lifecycleScope.launch {
                viewModel.login(email, password, num)?.collect { loginResult ->
                    loginResult.onSuccess { user ->
                        when (num) {
                            0 -> navController.navigate(R.id.action_loginFragment_to_customerMainPageFragment)
                            1 -> navController.navigate(R.id.action_loginFragment_to_employeeMainPageFragment)
                            2 -> navController.navigate(R.id.action_loginFragment_to_managerMainPageFragment)
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