package com.itpirates.leasingpartner

import android.annotation.SuppressLint
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var carPrice: EditText
    private lateinit var fee: EditText
    private lateinit var payout: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        } else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        carPrice = findViewById(R.id.carPriceText)
        fee = findViewById(R.id.feeText)
        payout = findViewById(R.id.payoutText)
    }

    private fun areTextFieldsNull(): Boolean {
        return carPrice.text.isNullOrBlank() || fee.text.isNullOrBlank() || payout.text.isNullOrBlank()
    }

    @SuppressLint("SetTextI18n")
    fun calculatePrice(view: View) {
        if (!areTextFieldsNull()) {
            val extraordinaryLeaseTV = findViewById<TextView>(R.id.extraordinaryTextView)
            val monthlyLeaseTV = findViewById<TextView>(R.id.monthlyTextView)
            val residualTV = findViewById<TextView>(R.id.residualTextView)

            val highTV = findViewById<TextView>(R.id.highTextView)
            val mediumTV = findViewById<TextView>(R.id.mediumTextView)
            val lowTV = findViewById<TextView>(R.id.lowTextView)

            val carPrice = carPrice.text.toString().toDouble()
            val fee = fee.text.toString().toDouble()
            val payout = payout.text.toString().toDouble()

            val extraordinaryLease = ExtraordinaryLease()
            val monthlyLease = MonthlyLease()

            extraordinaryLeaseTV.text = String.format(Locale.GERMAN,"%,.2f", extraordinaryLease.Calculate(carPrice, payout)) + " kr."
            monthlyLeaseTV.text = String.format(Locale.GERMAN,"%,.2f", monthlyLease.Calculate(fee, payout, carPrice)) + " kr."
            residualTV.text = String.format(Locale.GERMAN,"%,.2f", (carPrice * 7.5 * 0.8)) + " kr."

            highTV.text = String.format(Locale.GERMAN,"%,.2f", monthlyLease.calculateInvestment() * 0.3) + " kr."
            mediumTV.text = String.format(Locale.GERMAN,"%,.2f", monthlyLease.calculateInvestment() * 0.25) + " kr."
            lowTV.text = String.format(Locale.GERMAN,"%,.2f", monthlyLease.calculateInvestment() * 0.2) + " kr."
        }
    }
}
