package com.mirobotic.picworker


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_worker.*

/**
 * A simple [Fragment] subclass.
 */
class WorkerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_worker)

        val context = this

        val uri = Uri.parse("android.resource://"+context.packageName+"/"+R.raw.intro)

        videoPlayer.setVideoURI(uri)
        videoPlayer.setOnPreparedListener {

            it.isLooping = true
            //  it.setVolume(0f,0f)
        }

        btnHome.setOnClickListener {
            finish()
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
