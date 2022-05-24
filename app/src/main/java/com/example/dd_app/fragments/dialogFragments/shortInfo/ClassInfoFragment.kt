package com.example.dd_app.fragments.dialogFragments.shortInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.ClassData
import com.example.dd_app.databinding.DialogFragmentCharacterItemBinding
import com.example.dd_app.fragments.contact.navigator
import com.squareup.picasso.Picasso

class ClassInfoFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentCharacterItemBinding
    private lateinit var allClass: ClassData
    private lateinit var descrClass: String
    private lateinit var urlClass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            allClass = it.getSerializable(ARG_PARAM1) as ClassData
            urlClass = it.getSerializable(ARG_PARAM2) as String
            descrClass = it.getSerializable(ARG_PARAM3) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentCharacterItemBinding.inflate(inflater, container, false)

        Picasso.get().load(urlClass).fit().into(binding.icoCharacter)
        binding.nameCharacter.text = allClass.name
        var str = getString(R.string.master_bonus) + " " + allClass.masterBonus
        binding.hp.text = str
        str = getString(R.string.numb_spell) + " " + allClass.numbAVSpells
        binding.power.text = str
        str = getString(R.string.description) + " " + descrClass
        binding.agility.text = str

        binding.spellBtn.visibility = View.VISIBLE
        binding.spellBtn.setOnClickListener {
            this.onDestroyView()
            navigator().goToSpellsFrag(allClass.id)
        }

        binding.bodyType.visibility = View.INVISIBLE
        binding.intellect.visibility = View.INVISIBLE
        binding.wisdom.visibility = View.INVISIBLE
        binding.charisma.visibility = View.INVISIBLE
        binding.dmgBuff.visibility = View.INVISIBLE
        binding.hpBuff.visibility = View.INVISIBLE
        binding.notes.visibility = View.INVISIBLE

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "items2"

        @JvmStatic
        private val ARG_PARAM3 = "items3"

        @JvmStatic
        fun newInstance(allClass: ClassData, urlClass: String, descrClass: String) =
            ClassInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, allClass)
                    putSerializable(ARG_PARAM2, urlClass)
                    putSerializable(ARG_PARAM3, descrClass)
                }
            }
    }
}