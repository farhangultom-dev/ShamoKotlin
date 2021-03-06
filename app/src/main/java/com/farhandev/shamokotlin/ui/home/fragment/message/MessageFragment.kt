package com.farhandev.shamokotlin.ui.home.fragment.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.farhandev.shamokotlin.R

class MessageFragment : Fragment() {

    private lateinit var messageViewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        messageViewModel =
            ViewModelProvider(this).get(MessageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_message, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        messageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}