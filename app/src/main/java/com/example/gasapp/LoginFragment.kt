package com.example.gasapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val confirmLoginButton = view.findViewById<Button>(R.id.loginSubmitButton)
        val loginText = view.findViewById<EditText>(R.id.loginInput)
        val passwordText = view.findViewById<EditText>(R.id.passwordLoginInput)

        val dbHelper = DBHelper(context)
        confirmLoginButton.setOnClickListener {
            if (loginText.text.toString().length < 3
                || loginText.text.toString().length > 10
                || passwordText.text.toString().length < 3
                || passwordText.text.toString().length > 10
            ) {
                Toast.makeText(
                    context,
                    "Name and password are between 3 and 10 letters.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (dbHelper.checkPassword(
                        loginText.text.toString(),
                        passwordText.text.toString()
                    )
                ) {
                    Toast.makeText(context, "You are now logged in.", Toast.LENGTH_LONG).show()
                    passData(loginText.text.toString())
                } else {
                    Toast.makeText(context, "Wrong password.", Toast.LENGTH_LONG).show()
                }
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