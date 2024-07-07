package ir.abyx.pastry.adapter.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.adapter.recycler.diffUtil.RecyclerDiffUtil
import ir.abyx.pastry.data.remote.dataModel.Addresses
import ir.abyx.pastry.databinding.RecyclerItemListAddressBinding
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.ui.activity.EditAddressActivity

class AddressRecyclerAdapter(
    private val context: Context,
    private val addresses: ArrayList<Addresses>,
    private val viewUtils: ViewUtils
) :
    RecyclerView.Adapter<AddressRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemListAddressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int =
        addresses.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setDate(addresses[position])

    inner class CustomViewHolder(private val binding: RecyclerItemListAddressBinding) :
        ViewHolder(binding.root) {

        fun setDate(address: Addresses) {
            binding.txtName.text = address.receiver
            binding.txtPhone.text = address.phone
            binding.txtAddress.text = address.address

            binding.imgEdit.setOnClickListener {
                val intent = Intent(context, EditAddressActivity::class.java)
                intent.putExtra("edit", true)
                intent.putExtra("id", address.ID)
                intent.putExtra("receiver", address.receiver)
                intent.putExtra("phone", address.phone)
                intent.putExtra("address", address.address)
                context.startActivity(intent)
            }

            binding.imgDelete.setOnClickListener {
                viewUtils.deleteAddress(address.ID)
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

}