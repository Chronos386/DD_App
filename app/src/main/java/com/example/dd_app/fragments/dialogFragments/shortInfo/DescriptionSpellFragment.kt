package com.example.dd_app.fragments.dialogFragments.shortInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.databinding.DialogFragmentDescrSpellBinding

class DescriptionSpellFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentDescrSpellBinding
    private lateinit var spellName: String
    private lateinit var spellDescr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            spellName = it.getSerializable(ARG_PARAM1) as String
            spellDescr = it.getSerializable(ARG_PARAM2) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentDescrSpellBinding.inflate(inflater, container, false)

        binding.title.text = spellName
        binding.description.text = spellDescr

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "item2"

        @JvmStatic
        fun newInstance(spellName: String, spellDescr: String) =
            DescriptionSpellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, spellName)
                    putSerializable(ARG_PARAM2, spellDescr)
                }
            }
    }
}