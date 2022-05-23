package com.example.gasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UsersFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DBHelper(context)

        val view = inflater.inflate(R.layout.fragment_users, container, false)

        val recView = view.findViewById<RecyclerView>(R.id.recyclerView)

        layoutManager = LinearLayoutManager(context)
        recView.layoutManager = layoutManager

        adapter = RecyclerAdapter(context, dbHelper.getUsers())
        recView.adapter = adapter

        return view
    }

}