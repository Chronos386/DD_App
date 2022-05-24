package com.example.dd_app.fragments.dialogFragments.sureMake
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dd_app.R
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.CharacterData
import com.example.dd_app.databinding.DialogFragmentDelAccountBinding
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class SureDelCharacterFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentDelAccountBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var character: CharacterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getSerializable(ARG_PARAM1) as CharacterData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = DialogFragmentDelAccountBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)

        val title = binding.title
        val cancel = binding.exitBtn
        val confirm = binding.dellBtn

        title.text = getString(R.string.sure_del_character_btn)

        cancel.setOnClickListener {
            this.onDestroyView()
        }

        confirm.setOnClickListener {
            this.onDestroyView()
            val thr = Thread(kotlinx.coroutines.Runnable {
                netHelper.dellCharacter(character.toJson())
            })
            thr.start()
            Thread.sleep(1000)
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        fun newInstance(characterItem: CharacterData) =
            SureDelCharacterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, characterItem)
                }
            }
    }
}