package com.example.dd_app.fragments.dialogFragments.shortInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.VarRaceData
import com.example.dd_app.databinding.DialogFragmentInfoVarRaceBinding

class VarRaceInfoFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentInfoVarRaceBinding
    private lateinit var allVarRace: VarRaceData
    private lateinit var descr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            allVarRace = it.getSerializable(ARG_PARAM1) as VarRaceData
            descr = it.getSerializable(ARG_PARAM2) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentInfoVarRaceBinding.inflate(inflater, container, false)

        binding.name.text = allVarRace.name
        var str = getString(R.string.add_feat) + " " + allVarRace.addFeat
        binding.priceAddFeat.text = str
        str = getString(R.string.incr_char) + " " + allVarRace.incrChar
        binding.weightIncrChar.text = str
        str = getString(R.string.description) + " " + descr
        binding.stealHindrDamageDescr.text = str

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "items2"

        @JvmStatic
        fun newInstance(allRace: VarRaceData, descrClass: String) =
            VarRaceInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, allRace)
                    putSerializable(ARG_PARAM2, descrClass)
                }
            }
    }
}