package ir.abyx.pastry.adapter.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.adapter.recycler.diffUtil.RecyclerDiffUtil
import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.Addresses
import ir.abyx.pastry.databinding.RecyclerItemListAddressShopBinding

class ShopAddressRecyclerAdapter(
    private val addresses: ArrayList<Addresses>,
    private var selectedPosition: Int = 0
) : RecyclerView.Adapter<ShopAddressRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemListAddressShopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = addresses.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(addresses[position], position)

    @SuppressLint("NotifyDataSetChanged")
    inner class CustomViewHolder(private val binding: RecyclerItemListAddressShopBinding) :
        ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                updateSelectedPosition(adapterPosition)
                notifyDataSetChanged()
            }
        }

        @SuppressLint("SetTextI18n")
        fun setData(address: Addresses, position: Int) {
            binding.apply {
                cbAddress.isChecked = position == selectedPosition
                txtCbText.text = "آدرس ${position + 1} :"
                txtName.text = address.receiver
                txtAddress.text = address.address
                txtPhone.text = address.phone

                cbAddress.setOnClickListener {
                    updateSelectedPosition(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun dataUpdate(newList: ArrayList<Addresses>) {

        val diffCallback = RecyclerDiffUtil(addresses, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        addresses.clear()
        addresses.addAll(newList)

        diffResult.dispatchUpdatesTo(this)

    }

    private fun updateSelectedPosition(position: Int) {
        selectedPosition = position
    }
}