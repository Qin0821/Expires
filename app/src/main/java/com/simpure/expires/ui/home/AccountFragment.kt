package com.simpure.expires.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySettingBinding
import com.simpure.expires.ui.setting.AccountActivity
import com.simpure.expires.ui.setting.NewNotificationsActivity
import com.simpure.expires.ui.setting.NotificationsActivity
import com.simpure.expires.ui.setting.categories.CategoriesActivity
import com.simpure.expires.utilities.startAct
import com.simpure.expires.utilities.toast

class AccountFragment : Fragment() {

    val TAG = javaClass.simpleName


    private lateinit var mBinding: ActivitySettingBinding


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_setting, container, false)


        return mBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.apply {
            accountClick = View.OnClickListener {
                context?.startAct(AccountActivity::class.java)
            }
            catagoriesClick = View.OnClickListener {
                context?.startAct(CategoriesActivity::class.java)
            }
            notificationsClick = View.OnClickListener {
                context?.startAct(NewNotificationsActivity::class.java)
            }
            shareAppClick = View.OnClickListener {
                context?.toast("share")
            }
            rateUsClick = View.OnClickListener {
                context?.toast("rate")
            }
            feedbackClick = View.OnClickListener {
                context?.toast("feedback")
            }
        }
    }


}