package com.example.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.mainactivity.savedata.MyData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.widget.ArrayAdapter
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class MainActivity : AppCompatActivity() {




    private lateinit var homeFragment: HomeFragment
    private lateinit var stockListFragment: StockListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        stockListFragment = StockListFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainActivityFragment,homeFragment)
            addToBackStack(null)
            commit()
        }


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.home ->
                {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.left_in,R.anim.right_out).apply {
                        replace(R.id.mainActivityFragment,homeFragment)
                        addToBackStack(null)
                        commit()
                    }

                }

                R.id.bottomNavList ->
                {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.right_in,R.anim.left_out).apply {
                        replace(R.id.mainActivityFragment,stockListFragment)
                        addToBackStack(null)
                        commit()
                    }


                }


            }
            true

        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

//        val transactionType = resources.getStringArray(R.array.transactionType)
//        val arrayAdapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,transactionType)
//        autoCompleteTransaction.setAdapter(arrayAdapter)
//
//
//        database = Firebase.database.reference
//
//        firebaseAuth = Firebase.auth
//
//
//
//        expandButton.setOnClickListener {
//            handleExpansion()
//
//        }
//
//        saveButton.setOnClickListener {
//
//            handleNullData(it)
//
//        }
//
//
//    }
//
//    private fun handleNullData(arrive: View) {
//
//
//        when
//        {
//
//            TextUtils.isEmpty(stockName.text.toString().trim { it <= ' ' }) ->
//            {
//                stockName.error = "Stock Name is required"
//                stockName.requestFocus()
//                return
//            }
//            TextUtils.isEmpty(date.text.toString().trim { it <= ' ' }) ->
//            {
//                stockName.error = "Date is required"
//                stockName.requestFocus()
//                return
//            }
//
//
//            TextUtils.isEmpty(autoCompleteTransaction.text.toString().trim { it <= ' ' }) ->
//            {
//                autoCompleteTransaction.error = "This is required"
//                autoCompleteTransaction.requestFocus()
//                return
//            }
//
//            TextUtils.isEmpty(quantity.text.toString().trim { it <= ' ' }) ->
//            {
//                quantity.error = "Quantity number is required"
//                quantity.requestFocus()
//                return
//            }
//
//            TextUtils.isEmpty(amount.text.toString().trim { it <= ' ' }) ->
//            {
//                amount.error = "Amount is required"
//                amount.requestFocus()
//                return
//            }
//
//            else ->
//            {
//                val getStockName = stockName.text.toString()
//                val getDate = date.text.toString()
//                val getTransactionType = autoCompleteTransaction.text.toString()
//                val getQuantity = quantity.text.toString().toInt()
//                val getAmount = amount.text.toString().toInt()
//                saveData(getStockName,getDate,getTransactionType,getQuantity,getAmount)
//
//            }
//
//        }
//
//    }
//
//
//    private fun saveData(
//        getStockName: String,
//        getDate: String,
//        getTransactionType: String,
//        getQuantity: Int,
//        getAmount: Int
//    ) {
//
//        val user = Firebase.auth.currentUser?.uid
//
//        val dataObject = MyData(user,getStockName,getDate,getTransactionType,getQuantity,getAmount)
//
//        database.child("Sijan Client").child(getStockName).setValue(dataObject)
//
//
//        stockName.text?.clear()
//        date.text?.clear()
//        autoCompleteTransaction.text.clear()
//        quantity.text?.clear()
//        amount.text?.clear()
//        Toast.makeText(this, "Data has been saved to firebase", Toast.LENGTH_LONG).show()
//
//    }
//
//    private fun handleExpansion() {
//        if(hidden_view.visibility == View.VISIBLE)
//        {
//
//            // The transition of the hiddenView is carried out
//            //  by the TransitionManager class.
//            // Here we use an object of the AutoTransition
//            // Class to create a default transition.
//
//            // The transition of the hiddenView is carried out
//            //  by the TransitionManager class.
//            // Here we use an object of the AutoTransition
//            // Class to create a default transition.
//            TransitionManager.beginDelayedTransition(base_cardview,
//                AutoTransition())
//            hidden_view.visibility = View.GONE
//            expandButton.setImageResource(R.drawable.downarrowgoogle)
//        }
//        // If the CardView is not expanded, set its visibility
//        // to visible and change the expand more icon to expand less.
//        else {
//
//            TransitionManager.beginDelayedTransition(base_cardview,
//                AutoTransition())
//            hidden_view.visibility = View.VISIBLE;
//            expandButton.setImageResource(R.drawable.uparrowgoogle);
//        }
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//
//        val ref = database.child("Sijan Client")
//
//
//        ref.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
//
//    }
//

//
//
//