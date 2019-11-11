package com.liang.roomhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun jumpToTest(view: View) {
        startActivity(Intent(this, TestActivity::class.java))
    }

    fun clear(view: View){
        DataBaseManager.appDatabase.getUserDao().clearTable()
    }

}

