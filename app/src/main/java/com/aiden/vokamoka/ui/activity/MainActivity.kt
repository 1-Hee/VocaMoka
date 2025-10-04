package com.aiden.vokamoka.ui.activity


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.aiden.vokamoka.R
import com.aiden.vokamoka.base.bind.DataBindingConfig
import com.aiden.vokamoka.base.ui.BaseActivity
import com.aiden.vokamoka.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    // variables
    // 세팅 액티비티 팝업용 런처
    private lateinit var navController: NavController

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main)
    }

    override fun init(savedInstanceState: Bundle?) {
        // set up navcontroller
        navController = findNavController(R.id.nav_host_fragment_content_main)
        setSupportActionBar(mBinding.mainAppBar.toolbar)

    }

    // 액션 바 메뉴
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_app_bar, menu)
        return true
    }

    // 액션 바 메뉴 리스너
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId
        return when(id) {
            R.id.action_settings -> {
                val navController = (supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment).navController
                navController.navigate(R.id.settingFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}