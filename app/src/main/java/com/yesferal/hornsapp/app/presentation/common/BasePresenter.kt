package com.yesferal.hornsapp.app.presentation.common

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<VIEW: BaseContract.View>
    : BaseContract.ActionListener {
    var view: VIEW? = null

    override fun attach(view: BaseContract.View) {
        this.view = view as VIEW
    }

    override fun detachView() {
        this.view = null
    }
}