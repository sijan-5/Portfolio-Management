package com.example.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.savedata.AdapterData
import kotlinx.android.synthetic.main.recyclerrow.view.*

class MyAdapter(private val myList: ArrayList<AdapterData>,private  val listener : OnItemClickListener)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    inner class MyViewHolder(view:View) : RecyclerView.ViewHolder(view),View.OnClickListener {

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val position:Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {

                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerrow,parent,false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val obtainedData = myList[position]
        holder.itemView.sname.text = obtainedData.stockName.toString()
        holder.itemView.sQuantity.text = obtainedData.quantity.toString()
        holder.itemView.qAmount.text = obtainedData.amount.toString()
        holder.itemView.sDate.text = obtainedData.date.toString()
        holder.itemView.status.text = obtainedData.transactionType.toString()
    }

    override fun getItemCount(): Int = myList.size



    interface OnItemClickListener
    {
        fun onItemClick(position: Int)
    }
}