package ir.abyx.pastry.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelHomeFragment
import ir.abyx.pastry.mvp.presenter.PresenterHomeFragment
import ir.abyx.pastry.mvp.view.ViewHomeFragment

class HomeFragment(
    private val context: Context,
    private val activityUtils: ActivityUtils
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = ViewHomeFragment(context, activityUtils)
        val presenter = PresenterHomeFragment(view, ModelHomeFragment(), context)
        presenter.onCreate()
        return view.binding.root
    }

}