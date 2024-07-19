package ir.abyx.pastry.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.abyx.pastry.mvp.model.ModelCakeCatsFragment
import ir.abyx.pastry.mvp.presenter.PresenterCakeCatsFragment
import ir.abyx.pastry.mvp.view.ViewCakeCatsFragment

class CakeCatsFragment(private val context: Context) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = ViewCakeCatsFragment(context)
        val presenter = PresenterCakeCatsFragment(context, view, ModelCakeCatsFragment())
        presenter.onCreate()
        return view.binding.root

    }
}