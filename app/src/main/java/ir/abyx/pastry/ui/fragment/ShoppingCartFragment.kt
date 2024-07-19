package ir.abyx.pastry.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelShoppingCartFragment
import ir.abyx.pastry.mvp.presenter.PresenterShoppingCartFragment
import ir.abyx.pastry.mvp.view.ViewShoppingCartFragment

class ShoppingCartFragment(
    private val context: Context,
) :
    Fragment() {
    private lateinit var presenter: PresenterShoppingCartFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ViewShoppingCartFragment(context)
        presenter = PresenterShoppingCartFragment(context, view, ModelShoppingCartFragment())
        presenter.onCreate()
        return view.binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }
}