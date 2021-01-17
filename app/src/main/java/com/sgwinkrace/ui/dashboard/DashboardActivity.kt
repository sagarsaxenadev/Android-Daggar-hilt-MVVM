package com.sgwinkrace.ui.dashboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sgwinkrace.R
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var appDataStore: AppDataStore

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var leftSideDrawerView: LinearLayout
    private var screenHeight: Int = 0
    private var screenWidth: Int = 0
    private lateinit var imghome: ImageView
    private lateinit var imgUser: ImageView
    private var drawerMenuView: LinearLayout? = null
    private lateinit var mTvMobileNumber: TextView
    private lateinit var mTvUserName: TextView
    private lateinit var mToolBar: Toolbar

    var mProfileImage = ""


    var userId = ""
    var userMobile = ""
    var userEmail = ""


    override fun onResume() {
        super.onResume()


        mTvMobileNumber.text=userMobile
        mTvUserName.text=userEmail

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        lifecycleScope.launch(Dispatchers.Main) {

            appDataStore.userIdFlow.collect {
                userId = it
            }

            appDataStore.userMobileFlow.collect {
                userMobile = it
            }

            appDataStore.userEmailFlow.collect {
                userEmail = it
            }

            mTvMobileNumber.text=userMobile
            mTvUserName.text=userEmail
        }



        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(metrics)

        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
        getId()
        manageLeftSideDrawer()

        bottomMenu()
        val fragment = HomeFragment()
        loadFragment(fragment)

    }

    private fun bottomMenu() {
        navigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_home -> {
                    val fragment = HomeFragment()
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {
                    val fragment = HomeFragment()
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_chat -> {
                    val fragment = HomeFragment()
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_more -> {
                    val fragment = HomeFragment()
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false

        }
    }


    private fun getId() {
        mToolBar = findViewById(R.id.toolbar) as Toolbar
        drawerMenuView = findViewById(R.id.drawerMenuView) as LinearLayout
        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        setUpToolbar(R.drawable.ic_menu, "Dashboard")
    }

    private fun setUpToolbar(menu: Int, titleStr: String) {

        imghome = mToolBar.findViewById(R.id.home) as ImageView
        var tvTitle = mToolBar.findViewById(R.id.tvTitle) as TextView
        imghome.setImageResource(menu)
        mToolBar.setNavigationIcon(null)

        tvTitle.text = titleStr

        imghome.setOnClickListener {
            if (mDrawerLayout!!.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout!!.closeDrawer(Gravity.LEFT)
            } else {
                mDrawerLayout!!.openDrawer(Gravity.LEFT)
            }
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit()
            return true
        }
        return false
    }


    private fun manageLeftSideDrawer() {

        leftSideDrawerView =
            LayoutInflater.from(this).inflate(
                R.layout.left_side_drawer_layout,
                null
            ) as LinearLayout

        leftSideDrawerView!!.setLayoutParams(
            LinearLayout.LayoutParams(
                (screenWidth * 0.8).toInt(),
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        mTvMobileNumber = leftSideDrawerView.findViewById<View>(R.id.drawerUserNameMobile) as TextView
        mTvUserName = leftSideDrawerView.findViewById<View>(R.id.drawerUserNameTView) as TextView
        imgUser = leftSideDrawerView.findViewById<View>(R.id.imgUser) as ImageView
//        leftSideDrawerView.findViewById<LinearLayout>(R.id.llPayment).setOnClickListener(this)
        leftSideDrawerView.findViewById<LinearLayout>(R.id.llRateUs).setOnClickListener(this)
        leftSideDrawerView.findViewById<LinearLayout>(R.id.llLogout).setOnClickListener(this)
        drawerMenuView?.addView(leftSideDrawerView)


    }

    override fun onClick(p0: View?) {

    }

}