package com.mirobotic.picworker.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_TECHNICAL

import com.mirobotic.picworker.R
import kotlinx.android.synthetic.main.fragment_locate_us.*

/**
 * A simple [Fragment] subclass.
 */
class LocateUsFragment : Fragment() {

    lateinit var onActivityInteractionListener: OnActivityInteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onActivityInteractionListener = context as OnActivityInteractionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locate_us, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnHome.setOnClickListener {
            onActivityInteractionListener.showHome()
        }

        btnBack.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_TECHNICAL)
        }


    }

}
