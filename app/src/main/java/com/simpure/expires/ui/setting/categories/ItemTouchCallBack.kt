package com.simpure.expires.ui.setting.categories

import android.util.Log

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by jzman on 2017/5/17 0015.
 */
class ItemTouchCallBack : ItemTouchHelper.Callback() {

    private var mCanDrag = false
    private var onItemTouchListener: OnItemTouchListener? = null

    fun setOnItemTouchListener(onItemTouchListener: OnItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener
    }

    fun setCanDrag(canDrag: Boolean) {
        mCanDrag = canDrag
    }

    /**
     * 根据 RecyclerView 不同的布局管理器，设置不同的滑动、拖动方向
     * 该方法使用 makeMovementFlags(int dragFlags, int swipeFlags) 方法返回
     * 参数: dragFlags:拖动的方向
     * swipeFlags:滑动的方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        Log.i(TAG, "getMovementFlags")
        return if (recyclerView.layoutManager is GridLayoutManager || recyclerView.layoutManager is StaggeredGridLayoutManager) {
            //此处不需要进行滑动操作，可设置为除4和8之外的整数，这里设为0
            //不支持滑动
            makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0
            )
        } else {
            //如果是LinearLayoutManager则只能向上向下滑动，
            //此处第二个参数设置支持向右滑动
            makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT
            )
        }
    }

    /**
     * 当 ItemTouchHelper 拖动一个Item时该方法将会被回调，Item将从旧的位置移动到新的位置
     * 如果不拖动这个方法将从来不会调用,返回true表示已经被移动到新的位置
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.i(TAG, "onMove")
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        onItemTouchListener!!.onMove(fromPosition, toPosition)
        return true
    }

    /**
     * 当Item被滑动的时候被调用
     * 如果你不滑动这个方法将不会被调用
     * @param viewHolder
     * @param direction
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.i(TAG, "onSwiped")
        //此处是侧滑删除的主要代码
        val position = viewHolder.adapterPosition
        onItemTouchListener!!.onSwiped(position)
    }

    /**
     * 当Item被滑动、拖动的时候被调用
     * @param viewHolder
     * @param actionState
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        Log.i(TAG, "onSelectedChanged")
        //...
        super.onSelectedChanged(viewHolder, actionState)
    }

    /**
     * 当与用户交互结束或相关动画完成之后被调用
     * @param recyclerView
     * @param viewHolder
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        Log.i(TAG, "clearView")
        //...
        super.clearView(recyclerView, viewHolder)
    }

    /**
     * 长按拖拽开关
     * @return 代表是否开启长按拖拽
     */
    override fun isLongPressDragEnabled(): Boolean {
        return mCanDrag
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    /**
     * 移动交换数据的更新监听
     */
    interface OnItemTouchListener {
        //拖动Item时调用
        fun onMove(fromPosition: Int, toPosition: Int)

        //滑动Item时调用
        fun onSwiped(position: Int)

    }

    companion object {
        private val TAG = "drag"
    }
}


