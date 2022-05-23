package com.example.dd_app.fragments.dialogFragments
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.databinding.DialogFragmentCharacterItemBinding
import com.squareup.picasso.Picasso

class CharacterInformFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentCharacterItemBinding
    private lateinit var chData: CharacterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chData = it.getSerializable(ARG_PARAM1) as CharacterData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentCharacterItemBinding.inflate(inflater, container,
            false)
        Picasso.get().load(chData.pictID).fit().into(binding.icoCharacter)
        binding.nameCharacter.text = chData.name
        var str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.hp_character)) }
        str.append(" " + chData.hp)
        binding.hp.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.power_character)) }
        str.append(" " + chData.power)
        binding.power.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.agility_character)) }
        str.append(" " + chData.agility)
        binding.agility.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.bodyType_character)) }
        str.append(" " + chData.bodyType)
        binding.bodyType.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.intellect_character)) }
        str.append(" " + chData.intellect)
        binding.intellect.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.wisdom_character)) }
        str.append(" " + chData.wisdom)
        binding.wisdom.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.charisma_character)) }
        str.append(" " + chData.charisma)
        binding.charisma.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.dmg_buff_characters)) }
        if(!chData.dmgBuff)
            str.append(" " + requireContext().getString(R.string.absent))
        else
            str.append(" " + requireContext().getString(R.string.present))
        binding.dmgBuff.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.hp_buff_characters)) }
        if(!chData.hpBuff)
            str.append(" " + requireContext().getString(R.string.absent))
        else
            str.append(" " + requireContext().getString(R.string.present))
        binding.hpBuff.text = str
        str = SpannableStringBuilder().bold { append(requireContext().getString(R.string.notes_characters)) }
        str.append(" " + chData.notes)
        binding.notes.text = str
        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: CharacterData) =
            CharacterInformFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}