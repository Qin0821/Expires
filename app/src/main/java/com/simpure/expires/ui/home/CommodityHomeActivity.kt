package com.simpure.expires.ui.home


import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.PopupWindowCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.activity.CaptureActivity
import com.google.zxing.util.BarcodeGenerator
import com.google.zxing.util.Constant.REQ_QR_CODE
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.interfaces.SimpleCallback
import com.simpure.expires.BasicApp
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.GroupEntity
import com.simpure.expires.databinding.ActivityHomeBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.ui.commodity.InventoryAdapter
import com.simpure.expires.ui.search.SearchActivity
import com.simpure.expires.utilities.getCompatColor
import com.simpure.expires.utilities.startAct
import com.simpure.expires.utilities.toast
import com.simpure.expires.view.popup.*
import com.simpure.expires.view.scrollview.ExpiresScrollView
import com.simpure.expires.view.scrollview.ScrollViewListener
import com.simpure.expires.viewmodel.CommodityDetailViewModel
import com.simpure.expires.viewmodel.CommodityHome2ViewModel
import com.simpure.expires.viewmodel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_commodity.*
import kotlinx.android.synthetic.main.item_commodity.view.*
import kotlinx.android.synthetic.main.item_dialog_commodity_consuming.*
import kotlinx.android.synthetic.main.item_dialog_commodity_consuming.view.*
import kotlinx.android.synthetic.main.item_dialog_commodity_inventories.*
import kotlinx.android.synthetic.main.item_navigation.*
import kotlinx.android.synthetic.main.item_search.*
import kotlin.concurrent.thread
import kotlin.math.abs


class CommodityHomeActivity : BaseActivity(), View.OnTouchListener {


    private lateinit var mPlaceNamePopup: BasePopupView

    private lateinit var mBinding: ActivityHomeBinding

    private lateinit var mSelectPlace: String

    private lateinit var mPlaceList: List<String>

    private lateinit var mCommodityHomeViewModel: CommodityHome2ViewModel

    private lateinit var mPlacePopup: PlacePopup

    private lateinit var mCommodityDetail: CommodityEntity

    var lastX = 0f
    var lastY = 0f

    private var isScrollToStart = true

    private var justExpanded = false

    var collapsedHeight = 0

    var lastCommodityListY = 0f

    val userId = 1398762

    private val placeFragment = PlaceFragment()
    private val accountFragment = AccountFragment()

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mCommodityDetailViewModel: CommodityDetailViewModel

    var disposable: Disposable? = null

    private lateinit var titleArray: Array<String>

    private var lastBehavior = BottomSheetBehavior.STATE_HIDDEN

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            setCommodityHeight(slideOffset)
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    Log.e("AAA", "STATE_COLLAPSED")

                    mBinding.showBottomSheet = true

                    if (collapsedHeight == 0) {
                        val rect = Rect()
                        itemCommodity.getLocalVisibleRect(rect)
                        collapsedHeight = rect.bottom
                    }
                    setCommodityHeight(0f)

                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                    Log.e("AAA", "STATE_DRAGGING")

//                    mBinding.showBottomSheet = true

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
                    Log.e("AAA", "STATE_HALF_EXPANDED")

                }
                BottomSheetBehavior.STATE_EXPANDED -> {
//                    mBinding.showBottomSheet = true
                    Log.e("AAA", "STATE_EXPANDED")

                    justExpanded = true
                }
                BottomSheetBehavior.STATE_HIDDEN -> {
                    Log.e("AAA", "STATE_HIDDEN")
                    mBinding.isHome = true
                    mBinding.showBottomSheet = false
                    setExpiresTheme()
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                    if (mClickShadowToHidden) {
                        mBinding.showBottomSheet = false
                        mClickShadowToHidden = false
                    } else if (lastBehavior == BottomSheetBehavior.STATE_HIDDEN) {
                        mBinding.showBottomSheet = true
                    }
//                    else if (lastBehavior == BottomSheetBehavior.STATE_COLLAPSED) {
//                        mBinding.showBottomSheet = false
//                    }
                    Log.e("AAA", "STATE_SETTLING")

//                    mBinding.showBottomSheet = true
                }
            }
        }
    }

    private val svCommodityScrollListener = object : ScrollViewListener {
        override fun onScrollChanged(
            scrollView: ExpiresScrollView,
            x: Int,
            y: Int,
            oldX: Int,
            oldY: Int
        ) {
            isScrollToStart = svCommodity.scrollY == 0
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setExpiresTheme()

        initData()
        initView()
    }

    private fun initData() {
        titleArray = arrayOf(getString(R.string.app_name), getString(R.string.account))
    }

    private fun initView() {
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            BarUtils.getStatusBarHeight() + ConvertUtils.dp2px(89f)
        )
        rlCommodityHomeTop.layoutParams = lp

        mBinding.showPopup = false
        mBinding.isHome = true

        initFragment()
        initBottomSheet()
        initListener()
    }

    private fun initFragment() {
        mBinding.title = titleArray[0]

//        supportFragmentManager.beginTransaction()
//            .add(R.id.fcCommodity, placeFragment, placeFragment.TAG).commit()

        val pagerAdapter = ScreenSlidePagerAdapter(this, placeFragment, accountFragment)
        vpHome.adapter = pagerAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {

        mBinding.itemCommodity.itemConsuming.tvCommClear.setOnClickListener(this)
        mBinding.itemCommodity.itemInventories.ivInventoriesTopping.setOnClickListener(this)
        mBinding.itemCommodity.itemInventories.tvInventoriesThrow.setOnClickListener(this)
        mBinding.itemNavigation.ivInventories.setOnClickListener(this)
        mBinding.itemNavigation.ivBottomSecond.setOnClickListener(this)
        mBinding.itemNavigation.ivBottomThird.setOnClickListener(this)
        mBinding.ivHomeSearch.setOnClickListener(this)
        mBinding.tvAll.setOnClickListener(this)
//        mBinding.llPlace.setOnTouchListener(this)
        mBinding.llPlace.setOnClickListener(this)
        mBinding.vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBinding.title = titleArray[position]
                super.onPageSelected(position)
            }
        })

        val onTouchListener = View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastCommodityListY = event.y
                    Log.e(
                        javaClass.simpleName,
                        "ACTION_DOWN -------------- $lastCommodityListY"
                    )
                }
                MotionEvent.ACTION_MOVE -> {

                    val yDistance = event.y - lastCommodityListY
                    Log.e(javaClass.simpleName, "yDistance : $yDistance")

                    return@OnTouchListener homeTopAnim(yDistance)

                }
                MotionEvent.ACTION_UP -> {
                }
                else -> {
                }
            }
            return@OnTouchListener false
        }
//        placeFragment.setCommodityListTouchListener(onTouchListener)
    }

    private var mClickShadowToHidden = false
    private fun initBottomSheet() {
        if (!::mBottomSheetBehavior.isInitialized) {
            viewShadow.setOnClickListener {
                setNavigatorAnim()
                mClickShadowToHidden = true
                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            mBottomSheetBehavior = BottomSheetBehavior.from(itemCommodity)
            mBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            svCommodity.setScrollViewListener(svCommodityScrollListener)
            itemCommodity.setOnTouchListener(this)

            with(mBinding.itemCommodity.itemCommodityNavigation) {
                ivInventories.setOnClickListener {
                    if (mBinding.isDetail == null) return@setOnClickListener
                    if (mBinding.isDetail!!) {
                        toast("item inventory")
                    } else {
                        toast("item cancel")
                    }
                }
                ivConsuming.setOnClickListener {
                    if (mBinding.isDetail == null || mBinding.canDelete == null) return@setOnClickListener
                    if (mBinding.isDetail!!) {
                        toast("item consuming")
                    } else if (mBinding.canDelete!!) {
                        toast("item delete")
                    }
                    //                    mBinding.isHome = false
                }
                ivModify.setOnClickListener {
                    if (mBinding.isDetail == null) return@setOnClickListener
                    if (mBinding.isDetail!!) {
                        toast("item modity")
                    } else {
                        toast("item confirm")
                    }
                    //                    mBinding.isHome = false
                }
                ivModify.setOnClickListener {
                    //                    mBinding.isHome = false
                }
                ivBottomSecond.setOnClickListener {
                    showAddPopup(it)
                }
                ivBottomThird.setOnClickListener {
                    toast("item edit")
                }
            }
        }
        mCommodityDetailViewModel =
            ViewModelProvider(this).get(CommodityDetailViewModel::class.java)

        mCommodityDetailViewModel.commodityDetail.observe(this, Observer {
            if (null == it) return@Observer

            mCommodityDetail = it
            // 给 commodity sheet 设置数据
            mBinding.commodityDetail = it

            showInventories(it)
            showBarcode(it)
        })

    }

    fun newConsuming() {
        mBinding.isHome = true
    }

    fun fromInventroies() {
        mBinding.itemCommodity.itemInventories.ivInventoriesTopping.performClick()
    }

    fun typeInManually() {
        toast("type in manually")
    }

    fun scanBarcode() {
        toast("scan")
    }

    override fun onStart() {
        super.onStart()
        // 先读取本地数据库
        initUserViewModel()
        // 再联网获取数据
        getCommodityList()
    }

    private fun getCommodityList() {

//        expiresApiService.getAllCommodity()
//            .observeOn(Schedulers.io())
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it.code != 0) {
//                    toast(it.msg)
//                }
//
////                it.data
//            }, {
//                it.report()
//            })
    }


    private fun initUserViewModel() {
        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.setUserId(userId)
        viewModel.commodities.observe(this,
            Observer { user ->
                if (null == user) return@Observer

                getUserGroup(user.groupIdList)
            })
    }

    private fun getUserGroup(groupIdList: List<String>) {

        if (groupIdList.isNotEmpty()) {
            thread {
                val group =
                    (application as BasicApp).database.groupDao().loadGroupById(groupIdList[0])
                runOnUiThread {
                    initGroupHome(group)
                }
            }
        }
    }

    private fun initGroupHome(group: GroupEntity) {

        if (!::mPlaceList.isInitialized) {
            mPlaceList = listOf("All") + group.placeList
            mSelectPlace = mPlaceList[0]
            mBinding.placeName = mSelectPlace
            mBinding.notAll = true
        }

        mCommodityHomeViewModel = ViewModelProvider(this).get(CommodityHome2ViewModel::class.java)
        mCommodityHomeViewModel.commodityHome.observe(this, Observer {
            if (null == it) return@Observer

            placeFragment.setCommoditiesSummary(it.commoditySummaryList)
        })
    }

    private fun showInventories(it: CommodityEntity) {
        if (it.inventories.isNullOrEmpty()) return
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
                adapter = InventoryAdapter(it.inventories)
            } else {
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

    /** Shows the product detail placeFragment  */
    fun showCommodityDetail(commodityId: Int, sheetVersion: Boolean = true) {

//        if (sheetVersion) {
        mCommodityDetailViewModel.setCommodityId(commodityId)
        mBottomSheetBehavior.state = when (mBottomSheetBehavior.state) {
            BottomSheetBehavior.STATE_HIDDEN -> {
                setExpiresTheme(R.color.white)
                BottomSheetBehavior.STATE_COLLAPSED
            }
            BottomSheetBehavior.STATE_COLLAPSED or BottomSheetBehavior.STATE_EXPANDED -> BottomSheetBehavior.STATE_HIDDEN
            else -> BottomSheetBehavior.STATE_COLLAPSED
        }
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

    fun goConsuming(view: View) {
        disposable =
            expiresApiService.scanQRCode(50)
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

    fun homeTopAnim(yDistance: Float): Boolean {

        val maxHeight = ConvertUtils.dp2px(78f)
        val minHeight = ConvertUtils.dp2px(32f)

        val currentHeight = mBinding.rlCommodityHomeTop.height
        val isTopItem = !placeFragment.getCommodityListRecycleView().canScrollVertically(-1)
        val isBottomItem = !placeFragment.getCommodityListRecycleView().canScrollVertically(1)

        when {
            currentHeight == minHeight && yDistance < 0 -> return false
            currentHeight == minHeight && yDistance > 0 && !isTopItem -> return false
            isBottomItem && isTopItem -> return false
        }

        Log.e(javaClass.simpleName, isTopItem.toString())
        val newHeight = (currentHeight + yDistance).toInt()
        val showHeight: Int = when {
            newHeight > maxHeight -> {
                maxHeight
            }
            newHeight < minHeight -> {
                minHeight
            }
            else -> newHeight
        }

//        Log.e(javaClass.simpleName, "maxHeight : $maxHeight")
//        Log.e(javaClass.simpleName, "minHeight : $minHeight")
//        Log.e(javaClass.simpleName, "currentHeight : $currentHeight")
//        Log.e(javaClass.simpleName, "newHeight : $newHeight")
//        Log.e(javaClass.simpleName, "showHeight : $showHeight")
//        Log.e(javaClass.simpleName, "--------------")

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            showHeight
        )
        mBinding.rlCommodityHomeTop.layoutParams = lp

        Log.e(
            javaClass.simpleName,
            "showHeight - minHeight : ${ConvertUtils.px2dp((showHeight - minHeight).toFloat())}"
        )
        Log.e(javaClass.simpleName, "--------------")
        mBinding.tvAppName.textSize =
            ConvertUtils.px2dp(((showHeight - minHeight).toFloat())) * 16 / 46 + 16f

        return true
    }

    override fun onClick(v: View?) {
        if (null == v) return

        when (v) {
            tvCommClear -> {
                showEditPopup(v, ConsumingPopup(this))
            }
            ivInventoriesTopping -> {
                showEditPopup(v, InventoriesPopup(this, true))
            }
            tvInventoriesThrow -> {
                showEditPopup(v, InventoriesPopup(this, false))
            }
            ivInventories -> {
                mBinding.title = titleArray[0]
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fcCommodity, placeFragment, null)
//                    .commit()
                vpHome.currentItem = 0
            }
            ivConsuming -> {
//                showAddPopup(v)
                showAddPopup2(v)
            }
            ivModify -> {
//                startAct(Intent(this, SettingActivity::class.java))
                mBinding.title = titleArray[1]
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fcCommodity, accountFragment, null)
//                    .commit()
                vpHome.currentItem = 1
            }
            rlSearch -> {
                startSearch(v)
            }
            tvSearchCancel -> {
                cancelSearch()
            }
            tvAll -> {
                mBinding.placeName = mSelectPlace
                mBinding.notAll = false
                mCommodityHomeViewModel.setPlaceName("All")
            }
            ivHomeSearch -> {
                startAct(Intent(this, SearchActivity::class.java))
            }
            llPlace -> {
                showPlacePopup2(mPlaceList)
//                showPlacePopup(
//                    mBinding.llPlace,
//                    mPlaceList
//                )
            }
        }
    }

    private fun showPlacePopup2(placeList: List<String>) {

        val popup = PlacePopup2(this,
            {
                mBinding.showPopup = false
            },
            { placeName ->
                mBinding.showPopup = false
                mBinding.placeName = placeName
                mCommodityHomeViewModel.setPlaceName(placeName)
            })
        popup.setPlaceList(placeList)

        val contentView = popup.contentView
        Log.e("AAA", popup.width.toString())
        Log.e("AAA", popup.height.toString())
        contentView.measure(
            makeDropDownMeasureSpec(popup.height),
            makeDropDownMeasureSpec(popup.height)
        )

        Log.e("AAA", contentView.measuredHeight.toString())
        Log.e("AAA", mBinding.itemNavigation.llBottomNavigation.height.toString())
        Log.e("AAA", contentView.measuredWidth.toString())
        Log.e("AAA", mBinding.itemNavigation.llBottomNavigation.width.toString())

        val offsetX =
            abs(popup.contentView.measuredWidth - mBinding.llPlace.width) / 2
        val offsetY =
            -(mBinding.llPlace.height + ConvertUtils.dp2px(5f))

        mBinding.showPopup = true
        PopupWindowCompat.showAsDropDown(
            popup,
            mBinding.llPlace,
            offsetX,
            offsetY,
            Gravity.START
        )
    }

    private fun showAddPopup2(v: View) {

        val popup = AddPopup2(this) {
            mBinding.showPopup = false
        }
        val contentView = popup.contentView
        Log.e("AAA", popup.width.toString())
        Log.e("AAA", popup.height.toString())
        contentView.measure(
            makeDropDownMeasureSpec(popup.height),
            makeDropDownMeasureSpec(popup.height)
        )

        Log.e("AAA", contentView.measuredHeight.toString())
        Log.e("AAA", mBinding.itemNavigation.llBottomNavigation.height.toString())
        Log.e("AAA", contentView.measuredWidth.toString())
        Log.e("AAA", mBinding.itemNavigation.llBottomNavigation.width.toString())

        val offsetX =
            abs(mBinding.itemNavigation.llBottomNavigation.width - popup.contentView.measuredWidth) / 2
        val offsetY =
            -((popup.contentView.measuredHeight) + mBinding.itemNavigation.llBottomNavigation.height)

        mBinding.showPopup = true
        PopupWindowCompat.showAsDropDown(
            popup,
            mBinding.itemNavigation.llBottomNavigation,
            offsetX,
            offsetY,
            Gravity.START
        )

    }

    private fun makeDropDownMeasureSpec(measureSpec: Int): Int {
        val mode = if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            View.MeasureSpec.UNSPECIFIED
        } else {
            View.MeasureSpec.AT_MOST
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode)
    }

    private fun showAddPopup(it: View) {
        val location = IntArray(2)
        it.getLocationInWindow(location)

        location[1] -= ConvertUtils.dp2px(152.5f) + BarUtils.getNavBarHeight()

        XPopup.Builder(this@CommodityHomeActivity)
            .popupAnimation(PopupAnimation.ScrollAlphaFromBottom)
            .atView(mBinding.fcCommodity)
//            .offsetY(location[1])
            .offsetY(0)
            .asCustom(AddPopup(this@CommodityHomeActivity))
            .show()
    }

    private fun showEditPopup(
        view: View,
        popup: ExpiresPopupView,
//        callback: SimpleCallback? = null,
        backgroundRes: Int = R.color.transparency_90
    ) {
        val location = IntArray(2)
        view.getLocationInWindow(location)
//        location[1] -= ConvertUtils.dp2px(16f)
        location[1] += ConvertUtils.dp2px(24f)

        if (::mCommodityDetail.isInitialized) {
            popup.setCommodityDetail(mCommodityDetail)
            when (popup) {
                is ConsumingPopup -> {
                    val callback = object : ConsumingCallback {
                        override fun clearClick() {
                            toast("clear")
                        }
                    }
                    popup.setPopupCallback(callback)
                }
                /*is InventoriesPopup -> {
                    val callback = object : InventoriesCallback {
                        override fun minusClick() {
                            toast("minusClick")
                        }

                        override fun plusClick() {
                            toast("plusClick")
                        }

                        override fun toUseClick() {
                            toast("toUse")
                        }

                        override fun throwClick() {
                            toast("throw")
                        }
                    }
                    popup.setPopupCallback(callback)
                }*/
            }
        }

        XPopup.setShadowBgColor(getCompatColor(backgroundRes))

        val callback = object : SimpleCallback() {
            override fun onDismiss() {
                super.onDismiss()
                mBinding.itemCommodity.sheetShadow.visibility = View.GONE
            }
        }

        mPlaceNamePopup =
            XPopup
                .Builder(this)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .offsetY(location[1])
                .setPopupCallback(callback)
                .atView(mBinding.itemCommodity.layout)
//                .maxHeight(500)
                .asCustom(popup)
                .show()

//        mBinding.itemCommodity.sheetShadow.visibility = View.VISIBLE
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (v) {
            llPlace -> {
                placeTouchAnim(event)
            }
            itemCommodity -> {
                return itemCommodityTouchAnim(event)
            }
        }
        return true
    }

    private fun placeTouchAnim(event: MotionEvent?) {
        if (null == event) return

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                showPlacePopup(
                    mBinding.llPlace,
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
                    mBinding.placeName = mSelectPlace
                    mBinding.notAll = true
                    mCommodityHomeViewModel.setPlaceName(mSelectPlace)
                } catch (e: Exception) {
                    Log.e(javaClass.simpleName, "can not get name from empty place")
                }
            }
            else -> {

            }
        }
    }

    private fun itemCommodityTouchAnim(event: MotionEvent?): Boolean {
        if (null == event) return true

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
        return true
    }

    private fun setCommodityHeight(slideOffset: Float) {
        if (mBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) return

        val commodityNumHeight = if (slideOffset > 0) {
            ((1 - slideOffset) * ConvertUtils.dp2px(20f)).toInt()
        } else ConvertUtils.dp2px(20f)

        val set = ConstraintSet()
        set.clone(this, R.layout.item_commodity_head)
        set.connect(R.id.llCommodityNum, ConstraintSet.TOP, R.id.tvCommodityName, ConstraintSet.TOP)
        set.connect(
            R.id.llCommodityNum,
            ConstraintSet.BOTTOM,
            R.id.tvCommodityName,
            ConstraintSet.BOTTOM
        )
        set.connect(
            R.id.llCommodityNum,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        set.constrainHeight(R.id.llCommodityNum, commodityNumHeight)
        set.setAlpha(R.id.llCommodityNum, 1 - slideOffset)
        set.applyTo(mBinding.itemCommodity.itemCommodityHead.bottomSheetLayout)

        val consumeLayoutHeight = if (slideOffset > 0) {
            (slideOffset * ConvertUtils.dp2px(44f)).toInt()
        } else 0

        val consumeLayoutLP = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            consumeLayoutHeight
        )

        itemCommodity.itemConsuming.layoutConsume.alpha = slideOffset
        itemCommodity.itemConsuming.layoutConsume.layoutParams = consumeLayoutLP

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
        lp.setMargins(0, 0, 0, marginBottomHeight)
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        itemCommodity.itemCommodityNavigation.layoutParams = lp

//        Log.e(javaClass.simpleName, "h: $marginBottomHeight")
//        Log.e(javaClass.simpleName, "itemCommodity.height: ${itemCommodity.height}")
        val rect = Rect()
        itemCommodity.getLocalVisibleRect(rect)
//        Log.e(javaClass.simpleName, "rect: $rect")
//        Log.e(javaClass.simpleName, "height: $height")
//        Log.e(javaClass.simpleName, "height: ${ConvertUtils.px2dp(height.toFloat())}")
//        Log.e(javaClass.simpleName, "slideOffset: $slideOffset")
    }

    private fun showPlacePopup(view: View, placeList: List<String>) {
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
                .offsetX(ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(128f))
                .offsetY(location[1] - ConvertUtils.dp2px(10f))
                .setPopupCallback(object : SimpleCallback() {

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

                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                    override fun onBackPressed(): Boolean {
                        return true //默认返回false
                    }
                })
                .asCustom(mPlacePopup)
                .show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBottomSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                false
            } else {
                super.onKeyDown(keyCode, event)
            }
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // 扫描结果回调
        if (requestCode == REQ_QR_CODE && resultCode == RESULT_OK) {
//            val bundle = data?.extras
//            val scanResult = bundle?.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN)
//            mBinding.btInventories.text = scanResult
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setNavigatorAnim() {
        // todo 加个动画
//        val valueAnimator = ValueAnimator.ofArgb(Color.WHITE, 0xFFF7F7F7.toInt())
//        valueAnimator.addUpdateListener
//            setExpiresTheme(Color.parseColor(it.animatedValue.toString()))
////            mBinding.llPlace.setBackgroundColor(it.animatedValue as Int)
//        }
//        valueAnimator.duration = 3000
//        valueAnimator.start()
    }

    object Home {

        @JvmStatic
        @BindingAdapter("whiteFadeVisible")
        fun View.setWhiteFadeVisible(visible: Boolean) {
            this.animate().cancel()

            val animator: ValueAnimator
            if (visible) {
                this.visibility = View.VISIBLE

                animator = ValueAnimator.ofFloat(0f, 1f)
                animator.addUpdateListener { animation ->
                    val alpha = animation.animatedValue as Float
                    this.alpha = alpha
                }

//                this.alpha = 0f
//                this.animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator?) {
////                        super.onAnimationEnd(animation)
//                        this@setFadVisible.alpha = 1f
//                    }
//                })
            } else {
                animator = ValueAnimator.ofFloat(1f, 0f)
                animator.addUpdateListener { animation ->
                    val alpha = animation.animatedValue as Float
                    this.alpha = alpha
                    if (alpha == 0f) {
                        this.visibility = View.GONE
                    }
                }
//                this.animate().alpha(0f).setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator?) {
////                        super.onAnimationEnd(animation)
//                        this@setFadeVisible.alpha = 1f
//                        this@setFadeVisible.visibility = View.GONE
//                    }
//                })
            }
            animator.duration = 360
            animator.start()
        }

        @JvmStatic
        @BindingAdapter("blackFadeVisible")
        fun View.setBlackFadeVisible(visible: Boolean) {

//            Log.e("AAA", visible.toString())
//            Log.e("AAA", this.alpha.toString())
//            Log.e("AAA", if (this.visibility == View.VISIBLE) "Visible" else "Gone")
            if (visible) {
                if (this.visibility == View.VISIBLE || this.alpha != 0f) {
                    this.visibility = View.VISIBLE
                    return
                }
            } else {
                if (this.visibility == View.GONE || this.alpha != 1f) {
                    this.visibility = View.GONE
                    return
                }
            }

            this.animate().cancel()

            val animator: ValueAnimator
            if (visible) {
//                Log.e("AAA", "2")

                this.visibility = View.VISIBLE

                animator = ValueAnimator.ofFloat(0f, 1f)
                animator.addUpdateListener { animation ->
                    val alpha = animation.animatedValue as Float
                    this.alpha = alpha
                }

            } else {
//                Log.e("AAA", "3")

                animator = ValueAnimator.ofFloat(1f, 0f)
                animator.addUpdateListener { animation ->
                    val alpha = animation.animatedValue as Float
                    this.alpha = alpha
                    if (alpha == 0f) {
                        this.visibility = View.GONE
                    }
                }
            }
            animator.duration = 360
            animator.start()
        }
    }
}
