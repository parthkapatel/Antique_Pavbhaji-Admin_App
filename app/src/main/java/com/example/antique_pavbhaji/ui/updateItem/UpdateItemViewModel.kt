package com.example.antique_pavbhaji.ui.updateItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpdateItemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is updateItem Fragment"
    }
    val text: LiveData<String> = _text
}