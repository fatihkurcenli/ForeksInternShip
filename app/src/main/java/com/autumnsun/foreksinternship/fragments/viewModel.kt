package com.autumnsun.foreksinternship.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel : ViewModel() {
    val message = MutableLiveData<Any>()

    fun setMsgCommunicator(msg: String) {
        message.value = msg
    }
}


