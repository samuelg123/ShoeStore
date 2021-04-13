package com.udacity.shoestore.features.shoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.MainViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import timber.log.Timber

class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.shoeList.observe(viewLifecycleOwner) {
            binding.linearLayout.removeAllViews()
            it.forEach { shoe ->
                val itemView = LayoutInflater.from(requireContext()).inflate(
                    R.layout.shoe_item,
                    binding.linearLayout,
                    false
                ).apply {
                    setOnClickListener(
                        Navigation.createNavigateOnClickListener(
                            ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment(
                                shoe
                            )
                        )
                    )
                    findViewById<ImageView>(R.id.shoe_image).apply {
                        shoe.images.firstOrNull()?.apply {
                            setImageURI(this)
                        }
                    }
                    findViewById<TextView>(R.id.shoe_name).apply {
                        text = shoe.name
                    }
                }

                binding.linearLayout.addView(itemView)
            }
            binding.linearLayout.requestLayout()
            binding.noShoe.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}