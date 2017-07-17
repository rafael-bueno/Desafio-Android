package com.rbueno.desafioandroid.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.rbueno.desafioandroid.R
import io.kimo.konamicode.KonamiCode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        KonamiCode.Installer(this)
                .on(this)
                .install()
    }
}