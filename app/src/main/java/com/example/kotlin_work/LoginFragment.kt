package com.example.kotlin_work

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    val vm: LoginViewModel by viewModels()
    private val client = OkHttpClient()

    val viewModel: LoginViewModel by viewModels( factoryProducer = {
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(
                    OkHttpClient::class.java
                ).newInstance(client)
            }
        }
    })

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.responseBody.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),
                it, Toast.LENGTH_LONG).show()
        })

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.editEmailAddress).text.toString()
            val password = view.findViewById<EditText>(R.id.editPassword).text.toString()

            vm.login(email, password)
        }

        vm.userLiveData.observe(viewLifecycleOwner, Observer {
            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }
}
