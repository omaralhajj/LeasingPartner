package com.itpirates.leasingpartner

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText;
import android.widget.TextView
import java.lang.Math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var carPrice: EditText
    private lateinit var fee: EditText
    private lateinit var payout: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        carPrice = findViewById(R.id.carPriceText)
        fee = findViewById(R.id.feeText)
        payout = findViewById(R.id.payoutText)

        carPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!carPrice.text.isNullOrBlank()) {
                    val price = ceil((carPrice.text.toString().toDouble() * 7.5 * 1.5) / 100000.0) * 100000.0
                    fee.setText(String.format("%.0f", price))
                }
            }
        })
    }

    private fun AreTextFieldsNull(): Boolean {
        return carPrice.text.isNullOrBlank() || fee.text.isNullOrBlank() || payout.text.isNullOrBlank()
    }

    fun CalculatePrice(view: View) {
        if (!AreTextFieldsNull()) {
            val extraordinaryLeaseTV = findViewById<TextView>(R.id.extraordinaryTextView)
            val monthlyLeaseTV = findViewById<TextView>(R.id.monthlyTextView)
            val residualTV = findViewById<TextView>(R.id.residualTextView)

            val carPrice = carPrice.text.toString().toDouble()
            val fee = fee.text.toString().toDouble()
            val payout = payout.text.toString().toDouble()

            val extraordinaryLease = ExtraordinaryLease()
            val monthlyLease = MonthlyLease()

            extraordinaryLeaseTV.text = String.format("%.0f", extraordinaryLease.Calculate(carPrice, payout)) + " kr."
            monthlyLeaseTV.text = String.format("%.0f", monthlyLease.Calculate(fee, payout, carPrice)) + " kr."
            residualTV.text = String.format("%.0f", (carPrice * 7.5 * 0.8)) + " kr."
        }
    }
}
