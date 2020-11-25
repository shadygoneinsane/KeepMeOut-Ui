package com.example.keepmeout_ui.ui.splash

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.keepmeout_ui.MainActivity
import com.example.keepmeout_ui.R
import com.example.keepmeout_ui.databinding.ActivitySplashBinding


/**
 * Splash screen [AppCompatActivity]
 *
 * Created by: Vikesh Dass
 * Created on: 25-11-2020
 * Email : vikeshdass@gmail.com
 */
class SplashActivity : AppCompatActivity(), Animator.AnimatorListener {
    private lateinit var viewBinding: ActivitySplashBinding
    private val set = AnimatorSet()
    private val handler = Handler(Looper.getMainLooper())

    private val runnable: Runnable by lazy {
        Runnable {
            set.pause()
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        set.playSequentially(animateUp(), animateDown(), animateUp(), animateDown())
        set.addListener(this)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
        set.start()
    }

    override fun onStop() {
        super.onStop()
        set.pause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        set.end()
        super.onDestroy()
    }

    private fun animateUp(): Animator {
        return ObjectAnimator.ofFloat(viewBinding.icLogo, "translationY", -100f).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    private fun animateDown(): Animator {
        return ObjectAnimator.ofFloat(viewBinding.icLogo, "translationY", 100f).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onAnimationStart(animation: Animator?) {
        //Do nothing
    }

    override fun onAnimationEnd(animation: Animator?) {
        set.start()
    }

    override fun onAnimationCancel(animation: Animator?) {
        //Do nothing
    }

    override fun onAnimationRepeat(animation: Animator?) {
        //Do nothing
    }
}