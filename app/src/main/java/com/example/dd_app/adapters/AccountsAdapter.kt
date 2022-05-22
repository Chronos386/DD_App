package com.example.dd_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_app.R
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.help_components.GoToAcc

class AccountsAdapter(private val context: Context, private val list: ArrayList<AccountData>,
                      private val inter: GoToAcc
):
    RecyclerView.Adapter<AccountsAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val gamerName: TextView = view.findViewById<View>(R.id.gamers_spis) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.gamers_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        holder.gamerName.text = data.login
        holder.itemView.setOnClickListener {
            inter.onClicked(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}