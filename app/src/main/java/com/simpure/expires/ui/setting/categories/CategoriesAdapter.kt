package com.simpure.expires.ui.setting.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate


import androidx.recyclerview.widget.RecyclerView

import com.simpure.expires.R
import com.simpure.expires.databinding.ItemCategoriesBinding

import java.util.Collections

class CategoriesAdapter(private val context: Context, private val list: MutableList<String>) :
    RecyclerView.Adapter<CategoriesAdapter.DataViewHolder>(), View.OnClickListener,
    ItemTouchCallBack.OnItemTouchListener {

    private lateinit var mBinding: ItemCategoriesBinding
    private var recyclerView: RecyclerView? = null
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        mBinding = inflate(
            LayoutInflater.from(context), R.layout.item_categories,
            parent, false
        )
        mBinding.root.setOnClickListener(this)

        return DataViewHolder(mBinding.root)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        mBinding.name = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onClick(v: View) {
        if (onItemClickListener != null && recyclerView != null) {
            val position = recyclerView!!.getChildAdapterPosition(v)
            //程序执行到此，会执行该方法的具体实现
            onItemClickListener!!.onItemClick(recyclerView!!, v, position, list[position])
        }
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onSwiped(position: Int) {

        list.removeAt(position)
        notifyItemRemoved(position)
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        //参数（父组件，点击的View，位置，这里可能是某个对象的id或对象/这里不需要）
        fun onItemClick(recyclerView: RecyclerView, view: View, position: Int, obj: String)
    }

    /**
     * 将RecycleView附加到Adapter上
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    /**
     * 将RecycleView从Adapter解除
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    fun setCategoriesCanDrag(canDrag: Boolean) {
        mBinding.canDrag = canDrag
        notifyDataSetChanged()
    }
}
