package com.simpure.expires.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.data.*
import com.simpure.expires.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {


    override fun initData() {
        commodityList = listOf(
            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.ALMOST),
            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED),
            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.FRESH),
            CommodityRepository("薯条", "1234567890", 1234567890, 1234567890, 3, CommodityEnum.EXPIRED)
        )
    }

    override fun initView() {

    }

    private lateinit var commodityList: List<CommodityRepository>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.homeDTO = CommodityListRepository(
            User("qin", 0,0L,1L),
            BoxRepository("box", commodityList)
        )
//            setContentView(R.layout.activity_home)
//        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//        binding.mainViewModel = mainViewModel
//        binding.setLifecycleOwner(this)

    }

    private fun getExpiredList(): List<CommodityRepository> {

        return commodityList.filter { it.type == CommodityEnum.EXPIRED }
    }

    private fun getAtmostList(): List<CommodityRepository> {

        return commodityList.filter { it.type == CommodityEnum.ALMOST }
    }

    private fun getFreshtList(): List<CommodityRepository> {
        return commodityList.filter { it.type == CommodityEnum.FRESH }
    }

}
