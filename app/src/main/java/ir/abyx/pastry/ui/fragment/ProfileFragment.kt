package ir.abyx.pastry.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.abyx.pastry.mvp.model.ModelProfileFragment
import ir.abyx.pastry.mvp.presenter.PresenterProfileFragment
import ir.abyx.pastry.mvp.view.ViewProfileFragment

class ProfileFragment(
    private val context: Context,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = ViewProfileFragment(context)
        val presenter = PresenterProfileFragment(context, view, ModelProfileFragment())
        presenter.onCreate()
        return view.binding.root
    }

}