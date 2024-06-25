package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import ir.abyx.pastry.databinding.ActivityMainBinding

class ViewMainActivity(context: Context) : FrameLayout(context) {

    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))

}