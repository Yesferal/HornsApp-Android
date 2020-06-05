package com.yesferal.hornsapp.app.presentation.common.base

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V: BaseContract.View>
    : BaseContract.ActionListener {
    private var mView: V? = null

    override fun getView() = mView

    override fun attach(view: BaseContract.View) {
        mView = view as V
    }

    override fun detachView() {
        mView = null
    }
}