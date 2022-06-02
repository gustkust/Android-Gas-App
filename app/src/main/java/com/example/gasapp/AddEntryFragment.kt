package com.example.gasapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class AddEntryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val numberRegex = Regex("^\\d+$")
        val doubleRegex = Regex("^[0-9]+\\.[0-9]+\$")

        val view = inflater.inflate(R.layout.fragment_add_entry, container, false)

        val addEntryLoginButton = view.findViewById<Button>(R.id.addEntryButton)
        val priceInput = view.findViewById<TextView>(R.id.priceInput)
        val amountInput = view.findViewById<TextView>(R.id.amountInput)
        val typeInput = view.findViewById<RadioGroup>(R.id.typeRadioGroup)

        val gasButton = view.findViewById<RadioButton>(R.id.rb_gas)
        val repairButton = view.findViewById<RadioButton>(R.id.rb_repair)
        val insuranceButton = view.findViewById<RadioButton>(R.id.rb_insurance)

        val amountText = view.findViewById<TextView>(R.id.amountText)

        val dbHelper = DBHelper(context)
        val user = dbHelper.getUserByName(arguments?.getString("name").toString())

        gasButton.setOnClickListener() {
            if (typeInput.checkedRadioButtonId == R.id.rb_gas) {
                amountInput.visibility = View.VISIBLE
                amountText.visibility = View.VISIBLE
            } else {
                amountInput.text = "0.0"
                amountInput.visibility = View.INVISIBLE
                amountText.visibility = View.INVISIBLE
            }
        }

        repairButton.setOnClickListener() {
            if (typeInput.checkedRadioButtonId == R.id.rb_repair) {
                amountInput.text = "0.0"
                amountInput.visibility = View.INVISIBLE
                amountText.visibility = View.INVISIBLE
            } else {
                amountInput.visibility = View.VISIBLE
                amountText.visibility = View.VISIBLE
            }
        }

        insuranceButton.setOnClickListener() {
            if (typeInput.checkedRadioButtonId == R.id.rb_insurance) {
                amountInput.text = "0.0"
                amountInput.visibility = View.INVISIBLE
                amountText.visibility = View.INVISIBLE
            } else {
                amountInput.visibility = View.VISIBLE
                amountText.visibility = View.VISIBLE
            }
        }

        addEntryLoginButton.setOnClickListener {
            if (priceInput.text.toString().length > 10
                || amountInput.text.toString().length > 10
            ) {
                Toast.makeText(
                    context,
                    "Too long input.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (
                (
                        numberRegex.containsMatchIn(priceInput.text.toString()) ||
                                doubleRegex.containsMatchIn(priceInput.text.toString())
                        ) && (
                        numberRegex.containsMatchIn(amountInput.text.toString()) ||
                                doubleRegex.containsMatchIn(amountInput.text.toString())
                        )

            ) {
                val id = typeInput.checkedRadioButtonId
                dbHelper.addEntry(
                    Entry(
                        0,
                        user?.id ?: 0,
                        view.findViewById<RadioButton>(id).text.toString(),
                        priceInput.text.toString().toDouble(),
                        amountInput.text.toString().toDouble(),
                        ""
                    )
                )
                Toast.makeText(context, "Entry added.", Toast.LENGTH_LONG).show()
                passData("4")
            } else {
                Toast.makeText(context, "Values must be numbers.", Toast.LENGTH_LONG).show()
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    fun passData(data: String) {
        dataPasser.onDataPass(data)
    }
}