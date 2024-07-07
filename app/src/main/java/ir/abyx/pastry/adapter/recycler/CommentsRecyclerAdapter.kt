package ir.abyx.pastry.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.data.remote.dataModel.Comments
import ir.abyx.pastry.databinding.RecyclerItemCommentsBinding

class CommentsRecyclerAdapter(private val comments: ArrayList<Comments>) :
    RecyclerView.Adapter<CommentsRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemCommentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(comments[position])

    inner class CustomViewHolder(private val binding: RecyclerItemCommentsBinding) :
        ViewHolder(binding.root) {
        fun setData(comment: Comments) {
            binding.txtName.text = comment.name
            binding.txtRate.text = comment.rate.toString()
            binding.dateTime.text = comment.date_i18n
            binding.txtContent.text = comment.body
        }
    }

}