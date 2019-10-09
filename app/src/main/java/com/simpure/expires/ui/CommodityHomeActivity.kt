package com.simpure.expires.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.data.*
import com.simpure.expires.databinding.ActivityHomeBinding
import androidx.recyclerview.widget.LinearLayoutManager


import com.google.zxing.util.Constant.REQ_QR_CODE
import com.google.zxing.activity.CaptureActivity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.zxing.util.Constant
import com.orhanobut.dialogplus.DialogPlus
import com.simpure.expires.R
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.ui.commodity.CommodityAdapter
import com.simpure.expires.ui.commodity.CommodityHolder
import com.simpure.expires.utilities.toast
import com.simpure.expires.viewmodel.UserViewModel


class CommodityHomeActivity : BaseActivity() {


    override fun initData() {

    }

    override fun initView() {

    }

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initPlaceNameList()

        initCommodityList(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    val userId = 1398762
    private val mObservableUsers: MediatorLiveData<List<UserEntity>> = MediatorLiveData()
    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        subscribeUi(viewModel.commodities)
    }

    private fun subscribeUi(liveData: LiveData<List<UserEntity>>) {
        liveData.observe(this,
            Observer { user ->
                if (!user.isNullOrEmpty()) {
                    toast(user[0].name)
                } else {
                    toast("user is empty")
                }
            })
    }


    private fun initCommodityList(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val fragment = PlaceFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fcCommodity, fragment, fragment.TAG).commit()
        }
    }

    /** Shows the product detail fragment  */
    fun showCommodityDetail(commodityId: Int) {

        val adapter = CommodityAdapter(this, commodityId)

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
        if (requestCode == REQ_QR_CODE && resultCode == RESULT_OK) {
            val bundle = data?.extras
            val scanResult = bundle?.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN)
            mBinding.btInventories.text = scanResult
        }
    }
}
