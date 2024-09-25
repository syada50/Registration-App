package com.example.userregistrationnsdajob2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileListFragment : Fragment() {

    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_list, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.profileRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Set up adapter
        profileAdapter = ProfileAdapter(/* your data source */)
        recyclerView.adapter = profileAdapter

        return view
    }
}
