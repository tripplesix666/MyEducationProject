package com.example.my_simple_mvvm

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.example.my_simple_mvvm.utils.Event
import com.example.my_simple_mvvm.utils.ResourceActions
import com.example.my_simple_mvvm.views.Navigator
import com.example.my_simple_mvvm.views.UiActions
import com.example.my_simple_mvvm.views.base.BaseScreen
import com.example.my_simple_mvvm.views.base.LiveEvent
import com.example.my_simple_mvvm.views.base.MutableLiveEvent

const val ARG_SCREEN = "ARG_SCREEN"

/**
 * Implementation of [Navigator] and [UiActions].
 * It is based on activity view-model because instances of [Navigator] and [UiActions]
 * should be available from fragments' view-models (usually they are passed to the view-model constructor).
 *
 * This view-model extends [AndroidViewModel] which means that it is not "usual" view-model and
 * it may contain android dependencies (context, bundles, etc.).
 */
class MainViewModel(
    application: Application
) : AndroidViewModel(application), Navigator, UiActions {

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive {
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun toast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        return getApplication<App>().getString(messageRes, *args)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        // as screen classes are inside fragments -> we can create fragment directly from screen
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        // set screen object as fragment's argument
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .setCustomAnimations(
                R.anim.enter,
                R.anim.exit,
                R.anim.pop_enter,
                R.anim.pop_exit,
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }
}