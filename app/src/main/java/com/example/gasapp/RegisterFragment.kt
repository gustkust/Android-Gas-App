package com.example.gasapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import java.net.URL
import kotlin.collections.ArrayList


class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val confirmRegisterButton = view.findViewById<Button>(R.id.registerSubmitButton)
//        val pictureView = view.findViewById<EditText>(R.id.pictureInput)
        val loginText = view.findViewById<EditText>(R.id.loginRegisterInput)
        val passwordText = view.findViewById<EditText>(R.id.passwordRegisterInput)

        val pictures = ArrayList<PictureInfo>()

        val thread = Thread() {
            run {
                for (i in 1..6) {
                    val pictureId = (1..100).random()
                    val picture_info_url =
                        URL("https://picsum.photos/id/$pictureId/info").readText()
                    val gson = Gson()
                    pictures.add(
                        gson.fromJson<PictureInfo>(
                            picture_info_url,
                            object : TypeToken<PictureInfo>() {}.type
                        )
                    )
                }
            }
        }

        val pic1 = view.findViewById<ImageView>(R.id.imageView1)
        val pic2 = view.findViewById<ImageView>(R.id.imageView2)
        val pic3 = view.findViewById<ImageView>(R.id.imageView3)
        val pic4 = view.findViewById<ImageView>(R.id.imageView4)
        val pic5 = view.findViewById<ImageView>(R.id.imageView5)
        val pic6 = view.findViewById<ImageView>(R.id.imageView6)

        val pictureIdTextView = view.findViewById<TextView>(R.id.pictureIdTextView)

        thread.start()
        while (pictures.size != 6) {

        }
        Picasso.get().load(pictures[0].download_url)
            .into(pic1)
        Picasso.get().load(pictures[1].download_url)
            .into(pic2)
        Picasso.get().load(pictures[2].download_url)
            .into(pic3)
        Picasso.get().load(pictures[3].download_url)
            .into(pic4)
        Picasso.get().load(pictures[4].download_url)
            .into(pic5)
        Picasso.get().load(pictures[5].download_url)
            .into(pic6)

        var pictureUrl = "tmp"

        pic1.setOnClickListener() {
            pictureIdTextView.text = "1"
            pictureUrl = pictures[0].download_url
        }

        pic2.setOnClickListener() {
            pictureIdTextView.text = "2"
            pictureUrl = pictures[1].download_url
        }

        pic3.setOnClickListener() {
            pictureIdTextView.text = "3"
            pictureUrl = pictures[2].download_url
        }

        pic4.setOnClickListener() {
            pictureIdTextView.text = "4"
            pictureUrl = pictures[3].download_url
        }

        pic5.setOnClickListener() {
            pictureIdTextView.text = "5"
            pictureUrl = pictures[4].download_url
        }

        pic6.setOnClickListener() {
            pictureIdTextView.text = "6"
            pictureUrl = pictures[5].download_url
        }


        val dbHelper = DBHelper(context)
        confirmRegisterButton.setOnClickListener {
            if (dbHelper.addUser(
                    User(
                        0,
                        loginText.text.toString(),
                        passwordText.text.toString(),
                        pictureUrl
                    )
                )
            ) {
                Toast.makeText(
                    context,
                    "Registration completed, you can now log in.",
                    Toast.LENGTH_LONG
                ).show()
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

    fun passData(data: String) {
        dataPasser.onDataPass(data)
    }
}