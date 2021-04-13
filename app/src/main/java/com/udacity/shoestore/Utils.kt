package com.udacity.shoestore

val String.isEmailValid: Boolean
    get() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()