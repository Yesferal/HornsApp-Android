package com.yesferal.hornsapp.app.presentation.common

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V: BaseContract.View, VD: ViewData>
    : BaseContract.ActionListener {
    var view: V? = null

    abstract fun onViewCreated()
    abstract fun render(state: State<VD>)

    override fun attach(view: BaseContract.View) {
        this.view = view as V
    }

    override fun detachView() {
        this.view = null
    }
}