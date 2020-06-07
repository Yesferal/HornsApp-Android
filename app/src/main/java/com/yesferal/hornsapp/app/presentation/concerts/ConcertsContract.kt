package com.yesferal.hornsapp.app.presentation.concerts

import com.yesferal.hornsapp.app.presentation.base.BaseContract
import com.yesferal.hornsapp.app.presentation.base.State
import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertsContract {
    interface View: BaseContract.View {
        fun updateWith(state: State<List<Concert>>)
    }

    interface ActionListener: BaseContract.ActionListener {
        fun onCreate()
    }
}