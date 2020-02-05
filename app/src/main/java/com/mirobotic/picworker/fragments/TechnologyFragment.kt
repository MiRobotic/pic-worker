package com.mirobotic.picworker.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mirobotic.picworker.R
import com.mirobotic.picworker.MainActivity
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_ADAPTIVE
import com.mirobotic.picworker.MainActivity.Companion.SCREEN_TECHNICAL
import com.mirobotic.picworker.SkillDialog
import kotlinx.android.synthetic.main.fragment_technology.*

/**
 * A simple [Fragment] subclass.
 */
class TechnologyFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_technology, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnHome.setOnClickListener {
            onActivityInteractionListener.showHome()
        }

        btnBack.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_ADAPTIVE)
        }

        btnNext.setOnClickListener {
            onActivityInteractionListener.showContent(SCREEN_TECHNICAL)
        }

        viewSkill.setOnClickListener {
            SkillDialog(context!!,
                "\"SkillsFuture for Digital Workplace(SFDW)\"",
                "Thank you for your interest!\n\n" + "Approach our Course Consultant to enquire more on the course & redeem\n" + "a special gift from us.").show()
        }

        viewWorkSpace.setOnClickListener {
            SkillDialog(context!!,
                "\"Workplace IT Skills\"",
                "Thank you for your interest!\n\n" + "Approach our Course Consultant to enquire more on the course & redeem \n"+ "a special gift from us.").show()
        }

        viewSilver.setOnClickListener {
            SkillDialog(context!!,
                "\"Silver Digital Creators\"",
                "Thank you for your interest!\n\n" + "Approach our Course Consultant to enquire more on the course & redeem \n"+"a special gift from us.").show()
        }


    }

}
