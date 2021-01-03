package com.yesferal.hornsapp.app.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.R
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment
    : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        view.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams

                val statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android")

                val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)

                layoutParams.setMargins(0, 0, 0, statusBarHeight)
                imageView.layoutParams = layoutParams

                initMotionLayout()

                return true
            }
        })

        return view
    }

    private fun initMotionLayout() {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                findNavController().navigate(SplashFragmentDirections.actionSplashToHome())
            }
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) { }
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) { }
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) { }
        })
        motionLayout.transitionToEnd()
    }
}