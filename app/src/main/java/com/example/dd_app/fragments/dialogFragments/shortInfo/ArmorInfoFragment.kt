package com.example.dd_app.fragments.dialogFragments.shortInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.ArmorData
import com.example.dd_app.databinding.DialogFragmentInfoVarRaceBinding

class ArmorInfoFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentInfoVarRaceBinding
    private lateinit var arm: ArmorData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arm = it.getSerializable(ARG_PARAM1) as ArmorData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentInfoVarRaceBinding.inflate(inflater, container, false)

        binding.name.text = arm.name
        var str = getString(R.string.price) + " " + arm.price + " " + getString(R.string.gold_coins)
        binding.priceAddFeat.text = str
        str = getString(R.string.weight) + " " + arm.weight + " " + getString(R.string.weight_)
        binding.weightIncrChar.text = str
        str = if(arm.stealHindr)
            getString(R.string.steal_hindr) + " " + getString(R.string.present_)
        else
            getString(R.string.steal_hindr) + " " + getString(R.string.absent_)
        binding.stealHindrDamageDescr.text = str

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(arm: ArmorData) =
            ArmorInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, arm)
                }
            }
    }
}