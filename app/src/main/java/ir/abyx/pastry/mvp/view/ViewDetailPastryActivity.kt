package ir.abyx.pastry.mvp.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.R
import ir.abyx.pastry.adapter.carousel.CarouselAdapter
import ir.abyx.pastry.adapter.recycler.CommentsRecyclerAdapter
import ir.abyx.pastry.adapter.recycler.MaterialsRecyclerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.PastryDetailModel
import ir.abyx.pastry.databinding.ActivityDetailPastryBinding
import ir.abyx.pastry.databinding.CustomDialogSellBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.ext.ViewUtils

class ViewDetailPastryActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityDetailPastryBinding.inflate(LayoutInflater.from(context))

    @SuppressLint("SetTextI18n")
    fun initialize(data: PastryDetailModel, viewUtils: ViewUtils) {
        val viewPager = binding.viewPagerSlider
        viewPager.layoutDirection = View.LAYOUT_DIRECTION_RTL
        activityUtils.viewPagerFragment(viewPager, data.gallery)

        binding.apply {
            txtTitle.text = data.title
            txtRate.text = data.rate.rate.toString()
            recyclerMaterials.apply {
                getRecycler().layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                getRecycler().adapter = MaterialsRecyclerAdapter(data.materials)
            }

            //region Related Pastries
            recyclerRelated.apply {
                getRecycler().layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)
                getRecycler().adapter = CarouselAdapter(data.related)
            }
            //endregion
        }

        if (data.comment_count > 0) {
            binding.apply {
                commentCountGroup.visibility = View.VISIBLE
                txtCommentCount.text = data.comment_count.toString()
            }
        } else
            binding.commentCountGroup.visibility = View.INVISIBLE

        var bookmark = data.bookmark

        var action: String

        if (bookmark)
            binding.imgFavorite.setImageResource(R.drawable.ic_actived_favorite)
        else
            binding.imgFavorite.setImageResource(R.drawable.ic_favorite)

        binding.imgFavorite.setOnClickListener {
            if (bookmark) {
                action = "unfavorite"
                binding.imgFavorite.setImageResource(R.drawable.ic_favorite)
                bookmark = false
            } else {
                action = "favorite"
                binding.imgFavorite.setImageResource(R.drawable.ic_actived_favorite)
                bookmark = true
            }

            viewUtils.favorite(action, data.ID)
        }

        binding.btnSendComment.getView().setOnClickListener {
            val text = binding.edtComment.text.toString()
            val rate = binding.ratingComment.rating

            if (text.isEmpty() || (text.length < 10)) {
                ToastUtils.toast(context, "نظر شما نمیتواند کمتر از ۱۰ کارکتر باشد")
                return@setOnClickListener
            }

            binding.btnSendComment.enableProgress()

            viewUtils.sendComment(text, rate, data.ID)
        }

        binding.txtDesc.text = data.content

        if (data.comments != null) {
            binding.recyclerComments.apply {
                getRecycler().layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                CommentsRecyclerAdapter(data.comments).also { getRecycler().adapter = it }
            }
        }

        binding.txtMainPrice.text = OthersUtilities.splitPrice(data.price)

        if (data.has_discount) {

            binding.apply {
                txtMainPrice.paintFlags =
                    txtMainPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                txtMainPrice.setTextColor(Color.GRAY)

                offGroup.visibility = View.VISIBLE
                txtOffPrice.text = OthersUtilities.splitPrice(data.sale_price)
                txtOffPercent.text = data.discount_percent_110n
            }

        } else
            binding.offGroup.visibility = View.GONE

        binding.btnShop.getView().setOnClickListener {
            binding.btnShop.enableProgress()

            val view = CustomDialogSellBinding.inflate(LayoutInflater.from(context))
            val dialog = Dialog(context)

            view.txtPriceBased.text = OthersUtilities.splitPrice(data.price)

            shopDialog(view, data)

            if (data.has_discount) {

                view.apply {
                    offGroup.visibility = View.VISIBLE
                    txtPriceOff.text = OthersUtilities.splitPrice(data.sale_price)
                    txtOff18n.text = "(${data.discount_percent_110n})"
                    txtPriceTotal.paintFlags =
                        txtPriceTotal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    txtPriceTotal.setTextColor(Color.GRAY)
                }

            } else {

                view.apply {
                    offGroup.visibility = View.GONE
                    txtPriceTotalOff.visibility = View.GONE
                }
            }

            dialog.apply {
                setContentView(view.root)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }

            view.btnContinueSell.getView().setOnClickListener {

            }

            view.apply {
                rbSellNormal.setOnClickListener {

                    view.apply {
                        txtCount.text = "1"
                        rbSellNormal.isChecked = true
                        rbSellMajor.isChecked = false
                        viewNormal.setBackgroundResource(R.drawable.back_radio_sell_white)
                        viewMajor.setBackgroundResource(R.drawable.back_radio_sell_transparent)
                    }
                    shopDialog(view, data)
                }

                rbSellMajor.setOnClickListener {
                    view.txtCount.text = "10"
                    shopDialog(view, data)

                    view.rbSellNormal.isChecked = false
                    view.rbSellMajor.isChecked = true

                    view.viewNormal.setBackgroundResource(R.drawable.back_radio_sell_transparent)
                    view.viewMajor.setBackgroundResource(R.drawable.back_radio_sell_white)
                }

                viewPlus.setOnClickListener {

                    var count = view.txtCount.text.toString().toInt()

                    if (view.rbSellNormal.isChecked)
                        if (count < 10)
                            count++

                    if (view.rbSellMajor.isChecked)
                        if (count < 100)
                            count += 5

                    view.txtCount.text = count.toString()
                    shopDialog(view, data)

                }

                viewMin.setOnClickListener {

                    var count = view.txtCount.text.toString().toInt()

                    if (view.rbSellNormal.isChecked)
                        if (count > 1)
                            count--

                    if (view.rbSellMajor.isChecked)
                        if (count > 10)
                            count -= 5

                    view.txtCount.text = count.toString()
                    shopDialog(view, data)

                }
            }

            dialog.setOnCancelListener {
                binding.btnShop.disableProgress()
            }

        }

//        binding.rbNormal.setOnClickListener {
//            binding.rbNormal.setBackgroundResource(R.drawable.rb_active)
//            binding.rbMajor.setBackgroundResource(R.drawable.rb_deactive)
//        }
//
//        binding.rbMajor.setOnClickListener {
//            binding.rbNormal.setBackgroundResource(R.drawable.rb_deactive)
//            binding.rbMajor.setBackgroundResource(R.drawable.rb_active)
//        }
    }

    private fun shopDialog(view: CustomDialogSellBinding, data: PastryDetailModel) {

        val count = view.txtCount.text.toString().toInt()
        var salePrice = 0

        if (!data.bulk_price.isNullOrEmpty()) {
            data.bulk_price.forEach {
                if (it.amount > count) {
                    salePrice = it.sale_price
                    return@forEach
                }
            }
        } else
            salePrice = data.sale_price

        val priceTo = view.txtCount.text.toString().toInt() * data.price
        view.txtPriceTotal.text = OthersUtilities.splitPrice(priceTo)
        view.offGroup.visibility = View.VISIBLE
        val priceOffTotal = view.txtCount.text.toString().toInt() * salePrice
        view.txtPriceTotalOff.text = OthersUtilities.splitPrice(priceOffTotal)

    }

    fun onBackPressed() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }

    fun startGetData() {
        binding.apply {
            allViews.visibility = View.INVISIBLE
            bottomViewsGroup.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        }
    }

    fun endGetData() {
        binding.apply {
            allViews.visibility = View.VISIBLE
            bottomViewsGroup.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
    }

    fun endBtnCommentProgress() {
        binding.btnSendComment.disableProgress()
    }
}