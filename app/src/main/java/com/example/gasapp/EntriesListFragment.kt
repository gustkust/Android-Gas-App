package com.example.gasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class EntriesListFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<EntryRecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entries_list, container, false)

        val recView = view.findViewById<RecyclerView>(R.id.recyclerView2)
        layoutManager = LinearLayoutManager(context)
        recView.layoutManager = layoutManager

        val dbHelper = DBHelper(context)

        val name = arguments?.getString("name")
        val user = name?.let { dbHelper.getUserByName(it) }
        val entries = user?.let { dbHelper.getUsersEntries(it) }

        adapter = entries?.let {
            EntryRecyclerAdapter(
                context,
                it
            )
        }
        recView.adapter = adapter
        // Inflate the layout for this fragment
        return view
    }
}