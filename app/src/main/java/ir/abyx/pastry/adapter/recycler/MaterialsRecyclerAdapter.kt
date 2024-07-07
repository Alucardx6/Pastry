package ir.abyx.pastry.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.R
import ir.abyx.pastry.data.remote.dataModel.Materials
import ir.abyx.pastry.databinding.RecyclerItemListPastryMaterialsBinding

class MaterialsRecyclerAdapter(private val materials: ArrayList<Materials>) :
    RecyclerView.Adapter<MaterialsRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemListPastryMaterialsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = materials.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(materials[position])

    inner class CustomViewHolder(private val binding: RecyclerItemListPastryMaterialsBinding) :
        ViewHolder(binding.root) {
        fun setData(material: Materials) {
            binding.txtMaterial.text = material.material
            binding.txtAmount.text = material.amount

            if (adapterPosition % 2 != 0)
                binding.root.setBackgroundResource(R.color.white_color200)
        }
    }

}