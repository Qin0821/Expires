package com.simpure.expires.ui

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


import com.google.zxing.util.Constant.REQ_QR_CODE
import com.google.zxing.activity.CaptureActivity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.zxing.util.Constant
import com.orhanobut.dialogplus.DialogPlus
import com.simpure.expires.ui.commodity.CommodityAdapter
import com.simpure.expires.ui.commodity.CommodityHolder
import com.simpure.expires.utilities.toast
import com.simpure.expires.viewmodel.UserViewModel


class CommodityHomeActivity : BaseActivity() {


    override fun initData() {

    }

    override fun initView() {

    }

    private lateinit var fridgeCommodityList: List<CommodityRepository>
    private lateinit var makeUpsCommodityList: List<CommodityRepository>
    private lateinit var commodity: CommodityHomeRepository
    private lateinit var fragmentList: List<Fragment>

    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var navController: NavController


    private var lastPosition = -1


    private val internalPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            Log.d(javaClass.simpleName, "state: $state")
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

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
        mBinding = DataBindingUtil.setContentView(this, com.simpure.expires.R.layout.activity_home)
//        mBinding.vpCommodityList.adapter = BoxFragmentAdapter(supportFragmentManager, fragmentList)


        initPlaceNameList()

        initCommodityList(savedInstanceState)

//        commodity = CommodityHomeRepository(
//            User("qin", 0, 0L, 1L),
//            listOf(BoxRepository("box", commodityList))
//        )
//        mBinding.homeDTO = commodity


        /*if (mBinding.vpCommodityList.adapter != null) {

            val fragments = arrayListOf(
                PlaceFragment()
            )
            mBinding.vpCommodityList.adapter = CommodityFragmentAdapter(fragments, supportFragmentManager)
            lastPosition = -1
            createIndicators()
            mBinding.vpCommodityList.removeOnPageChangeListener(internalPageChangeListener)
            mBinding.vpCommodityList.addOnPageChangeListener(internalPageChangeListener)
            internalPageChangeListener.onPageSelected(mBinding.vpCommodityList.currentItem)
        }*/
//            setContentView(R.layout.activity_home)
//        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        mBinding.mainViewModel = mainViewModel
//        mBinding.setLifecycleOwner(this)

        initViewModel()
    }

    val userId = 1398762
    private fun initViewModel() {
        val factory = UserViewModel.Factory(application, userId)

        val model = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        mBinding.userViewModel = model
    }


    private fun initCommodityList(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val fragment = PlaceFragment()

            supportFragmentManager.beginTransaction()
                .add(com.simpure.expires.R.id.fcCommodity, fragment, fragment.TAG).commit()
        }
    }

    /** Shows the product detail fragment  */
    fun showCommodityDetail(commodity: com.simpure.expires.model.Commodity) {

        /*val commodityFragment = CommodityFragment().forCommodity(commodity.id)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack("commodity")
            .replace(
                R.id.fcCommodity,
                commodityFragment, null
            ).commit()*/

        val adapter = CommodityAdapter(commodity)

        val dialog = DialogPlus.newDialog(this)
            .setAdapter(adapter)
            .setContentHolder(CommodityHolder())
            .setOnClickListener { dialog, view ->

            }
            .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
            .create()
        dialog.show()
    }

    private fun initPlaceNameList() {
        /**
         * 1. 获取place列表
         * 2. 根据placeList创建CommodityListFragment
         */
        val fridgeList = arrayListOf(
            Commodity(10L, "薯条", 10, "根", 1),
            Commodity(11L, "雪碧", 2, "瓶", 1),
            Commodity(12L, "鸡腿", 2, "根", 1),
            Commodity(13L, "排骨", 1, "斤", 1),
            Commodity(14L, "酸奶", 2, "瓶", 1),
            Commodity(15L, "西瓜", 1, "个", 1)
        )

        val makeUpsList = arrayListOf(
            Commodity(20L, "dior", 2, "支", 2),
            Commodity(21L, "乳液", 1, "瓶", 2),
            Commodity(22L, "爽肤水", 1, "瓶", 2),
            Commodity(23L, "项链", 2, "个", 2),
            Commodity(24L, "手链", 1, "个", 2),
            Commodity(25L, "tf", 1, "支", 2)
        )

        val otherList = arrayListOf(
            Commodity(30L, "dior", 2, "支", 3),
            Commodity(31L, "乳液", 1, "瓶", 3),
            Commodity(32L, "爽肤水", 1, "瓶", 3),
            Commodity(33L, "项链", 2, "个", 3),
            Commodity(34L, "手链", 1, "个", 3),
            Commodity(35L, "tf", 1, "支", 3)
        )

        val commodityList = fridgeList + makeUpsList + otherList

        val placeList = arrayListOf(
            Place("All", commodityList, 0),
            Place("Fridge", fridgeList, 1),
            Place("makeUpsList", makeUpsList, 2),
            Place("Snacks", otherList, 3),
            Place("Icebox", otherList, 4),
            Place("Tea table", otherList, 5),
            Place("Night table", otherList, 6),
            Place("Chest", otherList, 7),
            Place("Safe", otherList, 8),
            Place("Mirror cabinet", otherList, 9),
            Place("Shoe rack", otherList, 10)
        )
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mBinding.rvLabels.layoutManager = layoutManager
        mBinding.rvLabels.adapter = PlaceNameAdapter(this, placeList)
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
            mBinding.btScan.text = scanResult
        }
    }
}
