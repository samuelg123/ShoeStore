package com.udacity.shoestore.features.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.isEmailValid

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener: (it: View) -> Unit = {
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            when {
                email.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.empty_email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !email.isEmailValid -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.incorrect_email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.empty_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
                }
            }
        }

//        val listener = Navigation.createNavigateOnClickListener(
//            if(binding.emailEdit.text.toString().isEmailValid){
//
//            }
//            LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
//        )

        binding.loginExistingButton.setOnClickListener(listener)
        binding.newLoginButton.setOnClickListener(listener)
    }
}