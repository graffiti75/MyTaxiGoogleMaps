package br.cericatto.openweathermap

import android.support.test.espresso.IdlingResource
import android.support.test.runner.AndroidJUnit4
import android.support.v4.app.FragmentManager

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DialogFragmentIdlingResource(private val mFragmentManager: FragmentManager, private val mTag: String)
    : IdlingResource {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private var mResourceCallback: IdlingResource.ResourceCallback? = null

    //--------------------------------------------------
    // Idling Resource
    //--------------------------------------------------

    override fun getName(): String {
        return DialogFragmentIdlingResource::class.java.name + ":" + mTag
    }

    override fun isIdleNow(): Boolean {
        val idle = mFragmentManager.findFragmentByTag(mTag) == null
        if (idle) {
            mResourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        mResourceCallback = resourceCallback
    }
}