package com.example.kotlin_work

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    val vm: LoginViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.editEmailAddress).text.toString()
            val password = view.findViewById<EditText>(R.id.editPassword).text.toString()

            vm.login(email, password)

            // findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        vm.userLiveData.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                requireView(),
                R.string.done_login,
                Snackbar.LENGTH_LONG
            ).show()
        })
    }
}
