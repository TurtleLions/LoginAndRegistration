package com.example.databases


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Loan(var loanee: String = "Unknown",
                var description: String = "Unknown",
                var owed: Double = 0.toDouble(),
                var dateLoaned: Date = Date(0),
                var amountRepaid: Double = 0.toDouble(),
                var dateRepaid: Date? = null,
                var repaid: Boolean = false,
                var ownerId: String = "",
                var objectId: String? = ""
                ):Parcelable
{
    fun balanceRemaining():Double{
        return owed-amountRepaid
    }
}
