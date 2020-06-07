package com.yesferal.hornsapp.app.presentation.base

interface BaseContract {
    interface View

    interface ActionListener {
        fun attach(view: View)
        fun detachView()
    }
}