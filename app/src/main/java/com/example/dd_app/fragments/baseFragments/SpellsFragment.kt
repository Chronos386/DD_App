package com.example.dd_app.fragments.baseFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dd_app.R
import com.example.dd_app.adapters.SpellAdapter
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.DescriptionData
import com.example.dd_app.dataSource.SpellData
import com.example.dd_app.dataSource.arrays.SpellsData
import com.example.dd_app.databinding.FragmentCharactersBinding
import com.example.dd_app.fragments.dialogFragments.shortInfo.DescriptionSpellFragment
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.GoToSpell
import javax.inject.Inject

class SpellsFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private var classID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            classID = it.getSerializable(ARG_PARAM1) as Long
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)

        binding.massage.text = getString(R.string.no_spell)
        binding.progressBar.visibility = View.VISIBLE
        binding.SpisRV.visibility = View.INVISIBLE
        binding.massage.visibility = View.INVISIBLE

        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getSpellsByClass(classID)
            if(netHelper.str.length != 2)
                requireActivity().runOnUiThread {
                    setSpellsInform()
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.SpisRV.visibility = View.VISIBLE
                }
            else
                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.massage.visibility = View.VISIBLE
                }
        })
        thr.start()

        return binding.root
    }

    private fun setSpellsInform() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.SpisRV.layoutManager = linearLayoutManager
        val arrAccounts = SpellsData.fromJson(netHelper.str)
        val adapter = SpellAdapter(requireContext(), arrAccounts, object : GoToSpell {
            override fun onClicked(data: SpellData){
                val thr = Thread(kotlinx.coroutines.Runnable {
                    netHelper.getDescrByID(data.descrID)
                    val desc = DescriptionData.fromJson(netHelper.str)
                    desc?.let { DescriptionSpellFragment.newInstance(data.name, it.field) }
                        ?.show(parentFragmentManager, "customDialog")
                })
                thr.start()
            }
        })
        binding.SpisRV.adapter = adapter
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(classID: Long) =
            SpellsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, classID)
                }
            }
    }
}