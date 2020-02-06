package com.mirobotic.picworker.fragments


import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mirobotic.picworker.R
import kotlinx.android.synthetic.main.fragment_video_player.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus

/**
 * A simple [Fragment] subclass.
 */
class VideoPlayerFragment : Fragment() {

    companion object {
        var stopPosition = 0

        enum class Player{
            PAUSE,
            RESUME
        }
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Player) {

        Log.e(tag,"onMessageEvent ${event.name}")

        if (event == Player.PAUSE) {
            onPause()
        }else {
            Handler().postDelayed({
                onResume()
            },500)
        }

    }




}
