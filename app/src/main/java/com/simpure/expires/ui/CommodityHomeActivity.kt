package com.simpure.expires.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.databinding.ActivityHomeBinding
import androidx.recyclerview.widget.LinearLayoutManager


import com.google.zxing.util.Constant.REQ_QR_CODE
import com.google.zxing.activity.CaptureActivity
import android.content.Intent
import android.graphics.Rect
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.simpure.expires.api.SignInApiService
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.utilities.toast
import com.simpure.expires.viewmodel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.*
import com.google.zxing.util.BarcodeGenerator
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.interfaces.SimpleCallback
import com.simpure.expires.BR
import com.simpure.expires.BasicApp
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.GroupEntity
import com.simpure.expires.ui.commodity.InventoryAdapter
import com.simpure.expires.utilities.getCompatColor
import com.simpure.expires.view.popup.ConsumingPopup
import com.simpure.expires.view.popup.InventoriesPopup
import com.simpure.expires.view.popup.PlacePopup
import com.simpure.expires.view.scrollview.ExpiresScrollView
import com.simpure.expires.view.scrollview.ScrollViewListener
import com.simpure.expires.viewmodel.CommodityDetailViewModel
import com.simpure.expires.viewmodel.CommodityHome2ViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_commodity.*
import kotlinx.android.synthetic.main.dialog_commodity.view.*
import kotlinx.android.synthetic.main.item_dialog_commodity_consuming.*
import kotlinx.android.synthetic.main.item_dialog_commodity_consuming.view.*
import kotlinx.android.synthetic.main.item_dialog_commodity_inventories.*
import kotlinx.android.synthetic.main.item_navigation.*
import kotlinx.android.synthetic.main.item_search.*
import kotlin.concurrent.thread


class CommodityHomeActivity : BaseActivity() {

    override fun onClick(v: View?) {
        when (v) {
            tvCommClear -> {
                showEditPopup(v!!, ConsumingPopup(this))
            }
            ivInventoriesTopping -> {
                showEditPopup(v!!, InventoriesPopup(this, "topping"))
            }
            tvInventoriesThrow -> {
                showEditPopup(v!!, InventoriesPopup(this, "throw"))
            }
            ivInventories -> {
                this@CommodityHomeActivity.toast("ivInventories")
            }
            ivConsuming -> {
                this@CommodityHomeActivity.toast("ivConsuming")
            }
            ivEdit -> {
                this@CommodityHomeActivity.toast("ivEdit")
            }
            rlSearch -> {
                startSearch(v!!)
            }
            tvSearchCancel -> {
                cancelSearch()
            }
            tvAll -> {
                mBinding.setVariable(BR.placeName, mSelectPlace)
                mBinding.setVariable(BR.notAll, false)
                mCommodityHomeViewModel.setPlaceName("All")
            }
        }
    }

    fun startSearch(view: View) {
        // view width
        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_left)
//        mBinding.itemSearch.ivSearch.startAnimation(translateAnimation)
//        mBinding.itemSearch.tvSearchCancel.fadeIn()
//        mBinding.itemSearch.tvSearch.fadeOut()
//131 184
// 145 24
//        val anim = ValueAnimator.ofInt(ConvertUtils.dp2px(131f), ConvertUtils.dp2px(10f))
        val lp = view.layoutParams as RelativeLayout.LayoutParams
        val tvAnim = ValueAnimator.ofInt(ConvertUtils.dp2px(145f), ConvertUtils.dp2px(24f))
        tvAnim.duration = 360
        tvAnim.addUpdateListener {
            val currentValue = it.animatedValue

        }
        val rlAnim = ValueAnimator.ofInt(ConvertUtils.dp2px(332f), ConvertUtils.dp2px(265f))
        rlAnim.duration = 360
        rlAnim.addUpdateListener {
            val currentValue = it.animatedValue
            lp.width = currentValue as Int
            view.layoutParams = lp
        }
//        rlAnim.start()

        val animSet = AnimatorSet()
        animSet.play(rlAnim)
//            .with()
        animSet.duration = 360
        animSet.start()
    }

    fun cancelSearch() {
        // view width
//        mBinding.itemSearch.rlSearch
//        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_right)
//        mBinding.itemSearch.ivSearch.startAnimation(translateAnimation)
//        mBinding.itemSearch.tvSearchCancel.fadeOut()
//        mBinding.itemSearch.tvSearch.fadeIn()

//        val lp = mBinding.itemSearch.rlSearch.layoutParams as RelativeLayout.LayoutParams
//        val rlAnim = ValueAnimator.ofInt(ConvertUtils.dp2px(265f), ConvertUtils.dp2px(322f))
//        rlAnim.duration = 360
//        rlAnim.addUpdateListener {
//            val currentValue = it.animatedValue
//            lp.width = currentValue as Int
//            mBinding.itemSearch.rlSearch.layoutParams = lp
//        }
    }

    private lateinit var mPlaceNamePopup: BasePopupView

    private fun showEditPopup(
        view: View,
        popup: PositionPopupView,
        callback: SimpleCallback? = null,
        backgroundRes: Int = R.color.transparency_90
    ) {
        val location = IntArray(2)
        view.getLocationInWindow(location)
//        tvInventoriesThrow.getLocationInWindow(location)

        XPopup.setShadowBgColor(getCompatColor(backgroundRes))

        mPlaceNamePopup =
            XPopup
                .Builder(this)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .offsetY(location[1])
                .setPopupCallback(callback)
                .asCustom(popup)
                .show()
    }


    override fun initData() {

    }

    override fun initView() {
    }

    private lateinit var mBinding: ActivityHomeBinding

    private lateinit var mSelectPlace: String
    private lateinit var mPlaceList: List<String>


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setActivityTheme(mBinding.relativeLayout)

        mBinding.itemCommodity.itemConsuming.tvCommClear.setOnClickListener(this)
        mBinding.itemCommodity.itemInventories.ivInventoriesTopping.setOnClickListener(this)
        mBinding.itemCommodity.itemInventories.tvInventoriesThrow.setOnClickListener(this)
//        mBinding.itemSearch.rlSearch.setOnClickListener(this)
//        mBinding.itemSearch.tvSearchCancel.setOnClickListener(this)
        mBinding.itemNavigation.ivInventories.setOnClickListener(this)
        mBinding.itemNavigation.ivConsuming.setOnClickListener(this)
        mBinding.itemNavigation.ivEdit.setOnClickListener(this)

        mBinding.tvAll.setOnClickListener(this)
        mBinding.tvPlace.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    showPlacePopup(
                        mBinding.tvPlace,
                        mPlaceList
                    )
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveX = event.rawX
                    val moveY = event.rawY
                    mPlacePopup.setMoveXY(moveX, moveY)
                }
                MotionEvent.ACTION_UP -> {
                    mPlaceNamePopup.dismiss()
                    mSelectPlace =
                        if (mPlacePopup.getSelectPlace().isEmpty()) mSelectPlace else mPlacePopup.getSelectPlace()

                    try {
                        mBinding.setVariable(BR.placeName, mSelectPlace)
                        mBinding.setVariable(BR.notAll, true)
                        mCommodityHomeViewModel.setPlaceName(mSelectPlace)
                    } catch (e: Exception) {
                        Log.e(javaClass.simpleName, "can not get name from empty place")
                    }
                }
                else -> {

                }
            }
            return@setOnTouchListener true
        }

        initCommodityList(savedInstanceState)

    }

    private lateinit var mCommodityHomeViewModel: CommodityHome2ViewModel

    private fun initGroupHome(group: GroupEntity) {

        if (!::mSelectPlace.isInitialized) {
            mSelectPlace = group.placeList[0]
            mBinding.setVariable(BR.placeName, mSelectPlace)
            mBinding.setVariable(BR.notAll, false)
        }
        if (!::mPlaceList.isInitialized) mPlaceList = group.placeList



        mCommodityHomeViewModel = ViewModelProvider(this).get(CommodityHome2ViewModel::class.java)
        mCommodityHomeViewModel.commodityHome.observe(this, Observer {
            if (null == it) return@Observer

            placeFragment.setCommoditiesSummary(it.commoditySummaryList)
        })
    }

    private lateinit var mPlacePopup: PlacePopup

    private fun showPlacePopup(
        view: View,
        placeList: List<String>
    ) {
        val location = IntArray(2)
        view.getLocationInWindow(location)

        XPopup.setShadowBgColor(getCompatColor(R.color.transparency))
        mPlacePopup = PlacePopup(this)
        mPlacePopup.setPlaceList(placeList)

        Log.e(javaClass.simpleName, (location[0] - ConvertUtils.dp2px(14f)).toString())
        Log.e(javaClass.simpleName, (location[1] - ConvertUtils.dp2px(10f)).toString())

        mPlaceNamePopup =
            XPopup
                .Builder(this)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .offsetX(location[0] - ConvertUtils.dp2px(14f))
                .offsetY(location[1] - ConvertUtils.dp2px(10f))
                .setPopupCallback(object : SimpleCallback() {
                    override fun onCreated() {
                        super.onCreated()
                    }

                    override fun beforeShow() {
                        super.beforeShow()
                        val xLimit = arrayOf(
                            location[0] - ConvertUtils.dp2px(14f),
                            location[0] - ConvertUtils.dp2px(14f) + ConvertUtils.dp2px(176f)
                        )
                        val yLimit = arrayOf(
                            location[1] - ConvertUtils.dp2px(10f),
                            location[1] - ConvertUtils.dp2px(10f) + ConvertUtils.dp2px(188f)
                        )
                        Log.e(javaClass.simpleName, "x0: ${xLimit[0]}, x1: ${xLimit[1]}")
                        Log.e(javaClass.simpleName, "y0: ${yLimit[0]}, y1: ${yLimit[1]}")
                        mPlacePopup.setMoveLimit(xLimit, yLimit)
                    }

                    override fun onShow() {
                        super.onShow()
                    }

                    override fun onDismiss() {
                        super.onDismiss()

                    }

                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                    override fun onBackPressed(): Boolean {
                        return true; //默认返回false
                    }
                })
                .asCustom(mPlacePopup)
                .show()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        initBottomSheet()
    }


    private fun executeAnimation(height: Int, llt: LinearLayout) {
        if (height < 0) return
        var p: Int = 0
        var s: Int = 0
        if (llt.height != 0) {
            p = height
            s = 0
        } else {
            s = height
            p = 0
        }

        val lp = llt.layoutParams as LinearLayout.LayoutParams
        val animator = ValueAnimator.ofInt(p, s)

        // 此方法会随用户的点击而调用, 所以不要用内部类的形式创建, 我这里只是节省代码量, 增加阅读性(捂脸)
        animator.addUpdateListener { animation ->
            val animatedValue: Int = animation.animatedValue as Int
            lp.height = animatedValue
            llt.layoutParams = lp
        }

        animator.duration = 500
        animator.start()
    }

    var lastX = 0f
    var lastY = 0f

    private var isScrollToStart = true
    private var justExpanded = false

    private fun initBottomSheet() {
        if (!::mBottomSheetBehavior.isInitialized) {
            viewShadow.setOnClickListener {
                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                viewShadow.visibility = View.GONE
            }
            mBottomSheetBehavior = BottomSheetBehavior.from(itemCommodity)
            mBottomSheetBehavior.setBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    setCommodityHeight(slideOffset)
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            viewShadow.visibility = View.VISIBLE

                            if (collapsedHeight == 0) {
                                val rect = Rect()
                                itemCommodity.getLocalVisibleRect(rect)
                                collapsedHeight = rect.bottom
                            }
                            setCommodityHeight(0f)

                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {


                            viewShadow.visibility = View.VISIBLE

                            // 在全屏状态，scrollview顶部不可见时，屏蔽sheet滑动事件
                            Log.e(javaClass.simpleName, "justExpanded: $justExpanded")
                            if (justExpanded) {
                                if (isScrollToStart) {
                                    Log.e(javaClass.simpleName, "isScrollToStart: $isScrollToStart")
                                    // 可以滑
//                                    mBottomSheetBehavior.isHideable = true
                                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_DRAGGING
                                } else {
                                    // bu
                                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                                }
                            }
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            viewShadow.visibility = View.VISIBLE

                            justExpanded = true
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            viewShadow.visibility = View.GONE
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            viewShadow.visibility = View.VISIBLE
                        }
                    }
                }

            })
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            svCommodity.setScrollViewListener(object : ScrollViewListener {
                override fun onScrollChanged(
                    scrollView: ExpiresScrollView,
                    x: Int,
                    y: Int,
                    oldX: Int,
                    oldY: Int
                ) {
                    isScrollToStart =
                        svCommodity.scrollY == 0
                }
            })
            itemCommodity.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {

                        val yDistance = event.y - lastY

                        Log.e(javaClass.simpleName, itemCommodity.height.toString())

                        setCommodityHeight(yDistance)
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                    else -> {
                    }
                }
                return@setOnTouchListener true
            }
        }
        mCommodityDetailViewModel =
            ViewModelProvider(this).get(CommodityDetailViewModel::class.java)

        mCommodityDetailViewModel.commodityDetail.observe(this, Observer {
            if (null == it) return@Observer

            // 给 commodity sheet 设置数据
            mBinding.setVariable(BR.commodityDetail, it)

            showInventories(it)
            showBarcode(it)
        })

    }

    var collapsedHeight = 0

    private fun setCommodityHeight(slideOffset: Float) {
        if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) return


        val height = if (slideOffset > 0) {
            (slideOffset * ConvertUtils.dp2px(44f)).toInt()
        } else 0
        val lpC = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            height
        )
//        itemCommodity.itemConsuming.layoutConsume.visibility =
//            if (ConvertUtils.px2dp(height.toFloat()) < 20) View.INVISIBLE else View.VISIBLE
        itemCommodity.itemConsuming.layoutConsume.alpha = (slideOffset - 1)
        itemCommodity.itemConsuming.layoutConsume.layoutParams = lpC

        /*val minHeight = ConvertUtils.dp2px(100f)
        val height =
            if (slideOffset > 0) {
                (slideOffset * extensibleHeight).toInt() + minHeight
            } else minHeight
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            height
        )
        itemCommodity.llCommDetail.layoutParams = lp*/

        val extensibleHeight = itemCommodity.height - collapsedHeight

        val marginBottomHeight = if (slideOffset > 0) {
            ((1 - slideOffset) * extensibleHeight).toInt()
        } else extensibleHeight
        val lp = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, 0, marginBottomHeight + 40)
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        itemCommodity.itemCommodityNavigation.layoutParams = lp

//        Log.e(javaClass.simpleName, "h: $marginBottomHeight")
//        Log.e(javaClass.simpleName, "itemCommodity.height: ${itemCommodity.height}")
        val rect = Rect()
        itemCommodity.getLocalVisibleRect(rect)
//        Log.e(javaClass.simpleName, "rect: $rect")
        Log.e(javaClass.simpleName, "height: $height")
        Log.e(javaClass.simpleName, "height: ${ConvertUtils.px2dp(height.toFloat())}")
        Log.e(javaClass.simpleName, "slideOffset: $slideOffset")
    }

    override fun onStart() {
        super.onStart()
        initUserViewModel()
    }

    val userId = 1398762
    private val mObservableUsers: MediatorLiveData<List<UserEntity>> = MediatorLiveData()
    private fun initUserViewModel() {
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.setUserId(userId)
        viewModel.commodities.observe(this,
            Observer { user ->
                if (null != user) {
                    toast(user.name)
                    getUserGroup(user.groupIdList)
                } else {
                    toast("user is empty")
                }
            })
    }

    private val mObservableGroup: MediatorLiveData<GroupEntity> = MediatorLiveData()

    private fun getUserGroup(groupIdList: List<String>) {

        if (groupIdList.isNotEmpty()) {
            // 目前不存在多group情况
//            val group = (application as BasicApp).repository.loadGroupById(groupIdList[0])
            thread {

                val group =
                    (application as BasicApp).database.groupDao().loadGroupById(groupIdList[0])
                runOnUiThread {
                    initGroupHome(group)
                }
            }

//            mObservableGroup.value = null
//            mObservableGroup.addSource(group) {
//
////                initGroupHome(it)
//                Log.e(javaClass.simpleName, it.toString())
//            }

        }
    }

    val placeFragment = PlaceFragment()

    private fun initCommodityList(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {


            supportFragmentManager.beginTransaction()
                .add(R.id.fcCommodity, placeFragment, placeFragment.TAG).commit()
        }
    }

    private fun showInventories(it: CommodityEntity) {
        if (!it.inventories.isNullOrEmpty()) {
            with(mBinding.itemCommodity.itemInventories.rvInventories) {
                if (null == layoutManager) {
                    layoutManager = object : LinearLayoutManager(
                        this@CommodityHomeActivity,
                        VERTICAL, false
                    ) {
                        override fun canScrollVertically(): Boolean = false
                    }
                    this.layoutManager = layoutManager
                }
                if (null == adapter) {
                    this.isNestedScrollingEnabled = false
                    adapter = InventoryAdapter()
                }

                (adapter as InventoryAdapter).setInventoryList(it.inventories)
            }
        }
    }

    private fun showBarcode(it: CommodityEntity) {
        if (it.barcode.isNotEmpty()) {
            thread {
                with(mBinding.itemCommodity.itemBarcode.ivBarcode) {
                    val barcodeBitmap =
                        BarcodeGenerator.getBarcodeImage(it.barcode, this.width, this.height)
                    runOnUiThread {
                        // 上面的width和height会莫名其妙变成0导致这里报错
                        // 先不处理
                        // 如果app在其他地方必须用到sd卡读取权限
                        // 这里做sd卡的缓存
                        // 如果没有必要的sd卡权限，这里做成存数据库
                        // http://ddrv.cn/a/119750
//                        if (null == barcodeBitmap) {
//                            parent!!.context.toast("条形码解析失败")
//                        }
                        this.setImageBitmap(barcodeBitmap)
                    }
                }
            }

        }
    }

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mCommodityDetailViewModel
            : CommodityDetailViewModel

    /** Shows the product detail placeFragment  */
    fun showCommodityDetail(commodityId: Int, sheetVersion: Boolean = true) {

//        if (sheetVersion) {
        mCommodityDetailViewModel.setCommodityId(commodityId)
        mBottomSheetBehavior.state = when (mBottomSheetBehavior.state) {
            BottomSheetBehavior.STATE_HIDDEN -> {
                BottomSheetBehavior.STATE_COLLAPSED
            }
            BottomSheetBehavior.STATE_COLLAPSED or BottomSheetBehavior.STATE_EXPANDED -> BottomSheetBehavior.STATE_HIDDEN
            else -> BottomSheetBehavior.STATE_COLLAPSED
        }
//        } else {
//            val adapter = CommodityAdapter(this, commodityId)
//
//            val dialog = DialogPlus.newDialog(this)
//                .setAdapter(adapter)
//                .setContentHolder(CommodityHolder())
//                .setOnClickListener { dialog, view -> }
//                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
//                .create()
//            dialog.show()
//        }
    }

    fun goInventories(view: View) {
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

    private val signInApiService by lazy {
        SignInApiService.create()
    }
    var disposable: Disposable? = null

    fun goConsuming(view: View) {
        disposable =
            signInApiService.scanQRCode(50)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        this.toast(result.toString())
                    },
                    { error ->
                        this.toast(error.toString())
                    }
                )

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBottomSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            return false
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 扫描结果回调
        if (requestCode == REQ_QR_CODE && resultCode == RESULT_OK) {
//            val bundle = data?.extras
//            val scanResult = bundle?.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN)
//            mBinding.btInventories.text = scanResult
        }
    }
}
