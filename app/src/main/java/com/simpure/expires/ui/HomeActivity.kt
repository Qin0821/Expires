package com.simpure.expires.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.data.CommodityRepository
import com.simpure.expires.data.CommodityEnum

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var commodityList: List<CommodityRepository>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: com.simpure.expires.databinding.ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        binding.mainViewModel = mainViewModel
        binding.setLifecycleOwner(this)

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
