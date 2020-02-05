package com.mirobotic.picworker.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_ADAPTIVE
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_LOCATE_US
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_TECHNICAL
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_TECHNOLOGY
import com.mirobotic.picworker.R
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var onActivityInteractionListener: OnActivityInteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onActivityInteractionListener = context as OnActivityInteractionListener

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdaptive.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_ADAPTIVE)
        }

        btnLocateUs.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_LOCATE_US)
        }

        btnTechnical.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_TECHNICAL)
        }

        btnTechnology.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_TECHNOLOGY)
        }

        btnWorker.setOnClickListener {
            onActivityInteractionListener.showWorker()
        }
    }

}
