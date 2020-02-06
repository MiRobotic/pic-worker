package com.mirobotic.picworker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.csjbot.cosclient.constant.ClientConstant
import com.csjbot.cosclient.entity.CommonPacket
import com.csjbot.cosclient.entity.MessagePacket
import com.csjbot.cosclient.listener.EventListener
import com.mirobotic.picworker.fragments.*
import com.mirobotic.picworker.services.RobotService
import com.mirobotic.picworker.speech.GoogleSpechImpl
import com.mirobotic.picworker.speech.ISpeechSpeak
import com.mirobotic.picworker.speech.OnSpeakListener
import com.mirobotic.picworker.speech.SpeechFactory
import com.mirobotic.picworker.utils.MyDateTimeUtils
import com.mirobotic.picworker.utils.Request
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashSet


class MainActivity : AppCompatActivity(), OnActivityInteractionListener {


    companion object {
        const val SCREEN_HOME = 1
        const val SCREEN_ADAPTIVE = 2
        const val SCREEN_LOCATE_US = 3
        const val SCREEN_TECHNOLOGY = 4
        const val SCREEN_TECHNICAL = 5
        const val TAG = "MainActivity"
        private var GREET_TIME = 0L

        const val GREET_DELAY = 30000
    }

    private lateinit var text2Speech: TextToSpeech

    private var context: Context? = null
    private var robotService: RobotService? = null
    private var eventListener: EventListener? = null
    private var failedListener: RobotService.OnEventFailedListener? = null

    private fun speak(text: String) {

        val `object` = JSONObject()
        try {
            `object`.put("msg_id", Request.SPEECH_FROM_TEXT_REQ)
            `object`.put("content", text)
            sendData(`object`.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private val robotConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val robotServiceBinder = service as RobotService.RobotServiceBinder
            robotService = robotServiceBinder.service
            robotService?.connectRobot(eventListener, failedListener, 60002)


        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        if (savedInstanceState == null) {
            showHome()
        }

        text2Speech = TextToSpeech(context,
            TextToSpeech.OnInitListener {

                if(it != TextToSpeech.ERROR) {
//                    text2Speech.language = Locale.ENGLISH
                    text2Speech.setOnUtteranceProgressListener(onSpeakListener)
//                    text2Speech.setPitch(0.7f)
//                    text2Speech.setSpeechRate(0.8f)

                    val features = HashSet<String>()
                    features.add("female")

                    val list = text2Speech.voices

                    var voice: Voice? = null
                    if (list != null && list.size > 0) {

                        for (lang in list) {
                            Log.d(TAG,"voice: lang: $lang")

                            if (lang.name == "en-gb-x-fis#female_1-local") {
                                voice = lang
                                Log.e(TAG,"voice: ${voice.name}")
                                break
                            }

                        }

                    }else {
                        Log.e(TAG,"voice: list is empty")
                    }

                    if (voice == null) {

                        voice = Voice("en-in-x-cxx#female_2-local", Locale.ENGLISH
                            , 400, 200, false, features )
                        Log.e(TAG,"voice: def voice")

                    }

                    text2Speech.voice = voice

                }

                sayGreetings(System.currentTimeMillis())
            })

        init()

    }

    private val onSpeakListener = object : UtteranceProgressListener() {

        override fun onDone(utteranceId: String?) {

            EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.RESUME)

        }

        override fun onError(utteranceId: String?) {

            EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.RESUME)

        }

        override fun onStart(utteranceId: String?) {

            EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.PAUSE)


        }

    }

    override fun onStart() {
        super.onStart()

//        val time = System.currentTimeMillis()
//
//        val diff = time - GREET_TIME
//
//        Log.e(TAG, "onStart! time diff $diff")
//
//        if (diff >  GREET_DELAY) {
//            sayGreetings(time)
//        }
    }

    private fun sayGreetings(time: Long) {

        val msg = "Welcome to NTUC Learning Hub."


        /*
        val speech = GoogleSpechImpl.newInstance(context)

        if(speech.isSpeaking) {
            Toast.makeText(context, "Already Speaking!", Toast.LENGTH_SHORT).show()
            Log.e(TAG,"sayGreetings: Already Speaking!")
            return
        }

        val list = speech.getSpeakerNames("english", "us" )

        if (list != null) {

            for (lang in list) {
                Log.d(TAG,"sayGreetings: lang: $lang")
            }

        }else {
            Log.e(TAG,"sayGreetings: list is empty")
        }

        speech.startSpeaking(msg, object : OnSpeakListener {

            override fun onSpeakBegin() {
                EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.PAUSE)
                Toast.makeText(context, "Speaking!", Toast.LENGTH_SHORT).show()
                Log.e(TAG,"sayGreetings: Speaking")
            }

            override fun onCompleted(i: Int) {
                EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.RESUME)
                Toast.makeText(context, "Completed!", Toast.LENGTH_SHORT).show()
                Log.e(TAG,"sayGreetings: Completed in $i")
            }

        })

         */


        /*
        val speak = SpeechFactory.createSpeech(context, SpeechFactory.SpeechType.GOOGLE)

        if(speak.isSpeaking) {
            Toast.makeText(context, "Already Speaking!", Toast.LENGTH_SHORT).show()
            Log.e(TAG,"sayGreetings: Already Speaking!")
            return
        }

        speak.setLanguage(Locale.ENGLISH)

        val res =  speak.setSpeakerName("")

        speak.startSpeaking(msg, object : OnSpeakListener {

            override fun onSpeakBegin() {
                EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.PAUSE)
                Toast.makeText(context, "Speaking!", Toast.LENGTH_SHORT).show()
                Log.e(TAG,"sayGreetings: Speaking")
            }

            override fun onCompleted(i: Int) {
                EventBus.getDefault().post(VideoPlayerFragment.Companion.Player.RESUME)
                Toast.makeText(context, "Completed!", Toast.LENGTH_SHORT).show()
                Log.e(TAG,"sayGreetings: Completed in $i")
            }

        })



         */

        text2Speech.speak(msg, TextToSpeech.QUEUE_FLUSH, null, "")

//        speak(msg)

        GREET_TIME = time

    }

    private fun init() {
        eventListener = EventListener {

            runOnUiThread {
                when (it.eventType) {
                    ClientConstant.EVENT_RECONNECTED -> Log.d(TAG, " EVENT_RECONNECTED")
                    ClientConstant.EVENT_CONNECT_SUCCESS -> Log.d(TAG, " EVENT_CONNECT_SUCCESS")
                    ClientConstant.EVENT_CONNECT_FAILD -> Log.d(
                        TAG,
                        "EVENT_CONNECT_FAILD" + it.data
                    )
                    ClientConstant.EVENT_CONNECT_TIME_OUT -> Log.d(
                        TAG,
                        "EVENT_CONNECT_TIME_OUT  " + it.data
                    )
                    ClientConstant.SEND_FAILED -> {
                        showMessage(true, "Send Failed")
                        Log.d(TAG, "SEND_FAILED")
                    }
                    ClientConstant.EVENT_DISCONNET -> Log.d(TAG, "EVENT_DISCONNECT")
                    ClientConstant.EVENT_PACKET -> {
                        val packet = it.data as MessagePacket
                        val json = (packet as CommonPacket).contentJson
                        handleResponse(json)
                    }
                    else -> {
                    }
                }//setLanguage();
            }
        }

        failedListener = RobotService.OnEventFailedListener { runOnUiThread { showMessage(true, it) }  }

        val intent = Intent(this, RobotService::class.java)
        bindService(intent, robotConnection, Context.BIND_AUTO_CREATE)

    }

    private fun handleResponse(json: String) {
        try {
            val `object` = JSONObject(json)

            var errorCode = 0

            if (`object`.has("error_code")) {
                errorCode = `object`.getInt("error_code")
            }

            val event = `object`.getString("msg_id")

            Log.d(TAG, "RES >> $`object`")

            if (errorCode == 0) {

                when (event) {

                    Request.DANCE_START_RSP -> showMessage(false, "Dance Start!")
                    Request.DANCE_STOP_RSP -> showMessage(false, "Dance Stop!")

                    Request.SPEECH_START_MULTI_RECOG_RSP -> showMessage(
                        false,
                        "Speech Start Recognition"
                    )

                    Request.SPEECH_STOP_MULTI_RECOG_RSP -> showMessage(
                        false,
                        "Speech Stop Recognition"
                    )
                    Request.FACE_DETECT_NOTIFICATION -> {

                        val time = System.currentTimeMillis()

                        val diff = time - GREET_TIME

                        Log.e(TAG, "Face Detected! time diff $diff")

                        if (diff >  GREET_DELAY) {
                            sayGreetings(time)
                        }else {
                            GREET_TIME = time
                        }

                    }
                    Request.FACE_RECOG_NOTIFICATION -> {

                    }
                    Request.SPEECH_TO_TEXT_NOTIFICATION -> {
                        Log.d(TAG, json)
                        try {
                            val text = `object`.getString("text")
                            Log.e(TAG, "SPEECH_TO_TEXT_NOTIFICATION >> $text")
                            showMessage(false, text)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendData(json: String) {
        try {
            if (robotService != null) {
                robotService?.sendCommand(json)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun showMessage(isError: Boolean, msg: String) {

        if (isError) {
            Log.e(TAG, "error: >> $msg")
        }

        runOnUiThread { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
    }
    override fun showContent(screen: Int) {

        var fragment: Fragment? = null
        var tag = ""

        when (screen) {

            SCREEN_HOME -> {
                tag = HomeFragment::class.java.simpleName
                fragment = HomeFragment()
            }
            SCREEN_ADAPTIVE -> {
                tag = AdaptiveFragment::class.java.simpleName
                fragment = AdaptiveFragment()
            }
            SCREEN_LOCATE_US -> {
                tag = LocateUsFragment::class.java.simpleName
                fragment = LocateUsFragment()
            }
            SCREEN_TECHNICAL -> {
                tag = TechnicalFragment::class.java.simpleName
                fragment = TechnicalFragment()
            }
            SCREEN_TECHNOLOGY -> {
                tag = TechnologyFragment::class.java.simpleName
                fragment = TechnologyFragment()
            }

        }

        Log.e("showContent","tag = $tag | $screen")

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragmentContent, fragment, tag)
            ft.commit()
        }


    }

    override fun showHome() {

        val fm = supportFragmentManager

        val fragment = fm.findFragmentById(R.id.fragmentMain)
        var ft = fm.beginTransaction()

        if (fragment != null) {
            ft.remove(fragment)
        }
        ft.commit()

        ft = fm.beginTransaction()
        ft.replace(R.id.fragmentContent, HomeFragment(), HomeFragment::class.java.simpleName)
        ft.commit()

        ft = fm.beginTransaction()
        ft.replace(R.id.fragmentVideo, VideoPlayerFragment(), VideoPlayerFragment::class.java.simpleName)
        ft.commit()

    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContent)

        if (fragment != null && fragment.tag != null) {

            when(fragment.tag) {

                HomeFragment::class.java.simpleName -> {
                    super.onBackPressed()
                }
                AdaptiveFragment::class.java.simpleName -> {
                    showContent(SCREEN_HOME)
                }
                TechnologyFragment::class.java.simpleName -> {
                    showContent(SCREEN_ADAPTIVE)
                }
                TechnicalFragment::class.java.simpleName -> {
                    showContent(SCREEN_TECHNOLOGY)
                }
                LocateUsFragment::class.java.simpleName -> {
                    showContent(SCREEN_TECHNICAL)
                }
            }


        }

    }

    override fun showWorker() {

        startActivity(Intent(this, WorkerActivity::class.java))

    }

    override fun onDestroy() {
        super.onDestroy()
        text2Speech.shutdown()
        unbindService(robotConnection)
    }
}
