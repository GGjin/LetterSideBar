package com.gg.lettersidebar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LetterSideBar.OnTouchLetterListener {


    override fun onTouchLetterChange(letter: String, isTouch: Boolean) {
        if (isTouch) {
            mLetterText.text = letter
            mLetterText.visibility = View.VISIBLE
        } else {
            mLetterText.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        letterSideBar.setOnTouchLetterListener(this)
    }
}
