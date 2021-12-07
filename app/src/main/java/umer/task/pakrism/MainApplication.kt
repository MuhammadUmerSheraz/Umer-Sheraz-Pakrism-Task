package umer.task.pakrism

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import umer.task.pakrism.di.Modules


class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Modules.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}