package com.example.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mainactivity.savedata.AdapterData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_recycler_item_view.*
import kotlinx.android.synthetic.main.fragment_home.*

class RecyclerItemView : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_item_view)


        databaseReference = Firebase.database.reference
        val obj = intent.extras?.get("listOfData") as AdapterData

        stocktitle.text = obj.stockName.orEmpty()



        val ref  = databaseReference.child("Shares").child("${obj.stockName}")




        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists())
                {
                   val totalU = snapshot.child("quantity").value.toString()
                   val totalC = snapshot.child("amount").value.toString()
                    val buyOrSell = snapshot.child("transactionType").value.toString()

                    if (buyOrSell == "Buy")
                    {
                        buyvalue.text = (totalC.toInt() * totalU.toInt()).toString()
                        buyunit.text = totalU
                    }
                    else
                    {
                        soldvalue.text = (totalC.toInt() * totalU.toInt()).toString()
                        soldunit.text = totalU
                    }

                   currentAmount.text = totalC
                    totalUnit.text = totalU
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        totalUnit.text = ref.toString()







    }
}