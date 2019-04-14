package com.gg.compassproject.core.platform

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(){

    private val subscriptions = CompositeDisposable()

    fun addSubscription(disposable: Disposable){
        subscriptions.addAll(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

}
