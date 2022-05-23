package com.example.gasapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val confirmRegisterButton = view.findViewById<Button>(R.id.registerSubmitButton)
        val picture = view.findViewById<EditText>(R.id.pictureInput)
        val loginText = view.findViewById<EditText>(R.id.loginRegisterInput)
        val passwordText = view.findViewById<EditText>(R.id.passwordRegisterInput)

        val dbHelper = DBHelper(context)
        confirmRegisterButton.setOnClickListener {
            if (dbHelper.addUser(
                    User(
                        0,
                        loginText.text.toString(),
                        passwordText.text.toString(),
                        picture.text.toString()
                    )
                )) {
                Toast.makeText(context, "Registration completed, you can now log in.", Toast.LENGTH_LONG).show()
                passData("loginRegister")
            } else {
                Toast.makeText(context, "Name already taken.", Toast.LENGTH_LONG).show()
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

    fun passData(data: String){
        dataPasser.onDataPass(data)
    }
}