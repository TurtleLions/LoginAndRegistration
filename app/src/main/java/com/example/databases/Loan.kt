package com.example.databases


import java.util.*

data class Loan(var loanee: String = "Unknown",
                var description: String = "Unknown",
                var owed: Double = 0.toDouble(),
                var dateLoaned: Date = Date(0),
                var amountRepaid: Double = 0.toDouble(),
                var dateRepaid: Date? = null,
                var isRepaid: Boolean = false
                )
{
    fun balanceRemaining():Double{
        return owed-amountRepaid
    }
}
