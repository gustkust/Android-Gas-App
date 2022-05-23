package com.example.gasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        val textView1 = view.findViewById<TextView>(R.id.textView2)
//        val textView2 = View(context).findViewById<Button>(R.id.textView2)

        textView1.text = arguments?.getString("name")
        // Inflate the layout for this fragment
        return view
    }
}