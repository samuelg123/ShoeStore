package com.udacity.shoestore.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.MainViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.isEmailValid

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this
        binding.fragment = this
        binding.viewModel = viewModel

        return binding.root
    }

    fun submit() {
        val user = viewModel.user.value ?: return
        val (email, password) = user
        when {
            email.isNullOrEmpty() -> {
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
            password.isNullOrEmpty() -> {
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
}