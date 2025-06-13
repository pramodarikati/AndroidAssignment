package com.example.androidassignment

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.adapter.ServiceSelectionAdapter
import com.example.androidassignment.data.model.Service
import com.example.androidassignment.data.model.SubService

class ServiceSelectionActivity : AppCompatActivity() {

    private lateinit var adapter: ServiceSelectionAdapter
    private val services = mutableListOf<Service>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_selection)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Customize Quick Access"

        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, android.R.color.white))


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                window.statusBarColor = getColor(android.R.color.black)
                window.decorView.systemUiVisibility = 0
            } else {
                window.statusBarColor = getColor(android.R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        val selected = intent.getParcelableArrayListExtra<SubService>("selected") ?: arrayListOf()

        services.addAll(
            listOf(
                Service(
                    "Account Service", false, R.drawable.ic_account, listOf(
                        SubService("Account Info", R.drawable.ic_account),
                        SubService("Account History", R.drawable.ic_account),
                        SubService("Account Block", R.drawable.ic_account),
                        SubService("Update KYC", R.drawable.ic_account),
                        SubService("Link Aadhaar", R.drawable.ic_account),
                        SubService("Nominee Details", R.drawable.ic_account),
                        SubService("Cheque Book Request", R.drawable.ic_account),
                        SubService("View Statements", R.drawable.ic_account),
                        SubService("Account Closure Request", R.drawable.ic_account),
                        SubService("Interest Certificate", R.drawable.ic_account),
                        SubService("E-Statement Email", R.drawable.ic_account)
                    )
                ),
                Service(
                    "Transfer", false, R.drawable.ic_transfer, listOf(
                        SubService("Own Accounts", R.drawable.ic_transfer),
                        SubService("Other CBG Accounts", R.drawable.ic_transfer),
                        SubService("Other Bank Accounts (GIP)", R.drawable.ic_transfer),
                        SubService("Mobile Money", R.drawable.ic_transfer),
                        SubService("To Other App User", R.drawable.ic_transfer),
                        SubService("International Transfer", R.drawable.ic_transfer),
                        SubService("FX Sale", R.drawable.ic_transfer),
                        SubService("Link Prepaid Card to Account", R.drawable.ic_transfer),
                        SubService("Transaction History", R.drawable.ic_transfer),
                        SubService("Bulk Payment", R.drawable.ic_transfer),
                        SubService("Scheduled Transfer", R.drawable.ic_transfer)
                    )
                ),
                Service(
                    "Recharge & Bill Pay", false, R.drawable.gui, listOf(
                        SubService("Mobile Recharge", R.drawable.gui),
                        SubService("DTH Recharge", R.drawable.gui),
                        SubService("Electricity Bill", R.drawable.gui),
                        SubService("Gas Bill", R.drawable.gui),
                        SubService("Water Bill", R.drawable.gui),
                        SubService("Broadband Bill", R.drawable.gui),
                        SubService("Postpaid Bill", R.drawable.gui),
                        SubService("Landline Bill", R.drawable.gui),
                        SubService("Municipality Tax", R.drawable.gui),
                        SubService("Credit Card Bill", R.drawable.gui),
                        SubService("Insurance Premium", R.drawable.gui)
                    )
                ),
                Service(
                    "Loans", false, R.drawable.mobile_money_svgrepo_com, listOf(
                        SubService("Apply Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Loan Repayment", R.drawable.mobile_money_svgrepo_com),
                        SubService("Loan Statement", R.drawable.mobile_money_svgrepo_com),
                        SubService("Personal Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Education Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Car Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Home Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Gold Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Business Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Top-Up Loan", R.drawable.mobile_money_svgrepo_com),
                        SubService("Loan Foreclosure", R.drawable.mobile_money_svgrepo_com)
                    )
                ),
                Service(
                    "Support Services",
                    false,
                    R.drawable.mobile_phone_protection_svgrepo_com,
                    listOf(
                        SubService("Chat Support", R.drawable.mobile_phone_protection_svgrepo_com),
                        SubService("Email Support", R.drawable.mobile_phone_protection_svgrepo_com),
                        SubService(
                            "Raise Complaint",
                            R.drawable.mobile_phone_protection_svgrepo_com
                        ),
                        SubService(
                            "Branch Locator",
                            R.drawable.mobile_phone_protection_svgrepo_com
                        ),
                        SubService("ATM Locator", R.drawable.mobile_phone_protection_svgrepo_com),
                        SubService("Feedback", R.drawable.mobile_phone_protection_svgrepo_com),
                        SubService(
                            "Service Requests",
                            R.drawable.mobile_phone_protection_svgrepo_com
                        ),
                        SubService(
                            "Callback Request",
                            R.drawable.mobile_phone_protection_svgrepo_com
                        ),
                        SubService("FAQ", R.drawable.mobile_phone_protection_svgrepo_com),
                        SubService(
                            "Download Forms",
                            R.drawable.mobile_phone_protection_svgrepo_com
                        ),
                        SubService("Customer Care", R.drawable.mobile_phone_protection_svgrepo_com)
                    )
                )
            )
        )


        services.forEach { s ->
            s.subServices.forEach { ss ->
                if (selected.any { it.name == ss.name }) ss.isSelected = true
            }
        }

        adapter = ServiceSelectionAdapter(services) { sub ->
            val currentSelected = services.flatMap { it.subServices }.count { it.isSelected }
            if (!sub.isSelected && currentSelected >= 8) {
                Toast.makeText(this, "Max 8 allowed", Toast.LENGTH_SHORT).show()
            } else {
                sub.isSelected = !sub.isSelected
                adapter.notifyDataSetChanged()
            }
        }

        findViewById<RecyclerView>(R.id.rvServices).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rvServices).adapter = adapter

        findViewById<Button>(R.id.btnDone).setOnClickListener {
            val selectedSubServices = services.flatMap { it.subServices }.filter { it.isSelected }
            val resultIntent = Intent().apply {
                putParcelableArrayListExtra("result", ArrayList(selectedSubServices))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_done, menu)

        val item = menu?.findItem(R.id.action_done)
        item?.let {
            val spanString = SpannableString(it.title.toString())
            spanString.setSpan(ForegroundColorSpan(Color.WHITE), 0, spanString.length, 0)
            it.title = spanString
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_done -> {
                setResult(RESULT_OK, Intent().apply {
                    putParcelableArrayListExtra("result", ArrayList(getSelectedSubServices()))
                })
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSelectedSubServices(): List<SubService> {
        return services.flatMap { it.subServices }.filter { it.isSelected }
    }
}
