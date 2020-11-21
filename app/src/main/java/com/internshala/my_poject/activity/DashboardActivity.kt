package com.internshala.my_poject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.my_poject.R
import com.internshala.my_poject.fragment.*

class DashboardActivity : AppCompatActivity(){
   private  lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
   private  lateinit var frame :FrameLayout

    var previousMenuItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        init()
        setUpToolbaar()
        openDashboard()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@DashboardActivity,
            drawerLayout, R.string.open_drawer, R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem != null){
            previousMenuItem?.isChecked = false
        }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.home->{
                    openDashboard()
                      drawerLayout.closeDrawers()
                }
                R.id.myProfile->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .addToBackStack("myProfile")
                        .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.favRes->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,FavouritesFragment())
                        .addToBackStack("favRes")
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.faqs->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FaqsFragment())
                        .addToBackStack("faqs")
                        .commit()
                    supportActionBar?.title = "FAQS"
                    drawerLayout.closeDrawers()
                }
                R.id.logout->{
                    val builder = AlertDialog.Builder(this@DashboardActivity)
                    builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want exit?")
                        .setPositiveButton("Yes") { _, _ ->
                            ActivityCompat.finishAffinity(this)
                        }
                        .setNegativeButton("No") { _, _ ->
                           openDashboard()
                        }
                        .create()
                        .show()
            }
            }

            return@setNavigationItemSelectedListener true
        }
    }
    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
    }

    fun setUpToolbaar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Restaurants"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun openDashboard (){
        val transaction =supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, DashboardFragment())
         transaction.commit()
        supportActionBar?.title = "Restaurants"
        navigationView.setCheckedItem(R.id.home)

    }

    override fun onBackPressed() {
       val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            !is DashboardFragment -> openDashboard()
            else ->
                super.onBackPressed()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}