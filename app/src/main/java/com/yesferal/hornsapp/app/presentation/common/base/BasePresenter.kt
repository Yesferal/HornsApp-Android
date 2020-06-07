package com.yesferal.hornsapp.app.presentation.common.base

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V: BaseContract.View>
    : BaseContract.ActionListener {
    var view: V? = null

    override fun attach(view: BaseContract.View) {
        this.view = view as V
    }

    override fun detachView() {
        this.view = null
    }
}