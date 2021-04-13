package com.udacity.shoestore.features.shoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.MainViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import java.util.*

class ShoeDetailFragment : Fragment() {

    private lateinit var llm: LinearLayoutManager
    private lateinit var shoeImageAdapter: ShoeImageAdapter
    private lateinit var binding: FragmentShoeDetailBinding

    private val viewModel: MainViewModel by activityViewModels()
    private val args: ShoeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        viewModel.shoe.value = args.shoe?.copy() ?: Shoe()

        binding.run {
            cancelButton.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }

            shoeImageAdapter = ShoeImageAdapter()
            llm = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            shoeRecycler.apply {
                adapter = shoeImageAdapter
                layoutManager = llm
            }

            this@ShoeDetailFragment.viewModel.shoe.observe(viewLifecycleOwner) {
                shoeImageAdapter.updateImage(it.images)
                noImageText.visibility =
                    if (it.images.isNotEmpty()) View.GONE
                    else View.VISIBLE
            }
        }
        return binding.root
    }

    fun onSave(view: View) {
        val success = viewModel.saveShoe(args.shoe != null)
        if (!success) {
            Toast.makeText(
                requireContext(),
                getString(R.string.shoe_name_empty),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        Navigation.findNavController(view).popBackStack()
    }
}