package com.internshala.my_poject.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.internshala.my_poject.R
import com.internshala.my_poject.api.ApiClient
import com.internshala.my_poject.api.ApiInterface


/**
 * Created by Rahul Abrol on 31/8/20.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val request = ApiClient.buildService(ApiInterface::class.java)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}