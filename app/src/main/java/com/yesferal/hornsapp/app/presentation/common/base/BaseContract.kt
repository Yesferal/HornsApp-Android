package com.yesferal.hornsapp.app.presentation.common.base

interface BaseContract {
    interface View

    interface ActionListener {
        fun attach(view: View)
        fun detachView()
        fun getView(): View?
    }
}