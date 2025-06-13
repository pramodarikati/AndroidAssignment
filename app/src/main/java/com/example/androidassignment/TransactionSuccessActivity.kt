package com.example.androidassignment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.androidassignment.data.model.TransactionResponse
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_success)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                window.statusBarColor = getColor(android.R.color.black)
                window.decorView.systemUiVisibility = 0
            } else {
                window.statusBarColor = getColor(android.R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        val json = assets.open("transaction_response.json")
            .bufferedReader()
            .use { it.readText() }

        val response = Gson().fromJson(json, TransactionResponse::class.java)
        val message = response.ResultMessage.Message

        findViewById<TextView>(R.id.tvHeader).text = message.resultHeader
        findViewById<TextView>(R.id.tvTime).text = message.date

        val paidTo = message.resultScreenDetails.find { it.code == "CR" }?.details?.firstOrNull()
        if (paidTo != null) {
            findViewById<TextView>(R.id.tvPaidToName).text = paidTo.value1
            findViewById<TextView>(R.id.tvPaidToAccNo).text = paidTo.value2
            findViewById<TextView>(R.id.tvPaidToAmount).text = paidTo.value3
        }

        val tran = message.resultScreenDetails.find { it.code == "TRANID" }?.details?.firstOrNull()
        findViewById<TextView>(R.id.tvTranId).text = tran?.value1 ?: ""

        val desc = message.resultScreenDetails.find { it.code == "DESC" }?.details?.firstOrNull()
        findViewById<TextView>(R.id.tvDescription).text = desc?.value1 ?: ""

        val debited = message.resultScreenDetails.find { it.code == "DR" }?.details?.firstOrNull()
        findViewById<TextView>(R.id.tvDebitedFrom).text = debited?.value1 ?: ""
        findViewById<TextView>(R.id.tvDebitedAmount).text = debited?.value3 ?: ""

        findViewById<Button>(R.id.btnDone).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnsave).setOnClickListener {
            generateAndOpenPdf()
        }
    }

    private fun generateAndOpenPdf() {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()
        paint.textSize = 12f

        canvas.drawText("Transaction Receipt", 80f, 40f, paint)
        canvas.drawText("Date: ${getCurrentDateTime()}", 20f, 80f, paint)
        canvas.drawText("To: Michelle Wayne", 20f, 100f, paint)
        canvas.drawText("Amount: GHS 5.00", 20f, 120f, paint)
        canvas.drawText("Txn ID: T123456789XYUTREWSED", 20f, 140f, paint)

        document.finishPage(page)

        val fileName = "receipt_${System.currentTimeMillis()}.pdf"
        val file = File(getExternalFilesDir(null), fileName)
        document.writeTo(FileOutputStream(file))
        document.close()

        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No app found to open PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }

}
