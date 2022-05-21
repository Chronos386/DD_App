package com.example.dd_app.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dd_app.R
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.help_components.GoToCharacter
import com.squareup.picasso.Picasso

class CharactersAdapter(private val context: Context, private val list: ArrayList<CharacterData>,
                        private val inter: GoToCharacter):
    RecyclerView.Adapter<CharactersAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val spisPicture: ImageView = view.findViewById<View>(R.id.image_character_spis) as ImageView
        val spisName: TextView = view.findViewById<View>(R.id.name_character_spis) as TextView
        val spisDmgBuff: TextView = view.findViewById<View>(R.id.dmgBuff_character_spis) as TextView
        val spisHpBuff: TextView = view.findViewById<View>(R.id.hpBuff_character_spis) as TextView
        val spisInfo: TextView = view.findViewById<View>(R.id.inform_character_spis) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.characters_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        holder.spisName.text = data.name
        var str = context.getString(R.string.dmg_buff_characters)
        if(data.dmgBuff) {
            str += " " + context.getString(R.string.present)
            holder.spisDmgBuff.text = str
        }
        else {
            str += " " + context.getString(R.string.absent)
            holder.spisDmgBuff.text = str
        }
        str = context.getString(R.string.hp_buff_characters)
        if(data.hpBuff) {
            str += " " + context.getString(R.string.present)
            holder.spisHpBuff.text = str
        }
        else {
            str += " " + context.getString(R.string.absent)
            holder.spisHpBuff.text = str
        }
        if(data.notes.isNotEmpty()) {
            str = context.getString(R.string.notes_characters) + " " + data.notes
            holder.spisInfo.text = str
        }
        Picasso.get().load(data.pictID).fit().into(holder.spisPicture)
        holder.itemView.setOnClickListener {
            inter.onClicked(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}