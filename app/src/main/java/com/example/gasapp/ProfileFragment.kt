package com.example.gasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val name = arguments?.getString("name")

        val dbHelper = DBHelper(context)
        val user = name?.let { dbHelper.getUserByName(it) }

        val bundle = Bundle()
        bundle.putString("name", name)

        val fragment = EntriesListFragment()
        fragment.arguments = bundle

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()

        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        if (user != null) {
            Picasso.get().load(user.picture)
                .into(imageView)
        }

        nameTextView.text = name
        // Inflate the layout for this fragment
        return view
    }
}