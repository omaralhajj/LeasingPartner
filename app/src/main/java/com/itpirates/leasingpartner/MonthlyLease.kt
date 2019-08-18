package com.itpirates.leasingpartner
import org.apache.poi.ss.formula.functions.FinanceLib
import kotlin.math.ceil
import kotlin.math.floor

class MonthlyLease {
    var fee = 0.0
    var payment = 0.0
    var carPrice = 0.0

    fun Calculate(fee: Double, payment: Double, carPrice: Double): Double {
        this.fee = fee
        this.payment = payment
        this.carPrice = carPrice
        return MonthlyPayments()
    }

    // 'Indtastning og beregning-AG'!D46
    fun calculateInvestment(): Double {
        val price = ceil(((carPrice * 7.5) + 12000 + 8000) / 1000.0) * 1000.0
        return price + 1180 + CalculateTax(fee) - payment
    }

    // 'beregning købekontrakt'!B46
    private fun MonthlyPayments(): Double {
        // 'Indtastning og beregning-AG'!D57
        val residualValue = carPrice * 7.5 * 0.8

        //FinanceLib.pmt er basically excel-funktionen YDELSE()
        var pmt = FinanceLib.pmt((0.0775/12), 12.0, calculateInvestment(), -residualValue, false)

        val administrationFee = 12500 / 12
        var result = (pmt * -1) * 1.25 + administrationFee * 1.25
        result = if (result > 0) {
            ceil(result / 10) * 10
        } else {
            floor(result / 10) * 10
        }
        return result
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