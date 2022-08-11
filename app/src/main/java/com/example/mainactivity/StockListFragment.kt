package com.example.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainactivity.savedata.AdapterData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_stock_list.*
import java.io.Serializable


class StockListFragment : Fragment(),MyAdapter.OnItemClickListener {


    private lateinit var databaseReference: DatabaseReference
    private lateinit var myList : ArrayList<AdapterData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_list, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // initialising the lateinit declared varaible
        databaseReference = FirebaseDatabase.getInstance().reference.child("Shares")
        myList = arrayListOf()

        stocksListRecycler.setHasFixedSize(true)
        stocksListRecycler.layoutManager = LinearLayoutManager(activity)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (myObject in snapshot.children) {
                        val i = myObject.getValue(AdapterData::class.java)
                        myList.add(i!!)


                    }
                }

                val adapter = MyAdapter(myList,this@StockListFragment)
                stocksListRecycler?.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    override fun onItemClick(position: Int) {



        // stroing the Adapter data into clickObject to send over the another activity
        val clickedObject : AdapterData = myList[position]

//        Toast.makeText(activity,"${clickedObject.stockName}  clicked",Toast.LENGTH_LONG).show()


        val intent = Intent(activity,RecyclerItemView::class.java)

        // to send object the object must be Serializable
        intent.putExtra("listOfData",clickedObject as Serializable)

        startActivity(intent)
    }


}