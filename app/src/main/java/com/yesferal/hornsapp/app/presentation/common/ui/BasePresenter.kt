package com.yesferal.hornsapp.app.presentation.common.ui

abstract class BasePresenter<VIEW: BaseContract.View>
    : BaseContract.ActionListener {
    var view: VIEW? = null

    @Suppress("UNCHECKED_CAST")
    override fun attach(view: BaseContract.View) {
        this.view = view as VIEW
    }

    override fun detachView() {
        this.view = null
    }
}