package com.yesferal.hornsapp.app.presentation.common

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<VIEW: BaseContract.View, VIEW_DATA: ViewData>
    : BaseContract.ActionListener {
    var view: VIEW? = null

    abstract fun onViewCreated()
    abstract fun render(state: ViewState<VIEW_DATA>)

    override fun attach(view: BaseContract.View) {
        this.view = view as VIEW
    }

    override fun detachView() {
        this.view = null
    }
}