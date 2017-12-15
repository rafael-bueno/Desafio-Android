package com.rbueno.desafioandroid.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.rbueno.desafioandroid.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)


       // https://api.github.com/search/repositories?q=language:Java&sort=stars&page=
//        https://api.github.com/repos/<criador>/<repositório>/pulls


    }
}