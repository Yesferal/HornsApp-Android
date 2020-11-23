package com.yesferal.hornsapp.app.presentation.common

abstract class ViewHolderData(
    id: String,
    name: String?
): ViewData(id, name) {

    interface Listener

    abstract fun getViewType(): Int
}