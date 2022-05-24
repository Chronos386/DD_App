package com.example.dd_app.fragments.baseFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.*
import com.example.dd_app.databinding.FragmentCharacterInfoPageBinding
import com.example.dd_app.fragments.dialogFragments.whatDoWith.WhatDoWithCharacterFragment
import com.example.dd_app.help_components.DaggerAppComponent
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CharacterFragment : Fragment() {
    private lateinit var binding: FragmentCharacterInfoPageBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    private lateinit var character: CharacterData
    private lateinit var masterName: String

    private lateinit var varRaceChar: VarRaceData
    private lateinit var raceChar: RaceData
    private lateinit var classChar: ClassData
    private lateinit var weapChar: WeapData
    private lateinit var armChar: ArmorData
    private var descRace = ""
    private var descVarRace = ""
    private var descClass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
            character = it.getSerializable(ARG_PARAM2) as CharacterData
            masterName = it.getSerializable(ARG_PARAM3) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentCharacterInfoPageBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        binding.progressBar.visibility = View.VISIBLE
        binding.icoCharacter.visibility = View.INVISIBLE
        binding.nameCharacter.visibility = View.INVISIBLE
        binding.characterInfo.visibility = View.INVISIBLE
        binding.actionButton.visibility = View.INVISIBLE

        binding.nameCharacter.text = character.name
        val thr = Thread(kotlinx.coroutines.Runnable {
            loadAllData()
            requireActivity().runOnUiThread {
                setAllData()
                binding.progressBar.visibility = View.INVISIBLE
                binding.icoCharacter.visibility = View.VISIBLE
                binding.nameCharacter.visibility = View.VISIBLE
                binding.characterInfo.visibility = View.VISIBLE
                binding.actionButton.visibility = View.VISIBLE
            }
        })
        thr.start()

        binding.actionButton.setOnClickListener {
            val dialog = WhatDoWithCharacterFragment.newInstance(acc, character, masterName)
            dialog.show(parentFragmentManager, "customDialog")
        }

        return binding.root
    }

    private fun loadAllData() {
        netHelper.getVarRaceByID(character.varRacesID)
        varRaceChar = VarRaceData.fromJson(netHelper.str)!!
        netHelper.getDescrByID(varRaceChar.descrID)
        var desc = DescriptionData.fromJson(netHelper.str)
        if (desc != null) {
            descVarRace = desc.field
        }

        netHelper.getRaceByID(varRaceChar.racID)
        raceChar = RaceData.fromJson(netHelper.str)!!
        netHelper.getDescrByID(raceChar.descrID)
        desc = DescriptionData.fromJson(netHelper.str)
        if (desc != null) {
            descRace = desc.field
        }

        netHelper.getClassByID(character.classID)
        classChar = ClassData.fromJson(netHelper.str)!!
        netHelper.getDescrByID(classChar.descrID)
        desc = DescriptionData.fromJson(netHelper.str)
        if (desc != null) {
            descClass = desc.field
        }

        netHelper.getWeapByID(character.weapID)
        weapChar = WeapData.fromJson(netHelper.str)!!

        netHelper.getArmorByID(character.armID)
        armChar = ArmorData.fromJson(netHelper.str)!!
    }

    private fun setAllData() {
        Picasso.get().load(character.pictID).fit().into(binding.icoCharacter)
        var str = getString(R.string.hp_character) + " " + character.hp.toString()
        binding.hp.text = str
        str = getString(R.string.power_character) + " " + character.power.toString()
        binding.power.text = str
        str = getString(R.string.agility_character) + " " + character.agility.toString()
        binding.agility.text = str
        str = getString(R.string.bodyType_character) + " " + character.bodyType.toString()
        binding.bodyType.text = str
        str = getString(R.string.intellect_character) + " " + character.intellect.toString()
        binding.intellect.text = str
        str = getString(R.string.wisdom_character) + " " + character.wisdom.toString()
        binding.wisdom.text = str
        str = getString(R.string.charisma_character) + " " + character.charisma.toString()
        binding.charisma.text = str
        str = getString(R.string.dmg_buff_characters)
        str += if(character.dmgBuff)
            " " + getString(R.string.present)
        else
            " " + getString(R.string.absent)
        binding.dmgBuff.text = str
        str = getString(R.string.hp_buff_characters)
        str += if(character.hpBuff)
            " " + getString(R.string.present)
        else
            " " + getString(R.string.absent)
        binding.hpBuff.text = str
        str = getString(R.string.notes_characters) + " " + character.notes
        binding.notes.text = str

        str = getString(R.string.name_game) + " " + raceChar.name
        binding.raceName.text = str
        str = getString(R.string.incr_char) + " " + raceChar.incrChar
        binding.raceIncr.text = str
        str = getString(R.string.worldview) + " " + raceChar.worldview
        binding.worldview.text = str
        str = getString(R.string.size) + " " + raceChar.size
        binding.size.text = str
        str = getString(R.string.speed) + " " + raceChar.speed + " " + getString(R.string.foot)
        binding.speed.text = str
        str = getString(R.string.description) + " " + descRace
        binding.raceDescr.text = str

        str = getString(R.string.name_game) + " " + varRaceChar.name
        binding.varRaceName.text = str
        str = getString(R.string.incr_char) + " " + varRaceChar.incrChar
        binding.varRaceIncr.text = str
        str = getString(R.string.add_feat) + " " + varRaceChar.addFeat
        binding.addFeat.text = str

        str = getString(R.string.name_game) + " " + classChar.name
        binding.className.text = str
        str = getString(R.string.master_bonus) + " " + classChar.masterBonus
        binding.masterBonus.text = str
        str = getString(R.string.numb_spell) + " " + classChar.numbAVSpells
        binding.numbsSpells.text = str
        str = getString(R.string.description) + " " + descClass
        binding.classDescr.text = str

        str = getString(R.string.name_game) + " " + weapChar.name
        binding.weaponName.text = str
        str = getString(R.string.price) + " " + weapChar.price + " " + getString(R.string.gold_coins)
        binding.weaponPrice.text = str
        str = getString(R.string.damage) + " " + weapChar.damage
        binding.weaponDamage.text = str
        str = getString(R.string.weight) + " " + weapChar.weight + " " + getString(R.string.weight_)
        binding.weaponWeight.text = str

        str = getString(R.string.name_game) + " " + armChar.name
        binding.armorName.text = str
        str = getString(R.string.price) + " " + armChar.price + " " + getString(R.string.gold_coins)
        binding.armorPrice.text = str
        str = getString(R.string.weight) + " " + armChar.weight + " " + getString(R.string.weight_)
        binding.armorWeight.text = str
        str = getString(R.string.steal_hindr) + " "
        str += if(armChar.stealHindr)
            getString(R.string.present)
        else
            getString(R.string.absent)
        binding.stealHindr.text = str
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "item2"

        @JvmStatic
        private val ARG_PARAM3 = "item3"

        @JvmStatic
        fun newInstance(account: AccountData, characterItem: CharacterData, masterName: String) =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, account)
                    putSerializable(ARG_PARAM2, characterItem)
                    putSerializable(ARG_PARAM3, masterName)
                }
            }
    }
}