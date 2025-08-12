package com.yogs.mytools.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yogs.mytools.R
import com.yogs.mytools.data.DataProvider
import com.yogs.mytools.data.model.Tool
import com.yogs.mytools.databinding.ActivityHomeBinding
import com.yogs.mytools.ui.resolution_changer.ResolutionChanger
import com.yogs.mytools.ui.setting.SettingsActivity
import com.yogs.mytools.ui.speedtest.SpeedTestActivity
import com.yogs.mytools.util.HomeAdapter
import com.yogs.mytools.util.HomeAdapter.OnItemClickCallback
import com.yogs.mytools.util.showToast

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var  homeAdapter : HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val data = DataProvider.getTools(this@HomeActivity)
        homeAdapter = HomeAdapter(data)
        showData()
        handleClickItemData()

    }

    private fun showData(){
        binding.rvTools.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = homeAdapter
            setHasFixedSize(true)
        }
    }

    private fun handleClickItemData(){
        homeAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: Tool) {
                when(data.key){
                    getString(R.string.key_screen_resolution_changer) -> {
                        val intent = Intent(this@HomeActivity, ResolutionChanger::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.key_speed_test)->{
                        val intent = Intent(this@HomeActivity, SpeedTestActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.key_router_manager)->{
                        showToast(getString(R.string.coming_soon))
                    }
                    getString(R.string.key_fh_pw_generator) -> {
                       showToast(getString(R.string.coming_soon))
                    }
                    else -> {
                        showToast(getString(R.string.coming_soon))
                    }
                }
            }
        })
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // 4. Tangani event klik pada item menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}