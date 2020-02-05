package com.mirobotic.picworker.fragments


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mirobotic.picworker.R
import kotlinx.android.synthetic.main.fragment_worker.*

/**
 * A simple [Fragment] subclass.
 */
class WorkerFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_worker, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnHome.setOnClickListener {
            onActivityInteractionListener.showHome()
        }




        val uri = Uri.parse("android.resource://"+context?.packageName+"/"+R.raw.intro)

        videoPlayer.setVideoURI(uri)
        videoPlayer.setOnPreparedListener {

            it.isLooping = true
          //  it.setVolume(0f,0f)
        }


    }

    override fun onResume() {
        super.onResume()
        videoPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.pause()
    }

}
