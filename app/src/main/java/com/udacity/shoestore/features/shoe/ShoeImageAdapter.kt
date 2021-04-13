package com.udacity.shoestore.features.shoe

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R

class ShoeImageAdapter :
    RecyclerView.Adapter<ShoeImageAdapter.ViewHolder>() {
    private val shoeImageList: MutableList<Uri> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.shoe_image)

        fun bind(uri: Uri) {
            image.setImageURI(uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shoe_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(shoeImageList[position])
    }

    override fun getItemCount() = shoeImageList.size

    fun updateImage(newList: List<Uri>) {
        shoeImageList.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }
}