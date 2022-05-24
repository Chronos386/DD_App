package com.example.dd_app.fragments.dialogFragments.shortInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataSource.WeapData
import com.example.dd_app.databinding.DialogFragmentInfoVarRaceBinding

class WeaponInfoFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentInfoVarRaceBinding
    private lateinit var weap: WeapData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weap = it.getSerializable(ARG_PARAM1) as WeapData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentInfoVarRaceBinding.inflate(inflater, container, false)

        binding.name.text = weap.name
        var str = getString(R.string.price) + " " + weap.price + " " + getString(R.string.gold_coins)
        binding.priceAddFeat.text = str
        str = getString(R.string.weight) + " " + weap.weight + " " + getString(R.string.weight_)
        binding.weightIncrChar.text = str
        str = getString(R.string.damage) + " " + weap.damage
        binding.stealHindrDamageDescr.text = str

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(weap: WeapData) =
            WeaponInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, weap)
                }
            }
    }
}