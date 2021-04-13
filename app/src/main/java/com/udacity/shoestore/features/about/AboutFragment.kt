package com.udacity.shoestore.features.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentAboutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
//        return inflater.inflate(R.layout.fragment_about, container, false)
        return binding.root
    }
}