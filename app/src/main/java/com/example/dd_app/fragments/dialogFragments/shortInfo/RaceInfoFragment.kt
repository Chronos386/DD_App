package com.example.dd_app.fragments.dialogFragments.shortInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.RaceData
import com.example.dd_app.databinding.DialogFragmentCharacterItemBinding
import com.squareup.picasso.Picasso

class RaceInfoFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentCharacterItemBinding
    private lateinit var allRace: RaceData
    private lateinit var descr: String
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            allRace = it.getSerializable(ARG_PARAM1) as RaceData
            url = it.getSerializable(ARG_PARAM2) as String
            descr = it.getSerializable(ARG_PARAM3) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentCharacterItemBinding.inflate(inflater, container, false)

        Picasso.get().load(url).fit().into(binding.icoCharacter)
        binding.nameCharacter.text = allRace.name
        var str = getString(R.string.incr_char) + " " + allRace.incrChar
        binding.hp.text = str
        str = getString(R.string.size) + " " + allRace.size
        binding.power.text = str
        str = getString(R.string.speed) + " " + allRace.speed
        binding.agility.text = str
        str = getString(R.string.worldview) + " " + allRace.worldview
        binding.bodyType.text = str
        str = getString(R.string.description) + " " + descr
        binding.intellect.text = str

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
        fun newInstance(allRace: RaceData, url: String, descrClass: String) =
            RaceInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, allRace)
                    putSerializable(ARG_PARAM2, url)
                    putSerializable(ARG_PARAM3, descrClass)
                }
            }
    }
}