package com.simpure.expires.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.simpure.expires.data.*
import com.simpure.expires.databinding.ActivityHomeBinding
import androidx.recyclerview.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.*
import com.google.zxing.util.Constant.REQ_QR_CODE
import com.google.zxing.activity.CaptureActivity
import android.content.Intent
import android.os.Build
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.zxing.util.Constant


class CommodityHomeActivity : BaseActivity() {


    override fun initData() {
//        fridgeCommodityList = listOf(
//            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
//            CommodityRepository("西瓜", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.ALMOST),
//            CommodityRepository("牛奶", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED),
//            CommodityRepository("鸡蛋", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
//            CommodityRepository("紫薯", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED)
//        )
//        makeUpsCommodityList = listOf(
//            CommodityRepository("口红", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
//            CommodityRepository("乳液", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.ALMOST),
//            CommodityRepository("卸妆水", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED),
//            CommodityRepository("精华", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
//            CommodityRepository("面霜", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED)
//        )

//        commodity = CommodityHomeRepository(
//            User("qin", 0, 0L, 1L),
//            listOf(
//                BoxRepository("fridge", fridgeCommodityList),
//                BoxRepository("Make-ups", makeUpsCommodityList)
//            )
//        )
//        binding.homeDTO = commodity

//        commodity.boxes.forEach {
//
//        }

        fragmentList = listOf(
            PlaceFragment()
        )
    }

    override fun initView() {

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
        binding = DataBindingUtil.setContentView(this, com.simpure.expires.R.layout.activity_home)
        binding.vpCommodityList.adapter = BoxFragmentAdapter(supportFragmentManager, fragmentList)


        /**
         * 1. 获取place列表
         * 2. 根据placeList创建CommodityListFragment
         */
        val commodityList = arrayListOf(
            Commodity("薯条", 10, 0, 1, 3, 2)
        )

        val placeList = arrayListOf(
            Place("All", commodityList, 0),
            Place("Fridge", commodityList, 0),
            Place("Make-ups", commodityList, 0),
            Place("Snacks", commodityList, 0),
            Place("Icebox", commodityList, 0),
            Place("Tea table", commodityList, 0),
            Place("Night table", commodityList, 0),
            Place("Chest", commodityList, 0),
            Place("Safe", commodityList, 0),
            Place("Mirror cabinet", commodityList, 0),
            Place("Shoe rack", commodityList, 0)
        )
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvLabels.layoutManager = layoutManager
        binding.rvLabels.adapter = PlaceNameAdapter(this, placeList)

//        commodity = CommodityHomeRepository(
//            User("qin", 0, 0L, 1L),
//            listOf(BoxRepository("box", commodityList))
//        )
//        binding.homeDTO = commodity


        if (binding.vpCommodityList.adapter != null) {

            val fragments = arrayListOf(
                PlaceFragment()
            )
            binding.vpCommodityList.adapter = CommodityFragmentAdapter(fragments, supportFragmentManager)
            lastPosition = -1
//            createIndicators()
            binding.vpCommodityList.removeOnPageChangeListener(internalPageChangeListener)
            binding.vpCommodityList.addOnPageChangeListener(internalPageChangeListener)
            internalPageChangeListener.onPageSelected(binding.vpCommodityList.currentItem)
        }
//            setContentView(R.layout.activity_home)
//        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        binding.mainViewModel = mainViewModel
//        binding.setLifecycleOwner(this)
    }

    fun changeText(view: View) {
//        binding.homeDTO = CommodityHomeRepository(
//            User("ting", 0, 0L, 1L),
//            listOf(BoxRepository("box", fridgeCommodityList))
//        )
    }

    fun scan(view: View) {
        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { shouldRequest -> ToastUtils.showShort(shouldRequest.toString()) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    val intent = Intent(this@CommodityHomeActivity, CaptureActivity::class.java)
                    startActivityForResult(intent, REQ_QR_CODE)
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        ToastUtils.showShort("showOpenAppSettingDialog")
                        return
                    }
                    ToastUtils.showShort("requestCamera")
                }
            })
            .theme { activity -> ScreenUtils.setFullScreen(activity) }
            .request()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            val bundle = data?.extras
            val scanResult = bundle?.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN)
            binding.btScan.text = scanResult
        }
    }
}
