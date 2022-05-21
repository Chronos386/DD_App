package com.example.dd_app.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_app.R
import com.example.dd_app.dataSource.GameData
import com.example.dd_app.help_components.GoToGame

class GamesAdapter(private val context: Context, private val list: ArrayList<GameData>,
                   private val inter: GoToGame):
    RecyclerView.Adapter<GamesAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val gamesName: TextView = view.findViewById<View>(R.id.name_game_spis) as TextView
        val worldName: TextView = view.findViewById<View>(R.id.name_world_spis) as TextView
        val masterName: TextView = view.findViewById<View>(R.id.master_name_spis) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.games_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        var str = context.getString(R.string.name_game) + " " + data.gameName
        holder.gamesName.text = str
        str = context.getString(R.string.name_world) + " " + data.worldName
        holder.worldName.text = str
        str = context.getString(R.string.name_game_master) + " " + data.masterID
        holder.masterName.text = str
        holder.itemView.setOnClickListener {
            inter.onClicked(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}