package com.udacity.shoestore

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.features.shoe.Shoe
import gun0912.tedimagepicker.builder.TedImagePicker
import timber.log.Timber

class MainViewModel : ViewModel() {
    private val _shoe: MutableLiveData<Shoe> = MutableLiveData()
    val shoe: MutableLiveData<Shoe>
        get() = _shoe

    val shoeSize =
        object : ObservableField<String?>(shoe.value?.size?.toString()) {
            override fun set(value: String?) {
                super.set(value)
                shoe.value = shoe.value?.copy(size = value?.toDoubleOrNull())
            }
        }

    private val _shoeList: MutableList<Shoe> = mutableListOf()
    private val _shoeListLiveData: MutableLiveData<List<Shoe>> =
        MutableLiveData()

    val shoeList: LiveData<List<Shoe>>
        get() = _shoeListLiveData

    fun setImage(context: Context) {
        TedImagePicker.with(context)
            .startMultiImage { uriList ->
                _shoe.value = (_shoe.value ?: Shoe()).apply {
                    images.apply {
                        clear()
                        addAll(uriList)
                    }
                }
            }
    }

    fun saveShoe(update: Boolean): Boolean {
        val currentShoe: Shoe = shoe.value ?: Shoe()
        if (currentShoe.name.isNullOrEmpty()) return false
        if (update) {
            updateShoe(currentShoe)
        } else {
            addShoe(currentShoe)
        }
        return true
    }

    private fun addShoe(newShoe: Shoe) {
        _shoeList.add(newShoe)
        _shoeListLiveData.value = _shoeList
    }

    private fun updateShoe(updateShoe: Shoe) {
        val idx = _shoeList.indexOfFirst { shoe -> shoe.id == updateShoe.id }
        if (idx > -1) {
            _shoeList[idx] = updateShoe
        }
        _shoeListLiveData.value = _shoeList
    }
}