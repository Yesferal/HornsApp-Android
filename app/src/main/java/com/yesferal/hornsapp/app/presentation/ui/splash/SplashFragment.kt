package com.yesferal.hornsapp.app.presentation.ui.splash

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.render.RenderFragment
import com.yesferal.hornsapp.app.presentation.di.hada.getViewModel

class SplashFragment : RenderFragment<SplashState>() {

    override val layout = R.layout.fragment_splash

    private lateinit var splashViewModel: SplashViewModel

    private lateinit var motionLayout: MotionLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel = getViewModel<SplashViewModel, SplashViewModelFactory>()

        motionLayout = view.findViewById(R.id.motionLayout)

        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)

                splashViewModel.onViewCreated()
                return true
            }
        })

        splashViewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun render(viewState: SplashState) {
        val navDirection = if (viewState.onBoardingVisibility) {
            SplashFragmentDirections.actionSplashToOnBoarding()
        } else {
            SplashFragmentDirections.actionSplashToHome()
        }
        initMotionLayout(navDirection)
    }

    private fun initMotionLayout(navDirection: NavDirections) {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                findNavController().navigate(navDirection)
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
        motionLayout.transitionToEnd()
    }
}
