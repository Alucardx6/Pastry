package ir.abyx.pastry.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.abyx.pastry.mvp.model.ModelPastryCatsFragment
import ir.abyx.pastry.mvp.presenter.PresenterPastryCatsFragment
import ir.abyx.pastry.mvp.view.ViewPastryCatsFragment

class PastryCatsFragment(private val context: Context) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ViewPastryCatsFragment(context)
        val presenter = PresenterPastryCatsFragment(context, view, ModelPastryCatsFragment())
        presenter.onCreate()
        return view.binding.root
    }
}