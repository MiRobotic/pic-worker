package com.mirobotic.picworker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.csjbot.cosclient.constant.ClientConstant
import com.csjbot.cosclient.entity.CommonPacket
import com.csjbot.cosclient.entity.MessagePacket
import com.csjbot.cosclient.listener.EventListener
import com.mirobotic.picworker.fragments.*
import com.mirobotic.picworker.services.RobotService
import com.mirobotic.picworker.utils.MyDateTimeUtils
import com.mirobotic.picworker.utils.Request
import org.json.JSONObject


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

        init()

    }

    override fun onStart() {
        super.onStart()

        sayGreetings(System.currentTimeMillis())
    }

    private fun sayGreetings(time: Long) {

        val msg = "Hello, Good ${MyDateTimeUtils.getGreetingMessage()}."
        speak(msg)

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
                        Log.e(TAG, "Face Detected!")

                        val time = System.currentTimeMillis()

                        if ((GREET_TIME - time) >  GREET_DELAY) {
                            sayGreetings(time)
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
        unbindService(robotConnection)
    }
}
