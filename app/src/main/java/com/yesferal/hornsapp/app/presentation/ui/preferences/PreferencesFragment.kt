package com.yesferal.hornsapp.app.presentation.ui.preferences

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import kotlinx.android.synthetic.main.fragment_preferences.*

class PreferencesFragment
    : BaseFragment<PreferencesState>() {

    override val layout: Int
        get() = R.layout.fragment_preferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrowImageView.setOnClickListener {
            environmentSpinner.performClick()
        }

        render(PreferencesState(
            listOf(
                    Pair("Debug", "https://mock.pe"),
                    Pair("Stage", "https://hornsapp.pe")
        )))
    }

    override fun render(viewState: PreferencesState) {

        ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                viewState.environments.map { it.first }
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            environmentSpinner.adapter = adapter
        }

        environmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                environmentSubTitleTextView.setUpWith(viewState.environments[position].second)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}