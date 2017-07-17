package com.rbueno.desafioandroid

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



/**
 * Created by Rafael Bueno on 12/04/2017.
 */

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }

}