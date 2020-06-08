package com.yesferal.hornsapp.app.presentation.common

interface BaseContract {
    interface View

    interface ActionListener {
        fun attach(view: View)
        fun detachView()
    }
}