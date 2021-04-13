package com.udacity.shoestore.features.shoe

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shoe(
    var id: Int = UniqueID.getId(),
    var name: String? = null,
    var size: Double? = null,
    var company: String? = null,
    var description: String? = null,
    val images: MutableList<Uri> = mutableListOf()
) : Parcelable

private object UniqueID {
    private var i = 0
    fun getId(): Int {
        return i++
    }
}