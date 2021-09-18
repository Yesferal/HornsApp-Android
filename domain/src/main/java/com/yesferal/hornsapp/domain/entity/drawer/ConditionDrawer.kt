package com.yesferal.hornsapp.domain.entity.drawer

data class ConditionDrawer(
    private val key: String?,
    val value: String?,
    val count: Int?
){
    enum class Type {
        SORT_BY_NEWEST_DATE,
        SORT_BY_UPCOMING_DATE,
        FILTER_BY_CATEGORY,
        UNDETERMINED
    }

    val type: Type
        get() = when (key) {
            Type.SORT_BY_NEWEST_DATE.name -> Type.SORT_BY_NEWEST_DATE
            Type.SORT_BY_UPCOMING_DATE.name -> Type.SORT_BY_UPCOMING_DATE
            Type.FILTER_BY_CATEGORY.name -> Type.FILTER_BY_CATEGORY
            else -> Type.UNDETERMINED
        }
}
