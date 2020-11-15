package com.yesferal.hornsapp.app.presentation.common.ui

interface BaseContract {
    interface View

    interface ActionListener {
        fun attach(view: View)
        fun detachView()
    }
}