package com.mirobotic.picworker.fragments


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView

import com.mirobotic.picworker.R
import kotlinx.android.synthetic.main.fragment_video_player.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class VideoPlayerFragment : Fragment() {

    companion object {
        var stopPosition = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = Uri.parse("android.resource://"+context?.packageName+"/"+R.raw.worker)

        videoPlayer.setVideoURI(uri)
        videoPlayer.setOnPreparedListener {

            it.isLooping = true
           // it.setVolume(0f,0f)
        }

        videoPlayer.start()

    }

    override fun onResume() {
        super.onResume()

        videoPlayer.seekTo(stopPosition)

        videoPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        stopPosition = videoPlayer.currentPosition

        videoPlayer.pause()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putInt("stopPos", stopPosition)

        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val pos  = savedInstanceState?.getInt("stopPos")

        if (pos != null) {
            stopPosition = pos
        }
    }




}
