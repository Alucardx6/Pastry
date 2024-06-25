package ir.abyx.pastry.mvp.ext

interface BaseLifecycle {

    fun onCreate()

    fun onStop() {}

    fun onDestroy() {}

}