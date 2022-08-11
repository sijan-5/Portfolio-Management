package com.example.mainactivity.savedata

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable


@IgnoreExtraProperties
data class MyData
    (
    val uid : String? = null,
    val StockName : String? = null,
    val Date : String? = null,
    val transactionType : String? = null,
    val quantity : Int? = null,
    val amount : Int? = null
            )