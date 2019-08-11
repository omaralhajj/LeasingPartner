package com.itpirates.leasingpartner
import android.util.Log
import org.apache.poi.ss.formula.functions.FinanceLib
import kotlin.math.ceil
import kotlin.math.floor

class MonthlyLease {
    fun Calculate(fee: Double, payment: Double, carPrice: Double): Double {
        return MonthlyPayments(fee, payment, carPrice)
    }

    // 'beregning købekontrakt'!B46
    private fun MonthlyPayments(fee: Double, payment: Double, carPrice: Double): Double {
        // 'Indtastning og beregning-AG'!D46
        val price = ceil((carPrice * 7.5) / 1000.0) * 1000.0
        val investment = price + 1180 + CalculateTax(fee) - payment + 12000 + 7500

        // 'Indtastning og beregning-AG'!D57
        val residualValue = carPrice * 7.5 * 0.8

        //FinanceLib.pmt er basically excel-funktionen YDELSE()
        var pmt = FinanceLib.pmt((0.0375/12), 12.0, investment, -residualValue, false)

        if (pmt > 0) {
            pmt = ceil(pmt)
        } else {
            pmt = floor(pmt)
        }

        val administrationfee = (6995 + 8000 + (price * 0.001 * 12)) / 12

        return ceil(((pmt * -1) * 1.25 + administrationfee * 1.25) / 100.0) * 100.0
    }

    // 'SKAT'!E36
    private fun CalculateTax(fee: Double): Double {
        val leasingPeriodInMonths = 12
        val rate = 0.036
        val valueLossPercentage = 0.06
        val valueLoss = fee * valueLossPercentage
        val residualFee = fee - valueLoss
        val rateFeeOfResidualFee = residualFee * rate * leasingPeriodInMonths / 12

        // returns 'SKAT'!E36
        return valueLoss + rateFeeOfResidualFee
    }
}