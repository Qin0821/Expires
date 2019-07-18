package com.simpure.expires.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.simpure.expires.R
import com.simpure.expires.data.*
import com.simpure.expires.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.*


class CommodityHomeActivity : BaseActivity() {


    override fun initData() {
        fridgeCommodityList = listOf(
            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
            CommodityRepository("西瓜", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.ALMOST),
            CommodityRepository("牛奶", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED),
            CommodityRepository("鸡蛋", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
            CommodityRepository("紫薯", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED)
        )
        makeUpsCommodityList = listOf(
            CommodityRepository("口红", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
            CommodityRepository("乳液", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.ALMOST),
            CommodityRepository("卸妆水", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED),
            CommodityRepository("精华", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
            CommodityRepository("面霜", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED)
        )

        commodity = CommodityHomeRepository(
            User("qin", 0, 0L, 1L),
            listOf(
                BoxRepository("fridge", fridgeCommodityList),
                BoxRepository("Make-ups", makeUpsCommodityList)
            )
        )
        binding.homeDTO = commodity

        commodity.boxes.forEach {

        }
    }

    override fun initView() {
        vpCommodityList.adapter = BoxFragmentAdapter(supportFragmentManager, fragmentList)
    }

    private lateinit var fridgeCommodityList: List<CommodityRepository>
    private lateinit var makeUpsCommodityList: List<CommodityRepository>
    private lateinit var commodity: CommodityHomeRepository
    private lateinit var fragmentList: List<Fragment>

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController


    private var lastPosition = -1


    private val internalPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            Log.d(javaClass.simpleName, "state: $state")
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            /*if (vpCommodityList.adapter == null || vpCommodityList.adapter.count <= 0) return

            if (mAnimatorIn.isRunning()) {
                mAnimatorIn.end()
                mAnimatorIn.cancel()
            }

            if (mAnimatorOut.isRunning()) {
                mAnimatorOut.end()
                mAnimatorOut.cancel()
            }

            val currentIndicator: View
            if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {//页面离开屏幕时指示器动画
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId)
                mAnimatorIn.setTarget(currentIndicator)
                mAnimatorIn.start()
            }

            val selectedIndicator = getChildAt(position)
            if (selectedIndicator != null) {//页面进入屏幕时指示器动画
                selectedIndicator!!.setBackgroundResource(mIndicatorBackgroundResId)
                mAnimatorOut.setTarget(selectedIndicator)
                mAnimatorOut.start()
            }
            lastPosition = position*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)


        commodity = CommodityHomeRepository(
            User("qin", 0, 0L, 1L),
            listOf(BoxRepository("box", commodityList))
        )
        binding.homeDTO = commodity

        val placeList = arrayListOf(
            Place("000", 0),
            Place("111", 1),
            Place("222", 2),
            Place("333", 3),
            Place("444", 4),
            Place("555", 5)
        )
        rlvBoxes.adapter = PlaceNameAdapter(placeList)
        rlvBoxes.setOffscreenItems(3)

        if (vpCommodityList.adapter != null) {
            vpCommodityList.adapter = CommodityFragmentAdapter(fragments)
            lastPosition = -1
//            createIndicators()
            vpCommodityList.removeOnPageChangeListener(internalPageChangeListener)
            vpCommodityList.addOnPageChangeListener(internalPageChangeListener)
            internalPageChangeListener.onPageSelected(vpCommodityList.currentItem)
        }
//            setContentView(R.layout.activity_home)
//        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        binding.mainViewModel = mainViewModel
//        binding.setLifecycleOwner(this)
    }

    fun changeText(view: View) {
        binding.homeDTO = CommodityHomeRepository(
            User("ting", 0, 0L, 1L),
            listOf(BoxRepository("box", fridgeCommodityList))
        )
    }

    private fun getExpiredList(): List<CommodityRepository> {

        return fridgeCommodityList.filter { it.type == CommodityEnum.EXPIRED }
    }

    private fun getAtmostList(): List<CommodityRepository> {

        return fridgeCommodityList.filter { it.type == CommodityEnum.ALMOST }
    }

    private fun getFreshtList(): List<CommodityRepository> {
        return fridgeCommodityList.filter { it.type == CommodityEnum.FRESH }
    }

}
