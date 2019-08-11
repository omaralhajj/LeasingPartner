package com.itpirates.leasingpartner

import kotlin.math.ceil
import kotlin.math.floor

class ExtraordinaryLease {
    fun Calculate(carPrice: Double, payout: Double): Double {
        val result = (LeasingCost(payout) - Profit(carPrice)) + FinalProfit(carPrice)
        return if (result <= 0) {
            floor(result / 1000.0) * 1000.0
        } else {
            ceil(result / 1000.0) * 1000.0
        }
    }

    private fun LeasingCost(payout: Double): Double {
        return payout * 1.25
    }

    private fun Profit(carPrice: Double): Double {
        val g4 = 12000 // 'Ark1 (2)'G4 from the excel sheet
        val salePrice = carPrice + (g4 / 7.5)
        val salePricePrivate = salePrice * 1.25
        val profitInEuros = salePricePrivate - carPrice
        return profitInEuros * 7.5
    }

    private fun FinalProfit(carPrice: Double): Double {
        val carPriceInDkk = carPrice * 7.5
        return ceil((carPriceInDkk * 0.15) / 100.0) * 100.0
    }
}