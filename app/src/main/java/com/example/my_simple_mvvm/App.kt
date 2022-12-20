package com.example.my_simple_mvvm

import android.app.Application
import com.example.foundation.BaseApplication
import com.example.foundation.model.Repository
import com.example.my_simple_mvvm.model.colors.InMemoryColorsRepository


/**
 * Here we store instances of model layer classes
 */

class App : Application(), BaseApplication {

    /**
     * Place your repositories here
     */
    override val repositories: List<Repository> = listOf<Repository>(
        InMemoryColorsRepository()
    )
}