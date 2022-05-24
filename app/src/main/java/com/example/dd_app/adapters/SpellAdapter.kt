package com.example.dd_app.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_app.R
import com.example.dd_app.dataSource.SpellData
import com.example.dd_app.help_components.GoToSpell

class SpellAdapter(private val context: Context, private val list: ArrayList<SpellData>,
                   private val inter: GoToSpell
):
    RecyclerView.Adapter<SpellAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val spellName: TextView = view.findViewById<View>(R.id.spell_title) as TextView
        val spellLevel: TextView = view.findViewById<View>(R.id.levelSpell) as TextView
        val durationSpell: TextView = view.findViewById<View>(R.id.duration_spell) as TextView
        val distanceSpell: TextView = view.findViewById<View>(R.id.distance_spell) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.spells_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        holder.spellLevel.text = data.level.toString()
        var str = context.getString(R.string.name_spell) + " " + data.name
        holder.spellName.text = str
        str = context.getString(R.string.duration) + " " + data.duration + " " + context
            .getString(R.string.sec)
        holder.durationSpell.text = str
        str = context.getString(R.string.distance) + " " + data.distance + " " + context
            .getString(R.string.foot)
        holder.distanceSpell.text = str
        holder.itemView.setOnClickListener {
            inter.onClicked(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}