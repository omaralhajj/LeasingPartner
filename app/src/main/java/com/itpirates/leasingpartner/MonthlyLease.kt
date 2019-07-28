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
        val investment = (carPrice * 7.5) + 1180 + CalculateTax(fee) - payment + 12000 + 7500

        // 'Indtastning og beregning-AG'!D57
        val residualValue = carPrice * 7.5 * 0.8

        //FinanceLib.pmt er basically excel-funktionen YDELSE()
        val result = FinanceLib.pmt((0.0375/12), 12.0, investment, -residualValue, false)
        return ceil(result)
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