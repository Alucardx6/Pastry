package ir.abyx.pastry.mvp.ext

interface BaseLifecycle {

    fun onCreate()

    fun onResume() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onStart() {}

}