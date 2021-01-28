package com.yesferal.hornsapp.app.presentation.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment
    : BaseFragment<SettingsState>() {
    private lateinit var settingsViewModel: SettingsViewModel

    override val layout: Int
        get() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrowImageView.setOnClickListener {
            environmentSpinner.performClick()
        }

        settingsViewModel = ViewModelProvider(
            this,
            hada().resolve<SettingsViewModelFactory>()
        ).get(SettingsViewModel::class.java)

        settingsViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun render(viewState: SettingsState) {

        ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                viewState.environments.map { it.first }
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            environmentSpinner.adapter = adapter
        }

        environmentSpinner.setSelection(viewState.environment)

        environmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                environmentSubTitleTextView.setUpWith(viewState.environments[position].second)
                settingsViewModel.onSpinnerItemSelected(environment = position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}