package com.yesferal.hornsapp.app.presentation.common

interface ViewHolderData: ViewData {

    interface Listener

    fun getViewType(): Int
}