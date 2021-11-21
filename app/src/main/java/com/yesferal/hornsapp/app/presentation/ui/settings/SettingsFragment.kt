package com.yesferal.hornsapp.app.presentation.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.extension.setUpWith
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.hadi_android.getViewModel

class SettingsFragment : RenderFragment<SettingsState>() {

    override val layout = R.layout.fragment_settings

    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var arrowImageView: ImageView
    private lateinit var environmentSpinner: Spinner
    private lateinit var forceCrashTextView: TextView
    private lateinit var environmentSubTitleTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrowImageView = view.findViewById(R.id.arrowImageView)
        environmentSubTitleTextView = view.findViewById(R.id.environmentSubTitleTextView)
        environmentSpinner = view.findViewById(R.id.environmentSpinner)
        forceCrashTextView = view.findViewById(R.id.forceCrashTextView)

        arrowImageView.setOnClickListener {
            environmentSpinner.performClick()
        }

        settingsViewModel = getViewModel<SettingsViewModel, SettingsViewModelFactory>()

        settingsViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        forceCrashTextView.setOnClickListener {
            throw Exception(getString(R.string.settings_force_crash_subtitle))
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
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                environmentSubTitleTextView.setUpWith(viewState.environments[position].second)
                settingsViewModel.onSpinnerItemSelected(environment = position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
