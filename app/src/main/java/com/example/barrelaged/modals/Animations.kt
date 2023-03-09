package com.example.barrelaged.modals

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView

object Animations {
    @SuppressLint("Recycle")
    fun growTextViewHeight(elm: TextView){
        val params = elm.layoutParams
        val animator = ValueAnimator.ofInt(
            params.height, measureTextViewHeight(elm)
        ).setDuration(250);

        animator.addUpdateListener { animation1 ->
            val value = animation1.animatedValue as Int
            elm.layoutParams.height = value
            elm.requestLayout()
        }

        startAnimation(animator)
    }

    fun growCardViewHeight(elm: com.google.android.material.card.MaterialCardView, innerElm: RelativeLayout, oldElm: RelativeLayout){
        val params = oldElm.layoutParams
        val test = innerElm.layoutParams

        print(params.height)
        print(test)
        val animator = ValueAnimator.ofInt(
            params.height, measureCardViewHeight(innerElm)
        ).setDuration(250);

        animator.addUpdateListener { animation1 ->
            val value = animation1.animatedValue as Int
            elm.layoutParams.height = value
            elm.requestLayout()
        }

        startAnimation(animator)
    }

    fun shrinkTextViewHeight(elm: TextView, oldHeight: Int){
        val params = elm.layoutParams
        val animator = ValueAnimator.ofInt(
            params.height, oldHeight
        ).setDuration(250);

        animator.addUpdateListener { animation1 ->
            val value = animation1.animatedValue as Int
            elm.layoutParams.height = value
            elm.requestLayout()
        }

        startAnimation(animator)
    }

    fun shrinkCardViewHeight(elm: com.google.android.material.card.MaterialCardView, oldHeight: Int){
        val params = elm.layoutParams
        val animator = ValueAnimator.ofInt(
            params.height, oldHeight
        ).setDuration(250);

        animator.addUpdateListener { animation1 ->
            val value = animation1.animatedValue as Int
            elm.layoutParams.height = value
            elm.requestLayout()
        }

        startAnimation(animator)
    }

    private fun startAnimation(animator: ValueAnimator){
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(animator);
        animationSet.start()
    }

    private fun measureTextViewHeight(elm: TextView): Int{
        elm.measure(
            View.MeasureSpec.makeMeasureSpec(
                elm.width - 0,
                View.MeasureSpec. EXACTLY
            ), View.MeasureSpec.UNSPECIFIED
        )
        return elm.measuredHeight
    }

    private fun measureCardViewHeight(elm: RelativeLayout): Int{
        elm.measure(
            View.MeasureSpec.makeMeasureSpec(
                elm.width - 0,
                View.MeasureSpec. EXACTLY
            ), View.MeasureSpec.UNSPECIFIED
        )
        return elm.measuredHeight
    }
}