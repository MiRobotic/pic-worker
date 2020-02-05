package com.mirobotic.picworker.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mirobotic.picworker.MainActivity
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_LOCATE_US
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_TECHNOLOGY

import com.mirobotic.picworker.R
import com.mirobotic.picworker.SkillDialog
import kotlinx.android.synthetic.main.fragment_technical.*

/**
 * A simple [Fragment] subclass.
 */
class TechnicalFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_technical, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnHome.setOnClickListener {
            onActivityInteractionListener.showHome()
        }

        btnBack.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_TECHNOLOGY)
        }

        btnNext.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_LOCATE_US)
        }

        viewAgile.setOnClickListener {
            SkillDialog(context!!,
                "\"Agile, ITIL , Project Management & Business Analysis\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n a special gift from us.").show()
        }

        viewCyberSecurity.setOnClickListener {
            SkillDialog(context!!,
                "\"Cybersecurity\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n a special gift from us.").show()
        }

        viewDataProtection.setOnClickListener {
            SkillDialog(context!!,
                "\"Data Protection & Privacy\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n a special gift from us.").show()
        }

        viewFoundation.setOnClickListener {
            SkillDialog(context!!,
                "\"Foundational IT Courses\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n a special gift from us.").show()
        }

        viewOracle.setOnClickListener {
            SkillDialog(context!!,
                "\"Oracle Business Applications\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n  a special gift from us.").show()
        }

        viewWorkSpace.setOnClickListener {
            SkillDialog(context!!,
                "\"Workplace Safety & Health\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n a special gift from us.").show()
        }

        viewSecurity.setOnClickListener {
            SkillDialog(context!!,
                "\"Security\"",
                "Thank you for your interest!\n\nApproach our Course Consultant to enquire more on the course & redeem \n a special gift from us.").show()
        }

    }

}
