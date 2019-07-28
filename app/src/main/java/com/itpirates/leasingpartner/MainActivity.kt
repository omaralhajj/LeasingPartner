package com.itpirates.leasingpartner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText;
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var carPrice: EditText
    private lateinit var fee: EditText
    private lateinit var payout: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carPrice = findViewById(R.id.carPriceText)
        fee = findViewById(R.id.feeText)
        payout = findViewById(R.id.payoutText)

        carPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!AreTextFieldsNull()) {
                    CalculatePrice()
                }
            }
        })

        fee.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!AreTextFieldsNull()) {
                    CalculatePrice()
                }
            }
        })

        payout.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!AreTextFieldsNull()) {
                    CalculatePrice()
                }
            }
        })
    }

    private fun AreTextFieldsNull(): Boolean {
        return carPrice.text.isNullOrBlank() || fee.text.isNullOrBlank() || payout.text.isNullOrBlank()
    }

    fun CalculatePrice() {
        val textView = findViewById<TextView>(R.id.priceTextView)
        var price: Double
        val carPrice = carPrice.text.toString().toDouble()
        val fee = fee.text.toString().toDouble()
        val payout = payout.text.toString().toDouble()
        val extraordinaryLease = MonthlyLease()

        price = extraordinaryLease.Calculate(fee, payout, carPrice)

        textView.text = price.toString()
    }
}
