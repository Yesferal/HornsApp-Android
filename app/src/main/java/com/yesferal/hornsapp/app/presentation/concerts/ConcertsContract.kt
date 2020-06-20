package com.yesferal.hornsapp.app.presentation.concerts

import com.yesferal.hornsapp.app.presentation.common.BaseContract
import com.yesferal.hornsapp.app.presentation.common.State
import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertsContract {
    interface View: BaseContract.View {
        fun render(state: State<List<Concert>>)
    }

    interface ActionListener: BaseContract.ActionListener {
        fun onViewCreated()
    }
}