package com.example.my_simple_mvvm

import android.app.Application
import com.example.my_simple_mvvm.model.colors.InMemoryColorsRepository


/**
 * Here we store instances of model layer classes
 */

class App : Application() {

    /**
     * Place your repositories here
     */
    val models = listOf<Any>(
        InMemoryColorsRepository()
    )
}