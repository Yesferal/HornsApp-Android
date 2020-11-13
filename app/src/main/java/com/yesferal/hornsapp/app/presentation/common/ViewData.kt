package com.yesferal.hornsapp.app.presentation.common

open class ViewData(
    val id: String,
    val name: String?
)

class TextViewData(
    id: String,
    name: String?
): ViewData(id, name)