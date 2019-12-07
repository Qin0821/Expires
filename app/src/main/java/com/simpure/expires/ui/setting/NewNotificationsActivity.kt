package com.simpure.expires.ui.setting

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityNotificationsNewBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.getCompatColor
import com.simpure.expires.utilities.toast


class NewNotificationsActivity : BaseActivity() {


    override fun onClick(v: View?) {
        with(mBinding) {

            when (v!!) {
                tvDone -> finish()
            }

        }
    }

    private lateinit var mBinding: ActivityNotificationsNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notifications_new)
        setActivityTheme(mBinding.llLayout)

        initData()
        initView()
        initListener()
    }

    private fun initView() {
        mBinding.apply {
            //            llFrom.performClick()
//            llTo.performClick()
            scNotifications.setOnCheckedChangeListener { buttonView, isChecked ->
                mBinding.isAllNotifications = isChecked
            }

//            wvPicker.setCyclic(true)

            val mOptionsItems: MutableList<String> = ArrayList()
            mOptionsItems.add("item0")
            mOptionsItems.add("item1")
            mOptionsItems.add("item2")

            val hours = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
            val minutes = listOf(
                "00",
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31",
                "32",
                "33",
                "34",
                "35",
                "36",
                "37",
                "38",
                "39",
                "40",
                "41",
                "42",
                "43",
                "44",
                "45",
                "46",
                "47",
                "48",
                "49",
                "50",
                "51",
                "52",
                "53",
                "54",
                "55",
                "56",
                "57",
                "58",
                "59",
                "60"
            )
            val types = listOf("AM", "PM")

//            wvPicker.adapter = ArrayWheelAdapter(mOptionsItems)
//            wvPicker.setOnItemSelectedListener { index ->
//                Toast.makeText(
//                    this@NewNotificationsActivity,
//                    "" + mOptionsItems[index],
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            var tvNotifyTime: TextView? = null

            pvNotifications =
                OptionsPickerBuilder(this@NewNotificationsActivity,
                    OnOptionsSelectListener { options1, option2, options3, v ->
                        //返回的分别是三个级别的选中位置
                        val tx: String = (hours[options1]
                                + minutes[option2]
                                + types[options3])
                        toast(tx)
                        tvNotifyTime?.text = tx
                    })
                    .setLayoutRes(R.layout.item_expires_picker, { v ->
                        tvNotifyTime = v.findViewById(R.id.tvNotifyTime)
                    })
                    .setDecorView(clPickerContainer)
                    .setContentTextSize(20)
                    .setDividerColor(getCompatColor(R.color.divider))
                    .setOutSideColor(0x00000000)
                    .setLineSpacingMultiplier(2f)
                    .setOutSideCancelable(false)
                    .setCyclic(true, true, false)
                    .build<String>()
            pvNotifications.setNPicker(hours, minutes, types)
            pvNotifications.setSelectOptions(7, 0, 0)
            pvNotifications.show(false)
        }

    }

    private lateinit var pvNotifications: OptionsPickerView<String>

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {

        mBinding.apply {
            tvDone.setOnClickListener(this@NewNotificationsActivity)
        }
    }

    private fun initData() {
        mBinding.isAllNotifications = false
    }
}