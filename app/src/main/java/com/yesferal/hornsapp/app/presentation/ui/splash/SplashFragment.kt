package com.yesferal.hornsapp.app.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment
    : BaseFragment<SplashState>() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel = ViewModelProvider(
            this,
            hada().resolve<SplashViewModelFactory>()
        ).get(SplashViewModel::class.java)

        splashViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun initMotionLayout(navDirection : NavDirections) {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                findNavController().navigate(navDirection)
            }
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) { }
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) { }
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) { }
        })
        motionLayout.transitionToEnd()
    }

    override fun render(viewState: SplashState) {
        val navDirection : NavDirections = if (viewState.onBoardingVisibility) {
            SplashFragmentDirections.actionSplashToOnBoarding()
        } else {
            SplashFragmentDirections.actionSplashToHome()
        }
        initMotionLayout(navDirection)
    }

    override val layout: Int
        get() = R.layout.fragment_splash
}