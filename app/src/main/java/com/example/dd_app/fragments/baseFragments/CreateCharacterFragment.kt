package com.example.dd_app.fragments.baseFragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dd_app.dataFrom.DataFromNetwork
import com.example.dd_app.dataSource.*
import com.example.dd_app.dataSource.arrays.*
import com.example.dd_app.databinding.FragmentCreateCharacterBinding
import com.example.dd_app.fragments.dialogFragments.shortInfo.*
import com.example.dd_app.help_components.DaggerAppComponent
import javax.inject.Inject

class CreateCharacterFragment : Fragment() {
    private lateinit var binding: FragmentCreateCharacterBinding
    @Inject lateinit var netHelper: DataFromNetwork
    private lateinit var acc: AccountData
    private lateinit var game: GameData
    private var ownerID: Long = 0

    private lateinit var allRace: RacesData
    private lateinit var allVarRace: VarRacesData
    private lateinit var allClasses: ClassesData
    private lateinit var allWeap: WeaponsData
    private lateinit var allArm: ArmorsData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            acc = it.getSerializable(ARG_PARAM1) as AccountData
            game = it.getSerializable(ARG_PARAM2) as GameData
            ownerID = it.getSerializable(ARG_PARAM3) as Long
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View {
        binding = FragmentCreateCharacterBinding.inflate(inflater, container, false)
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        makeAllInvisible()

        val name = binding.characterName
        val pict = binding.characterPict
        val notes = binding.notes
        val saveBtn = binding.saveButton

        val health = binding.characterHealth
        val power = binding.characterPower
        val agility = binding.characterAgility
        val bodyType = binding.characterBodyType
        val wisdom = binding.characterWisdom
        val charisma = binding.characterCharisma
        val intellect = binding.characterIntellect

        val spinClass = binding.spinnerClass
        val btnClass = binding.buttonClass
        val spinRace = binding.spinnerRace
        val btnRace = binding.buttonRace
        val spinVarRace = binding.spinnerVarRace
        val btnVarRace = binding.buttonVarRace
        val spinWeap = binding.spinnerWeap
        val btnWeap = binding.buttonWeap
        val spinArm = binding.spinnerArm
        val btnArm = binding.buttonArm

        val thr = Thread(kotlinx.coroutines.Runnable {
            netHelper.getAllClasses()
            allClasses = ClassesData.fromJson(netHelper.str)
            netHelper.getAllRaces()
            allRace = RacesData.fromJson(netHelper.str)

            requireActivity().runOnUiThread {
                makeAllVisible()

                val arrClassName: ArrayList<String> = arrayListOf()
                val arrRaceName: ArrayList<String> = arrayListOf()
                setNamesClass(arrClassName)
                setNamesRace(arrRaceName)
                val adapterClass: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, arrClassName
                )
                val adapterRace: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, arrRaceName
                )
                spinClass.adapter = adapterClass
                spinRace.adapter = adapterRace
            }
        })
        thr.start()

        btnClass.setOnClickListener {
            val id = spinClass.selectedItemId

            val thr2 = Thread(kotlinx.coroutines.Runnable {
                netHelper.getDescrByID(allClasses[id.toInt()].descrID)
                val newDesc = DescriptionData.fromJson(netHelper.str)
                netHelper.getPictByID(allClasses[id.toInt()].pictID)
                val url = netHelper.str
                newDesc?.let { it1 ->
                    ClassInfoFragment.newInstance(
                        allClasses[id.toInt()],
                        url,
                        it1.field
                    )
                }?.show(parentFragmentManager, "customDialog")


                val arrWeapName: ArrayList<String> = arrayListOf()
                val arrArmName: ArrayList<String> = arrayListOf()
                netHelper.getAllWeapByClassID(allClasses[id.toInt()].id)
                allWeap = WeaponsData.fromJson(netHelper.str)
                setNamesWeap(arrWeapName)
                netHelper.getAllArmByClassID(allClasses[id.toInt()].id)
                allArm = ArmorsData.fromJson(netHelper.str)
                setNamesArmor(arrArmName)

                val adapterWeap: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, arrWeapName
                )
                val adapterArm: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, arrArmName
                )
                requireActivity().runOnUiThread {
                    spinWeap.adapter = adapterWeap
                    spinArm.adapter = adapterArm
                }
            })
            thr2.start()
        }

        btnRace.setOnClickListener {
            val id = spinRace.selectedItemId
            val thr2 = Thread(kotlinx.coroutines.Runnable {
                netHelper.getDescrByID(allRace[id.toInt()].descrID)
                val newDesc = DescriptionData.fromJson(netHelper.str)
                netHelper.getPictByID(allRace[id.toInt()].pictID)
                val url = netHelper.str
                newDesc?.let { it1 ->
                    RaceInfoFragment.newInstance(
                        allRace[id.toInt()],
                        url,
                        it1.field
                    )
                }?.show(parentFragmentManager, "customDialog")
                val arrVarRaceName: ArrayList<String> = arrayListOf()
                netHelper.getAllVarRacesByRaceID(allRace[id.toInt()].id)
                allVarRace = VarRacesData.fromJson(netHelper.str)
                setNamesVerRace(arrVarRaceName)
                val adapterVarRace: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, arrVarRaceName
                )
                requireActivity().runOnUiThread {
                    spinVarRace.adapter = adapterVarRace
                }
            })
            thr2.start()
        }

        btnVarRace.setOnClickListener {
            if(spinVarRace.adapter == null) {
                val toastTXT = "Выберите подрасу"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
            }
            else {
                val id = spinVarRace.selectedItemId
                val thr2 = Thread(kotlinx.coroutines.Runnable {
                    netHelper.getDescrByID(allVarRace[id.toInt()].descrID)
                    val newDesc = DescriptionData.fromJson(netHelper.str)
                    if (newDesc != null) {
                        println(newDesc.field)
                    }
                    newDesc?.let { it1 ->
                        VarRaceInfoFragment.newInstance(
                            allVarRace[id.toInt()],
                            it1.field
                        )
                    }?.show(parentFragmentManager, "customDialog")
                })
                thr2.start()
            }
        }

        btnWeap.setOnClickListener {
            if(spinWeap.adapter == null) {
                val toastTXT = "Выберите оружие"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
            }
            else {
                val id = spinWeap.selectedItemId
                val dialog = WeaponInfoFragment.newInstance(allWeap[id.toInt()])
                dialog.show(parentFragmentManager, "customDialog")
            }
        }

        btnArm.setOnClickListener {
            if(spinArm.adapter == null) {
                val toastTXT = "Выберите броню"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
            }
            else {
                val id = spinArm.selectedItemId
                val dialog = ArmorInfoFragment.newInstance(allArm[id.toInt()])
                dialog.show(parentFragmentManager, "customDialog")
            }
        }

        saveBtn.setOnClickListener {
            var bool = true
            if(name.text.isEmpty())
                bool = false
            if(health.text.isEmpty())
                bool = false
            if(power.text.isEmpty())
                bool = false
            if(agility.text.isEmpty())
                bool = false
            if(bodyType.text.isEmpty())
                bool = false
            if(wisdom.text.isEmpty())
                bool = false
            if(charisma.text.isEmpty())
                bool = false
            if(intellect.text.isEmpty())
                bool = false
            if(spinVarRace.adapter == null)
                bool = false
            if(spinWeap.adapter == null)
                bool = false
            if(spinArm.adapter == null)
                bool = false
            if(bool) {
                val character = CharacterData(ownerID, agility.text.toString().toLong(), allArm[
                        spinArm.selectedItemId.toInt()].id, bodyType.text.toString().toLong(),
                    charisma.text.toString().toLong(), allClasses[spinClass.selectedItemId.toInt()]
                        .id, false, game.id, health.text.toString().toLong(), false,
                1, intellect.text.toString().toLong(), name.text.toString(), notes.text.toString(),
                    pict.text.toString(), power.text.toString().toLong(), allVarRace[spinVarRace
                        .selectedItemId.toInt()].id, allWeap[spinWeap.selectedItemId.toInt()].id,
                    wisdom.text.toString().toLong())
                val thr2 = Thread(kotlinx.coroutines.Runnable {
                    println(character.toJson())
                    netHelper.addCharacter(character.toJson())
                })
                thr2.start()
                requireActivity().onBackPressed()
            }
            else {
                val toastTXT = "Заполните все обязательные поля"
                Toast.makeText(requireContext(), toastTXT, Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun setNamesVerRace(arrVarRaceName: ArrayList<String>) {
        for(i in allVarRace) {
            arrVarRaceName.add(i.name)
        }
    }

    private fun setNamesArmor(arrArmName: ArrayList<String>) {
        for(i in allArm) {
            arrArmName.add(i.name)
        }
    }

    private fun setNamesWeap(arrWeapName: ArrayList<String>) {
        for(i in allWeap) {
            arrWeapName.add(i.name)
        }
    }

    private fun setNamesClass(arrClassName: ArrayList<String>) {
        for(i in allClasses) {
            arrClassName.add(i.name)
        }
    }

    private fun setNamesRace(arrRaceName: ArrayList<String>) {
        for(i in allRace) {
            arrRaceName.add(i.name)
        }
    }

    private fun makeAllInvisible() {
        binding.nameGame.visibility = View.INVISIBLE
        binding.characterName.visibility = View.INVISIBLE
        binding.characterPict.visibility = View.INVISIBLE
        binding.notes.visibility = View.INVISIBLE
        binding.saveButton.visibility = View.INVISIBLE

        binding.health.visibility = View.INVISIBLE
        binding.characterHealth.visibility = View.INVISIBLE
        binding.power.visibility = View.INVISIBLE
        binding.characterPower.visibility = View.INVISIBLE
        binding.agility.visibility = View.INVISIBLE
        binding.characterAgility.visibility = View.INVISIBLE
        binding.bodyType.visibility = View.INVISIBLE
        binding.characterBodyType.visibility = View.INVISIBLE
        binding.wisdom.visibility = View.INVISIBLE
        binding.characterWisdom.visibility = View.INVISIBLE
        binding.charisma.visibility = View.INVISIBLE
        binding.characterCharisma.visibility = View.INVISIBLE
        binding.intellect.visibility = View.INVISIBLE
        binding.characterIntellect.visibility = View.INVISIBLE

        binding.spinnerClass.visibility = View.INVISIBLE
        binding.buttonClass.visibility = View.INVISIBLE
        binding.spinnerRace.visibility = View.INVISIBLE
        binding.buttonRace.visibility = View.INVISIBLE
        binding.spinnerVarRace.visibility = View.INVISIBLE
        binding.buttonVarRace.visibility = View.INVISIBLE
        binding.spinnerWeap.visibility = View.INVISIBLE
        binding.buttonWeap.visibility = View.INVISIBLE
        binding.spinnerArm.visibility = View.INVISIBLE
        binding.buttonArm.visibility = View.INVISIBLE

        binding.progressBar.visibility = View.VISIBLE
    }

    private fun makeAllVisible() {
        binding.nameGame.visibility = View.VISIBLE
        binding.characterName.visibility = View.VISIBLE
        binding.characterPict.visibility = View.VISIBLE
        binding.notes.visibility = View.VISIBLE
        binding.saveButton.visibility = View.VISIBLE

        binding.health.visibility = View.VISIBLE
        binding.characterHealth.visibility = View.VISIBLE
        binding.power.visibility = View.VISIBLE
        binding.characterPower.visibility = View.VISIBLE
        binding.agility.visibility = View.VISIBLE
        binding.characterAgility.visibility = View.VISIBLE
        binding.bodyType.visibility = View.VISIBLE
        binding.characterBodyType.visibility = View.VISIBLE
        binding.wisdom.visibility = View.VISIBLE
        binding.characterWisdom.visibility = View.VISIBLE
        binding.charisma.visibility = View.VISIBLE
        binding.characterCharisma.visibility = View.VISIBLE
        binding.intellect.visibility = View.VISIBLE
        binding.characterIntellect.visibility = View.VISIBLE

        binding.spinnerClass.visibility = View.VISIBLE
        binding.buttonClass.visibility = View.VISIBLE
        binding.spinnerRace.visibility = View.VISIBLE
        binding.buttonRace.visibility = View.VISIBLE
        binding.spinnerVarRace.visibility = View.VISIBLE
        binding.buttonVarRace.visibility = View.VISIBLE
        binding.spinnerWeap.visibility = View.VISIBLE
        binding.buttonWeap.visibility = View.VISIBLE
        binding.spinnerArm.visibility = View.VISIBLE
        binding.buttonArm.visibility = View.VISIBLE

        binding.progressBar.visibility = View.INVISIBLE
    }

    companion object {
        @JvmStatic
        private val ARG_PARAM1 = "items"

        @JvmStatic
        private val ARG_PARAM2 = "item2"

        @JvmStatic
        private val ARG_PARAM3 = "item3"

        @JvmStatic
        fun newInstance(item: AccountData, gameItem: GameData, ownerID: Long) =
            CreateCharacterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, item)
                    putSerializable(ARG_PARAM2, gameItem)
                    putSerializable(ARG_PARAM3, ownerID)
                }
            }
    }
}