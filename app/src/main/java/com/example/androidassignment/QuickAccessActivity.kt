package com.example.androidassignment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.adapter.SelectedSubServiceAdapter
import com.example.androidassignment.data.model.SubService

class QuickAccessActivity : AppCompatActivity() {

    private val selectedSubServices = mutableListOf<SubService>()
    private lateinit var adapter: SelectedSubServiceAdapter
    private lateinit var dotsContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_access)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                window.statusBarColor = getColor(android.R.color.black)
                window.decorView.systemUiVisibility = 0
            } else {
                window.statusBarColor = getColor(android.R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        adapter = SelectedSubServiceAdapter(selectedSubServices)
        recyclerView = findViewById(R.id.rvQuickAccess)
        dotsContainer = findViewById(R.id.dotsContainer)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        setupDots(selectedSubServices.size)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = rv.layoutManager as LinearLayoutManager
                val activePosition = layoutManager.findFirstVisibleItemPosition()
                updateDots(activePosition)
            }
        })

        selectedSubServices.addAll(
            listOf(
                SubService("Transfer", R.drawable.ic_transfer),
                SubService("GIP", R.drawable.gui),
                SubService("Mobile Money", R.drawable.mobile_money_svgrepo_com),
                SubService("Buy Airtime", R.drawable.mobile_phone_protection_svgrepo_com)
            )
        )
        adapter.notifyDataSetChanged()
        setupDots(selectedSubServices.size)



        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val intent = Intent(this, ServiceSelectionActivity::class.java)
            intent.putParcelableArrayListExtra("selected", ArrayList(selectedSubServices))
            startActivityForResult(intent, 1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            val result = data?.getParcelableArrayListExtra<SubService>("result") ?: return
            selectedSubServices.clear()
            selectedSubServices.addAll(result)
            adapter.notifyDataSetChanged()
            setupDots(selectedSubServices.size)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupDots(itemCount: Int) {
        val pageCount = if (itemCount % 4 == 0) itemCount / 4 else (itemCount / 4) + 1
        dotsContainer.removeAllViews()
        for (i in 0 until pageCount) {
            val dot = ImageView(this).apply {
                setImageResource(R.drawable.dot_inactive)
                val params = LinearLayout.LayoutParams(20, 20)
                params.setMargins(8, 0, 8, 0)
                layoutParams = params
            }
            dotsContainer.addView(dot)
        }
        updateDots(0)
    }

    private fun updateDots(activeIndex: Int) {
        for (i in 0 until dotsContainer.childCount) {
            val dot = dotsContainer.getChildAt(i) as ImageView
            dot.setImageResource(
                if (i == activeIndex) R.drawable.dot_active else R.drawable.dot_inactive
            )
        }
    }
}
