package com.example.mainactivity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mainactivity.savedata.MyData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var arrayList : ArrayList<String>
    private lateinit var totalUnit : ArrayList<Int>
    private lateinit var totalSOld : ArrayList<Int>
    private lateinit var totalBuy : ArrayList<Int>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference
        firebaseAuth = Firebase.auth
        arrayList = ArrayList()
        totalUnit = ArrayList()
        totalSOld = ArrayList()
        totalBuy = ArrayList()



        // setting up the autocomplete text for buy or sell
        val transactionType = resources.getStringArray(R.array.transactionType)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,transactionType)
        autoCompleteTransaction.setAdapter(arrayAdapter)




       // to see the the total information of the portfolio expand the layout
        expandButton.setOnClickListener {
            handleExpansion()

        }

        // to save the the Stock information
        saveButton.setOnClickListener {

            handleNullData(it)

        }


       val database2 = database.child("Shares")

        database2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    for (stocks in snapshot.children)
                    {
                        // store single data from the child and store into the temporary variable
                            // the adding to the arraylist
                        val getStocks = stocks.child("stockName").value.toString()
                        val getUnits = stocks.child("quantity").value.toString().toInt()
                        val type = stocks.child("transactionType").value.toString()

                        //if transaction type is sell then add information to the sell attributes
                        if (type == "Sell")
                        {
                            val sellUnits = stocks.child("amount").value.toString().toInt()
                            val sellAmounts = stocks.child("quantity").value.toString().toInt()
                            totalSOld.add(sellAmounts * sellUnits)
                        }


                        else if(type == "Buy")
                        {
                            val buyUnits = stocks.child("amount").value.toString().toInt()
                            val buyAmounts = stocks.child("quantity").value.toString().toInt()

                            totalBuy.add(buyUnits * buyAmounts)
                        }


                        arrayList.add(getStocks)
                        totalUnit.add(getUnits)

                    }

                    setAdapter(arrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Something went wrong",Toast.LENGTH_LONG).show()
                Log.d("Error","Occured")
            }

        })





    }

    private fun setAdapter(arrayList: ArrayList<String>) {



        //setting the auto comeplete textview  for the stock name from the firebase database
        val firstAdapter = ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,arrayList)
        stockName.setAdapter(firstAdapter)

        units.text = "${totalUnit.sum() }"
        totalSoldAmount.text = "${totalSOld.sum()}"
        totalInvestment.text = "${totalBuy.sum()}"


        //calculating profit
        val decision = totalBuy.sum() - totalSOld.sum()
        if (decision > 0 )
        {
            plStatus.text = "Overall Profit: "
            pla.text = decision.toString()
        }
        else
        {
            plStatus.text = "Overall Loss :"
            pla.text = decision.toString()
        }



    }


    // checking if the data value are not null
    private fun handleNullData(arrive: View) {


        when
        {

            TextUtils.isEmpty(stockName.text.toString().trim { it <= ' ' }) ->
            {
                stockName.error = "Stock Name is required"
                stockName.requestFocus()
                return
            }
            TextUtils.isEmpty(date.text.toString().trim { it <= ' ' }) ->
            {
                stockName.error = "Date is required"
                stockName.requestFocus()
                return
            }


            TextUtils.isEmpty(autoCompleteTransaction.text.toString().trim { it <= ' ' }) ->
            {
                autoCompleteTransaction.error = "This is required"
                autoCompleteTransaction.requestFocus()
                return
            }

            TextUtils.isEmpty(quantity.text.toString().trim { it <= ' ' }) ->
            {
                quantity.error = "Quantity number is required"
                quantity.requestFocus()
                return
            }

            TextUtils.isEmpty(amount.text.toString().trim { it <= ' ' })  ->
            {
                amount.error = "Amount is required"
                amount.requestFocus()
                return
            }

            else ->
            {
                val getStockName = stockName.text.toString()
                val getDate = date.text.toString()
                val getTransactionType = autoCompleteTransaction.text.toString()
                val getQuantity = quantity.text.toString().toInt()
                val getAmount = amount.text.toString().toInt()
                saveData(getStockName,getDate,getTransactionType,getQuantity,getAmount)

            }

        }

    }


    private fun saveData(
        getStockName: String,
        getDate: String,
        getTransactionType: String,
        getQuantity: Int,
        getAmount: Int
    ) {

        val user = Firebase.auth.currentUser?.uid

        val dataObject = MyData(user,getStockName,getDate,getTransactionType,getQuantity,getAmount)

        database.child("Shares").child(getStockName).setValue(dataObject)


        stockName.text?.clear()
        date.text?.clear()
        autoCompleteTransaction.text?.clear()
        quantity.text?.clear()
        amount.text?.clear()
        Toast.makeText(activity, "Data has been saved to firebase", Toast.LENGTH_LONG).show()

    }

    private fun handleExpansion() {
        if(hidden_view.visibility == View.VISIBLE)
        {

            // The transition of the hiddenView is carried out
            //  by the TransitionManager class.
            // Here we use an object of the AutoTransition
            // Class to create a default transition.

            // The transition of the hiddenView is carried out
            //  by the TransitionManager class.
            // Here we use an object of the AutoTransition
            // Class to create a default transition.
            TransitionManager.beginDelayedTransition(base_cardview,
                AutoTransition())
            hidden_view.visibility = View.GONE
            expandButton.setImageResource(R.drawable.downarrowgoogle)
        }
        // If the CardView is not expanded, set its visibility
        // to visible and change the expand more icon to expand less.
        else {

            TransitionManager.beginDelayedTransition(base_cardview,
                AutoTransition())
            hidden_view.visibility = View.VISIBLE
            expandButton.setImageResource(R.drawable.uparrowgoogle)
        }
    }



}


