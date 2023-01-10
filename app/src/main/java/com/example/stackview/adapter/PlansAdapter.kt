package com.example.stackview.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stackview.R
import com.example.stackview.databinding.SquareCardViewBinding
import com.example.stackview.model.Plans

class PlansAdapter : ListAdapter<Plans, PlansAdapter.PlansViewHolder>(PLANS_COMPARATOR) {

    private var context: Context? = null

    var visibleCheckPosition: Int = -1
        private set

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlansAdapter.PlansViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SquareCardViewBinding.inflate(inflater, parent, false)
        context = parent.context
        return PlansViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: PlansAdapter.PlansViewHolder, position: Int) {
        val plans = getItem(position)
        holder.bind(plans, position == visibleCheckPosition)
    }


    inner class PlansViewHolder(
        private val binding: SquareCardViewBinding,
        private val adapter: PlansAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardLayout.setOnClickListener {
                if (adapter.visibleCheckPosition == adapterPosition) {
                    adapter.hideCheck()
                } else {
                    adapter.viewCheck(adapterPosition)
                }
            }
        }

        fun bind(item: Plans, hidden: Boolean) {

            binding.checkbox.visibility = if (hidden) View.VISIBLE else View.GONE
            binding.cardLayout.setCardBackgroundColor(item.color)
            binding.checkIcon.setCardBackgroundColor(item.color)
            binding.recommendedChip.visibility = if (item.isRecommended) View.VISIBLE else View.INVISIBLE

            binding.textViewAmount.text =
                binding.textViewAmount.context.getString(R.string.rupees, item.price)
            binding.durationTextView.text =
                binding.durationTextView.context.getString(R.string.for_months, item.duration)

            binding.textViewAmount.text = item.price.toString()
        }
    }

    fun hideCheck() {
        if (visibleCheckPosition >= 0) {
            val prev = visibleCheckPosition
            visibleCheckPosition = -1
            notifyItemChanged(prev)
        }
    }

    fun viewCheck(position: Int) {
        if (visibleCheckPosition >= 0) {
            val prev = visibleCheckPosition
            visibleCheckPosition = -1
            notifyItemChanged(prev)
        }
        visibleCheckPosition = position
        notifyItemChanged(position)
    }


    companion object {
        val PLANS_COMPARATOR = object : DiffUtil.ItemCallback<Plans>() {
            override fun areItemsTheSame(
                oldItem: Plans,
                newItem: Plans
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Plans,
                newItem: Plans
            ): Boolean =
                oldItem == newItem
        }
    }


}