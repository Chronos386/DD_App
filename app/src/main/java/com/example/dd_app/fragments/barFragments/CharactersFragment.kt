package com.example.dd_app.fragments.barFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dd_app.adapters.CharactersAdapter
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.AccountData
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.dataSource.CharactersData
import com.example.dd_app.databinding.FragmentCharactersBinding
import com.example.dd_app.fragments.dialogFragments.CharacterInformDialogFragment
import com.example.dd_app.help_components.DaggerAppComponent
import com.example.dd_app.help_components.GoToCharacter
import javax.inject.Inject

class CharactersFragment : Fragment() {
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        binding.progressBar.visibility = View.VISIBLE
        binding.SpisRV.visibility = View.INVISIBLE
        binding.massage.visibility = View.INVISIBLE
        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getCharactersByAccount(acc.id)
            if(netHelper.str.length != 2)
                requireActivity().runOnUiThread {
                    setCharactersInform()
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

    private fun setCharactersInform() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.SpisRV.layoutManager = linearLayoutManager
        val arrCharacters = CharactersData.fromJson(netHelper.str)
        val adapter = CharactersAdapter(requireContext(), arrCharacters, object : GoToCharacter {
            override fun onClicked(data: CharacterData){
                val dialog = CharacterInformDialogFragment.newInstance(data)
                dialog.show(parentFragmentManager, "customDialog")
            }
        })
        binding.SpisRV.adapter = adapter
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(item: AccountData) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                }
            }
    }
}