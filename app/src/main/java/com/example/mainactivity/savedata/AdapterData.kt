package com.example.mainactivity.savedata

import java.io.Serializable

data class AdapterData (var amount: Int? = null,
                        var date : String? = null,
                        var quantity : Int? = null,
                        var stockName : String? = null,
                        var transactionType: String? = null

) : Serializable