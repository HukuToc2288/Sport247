package ru.hukutoc2288.sport247.ui.home

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.hukutoc2288.sport247.R
import ru.hukutoc2288.sport247.Sports
import ru.hukutoc2288.sport247.readFromFile

class HomeFragment : Fragment() {
    var currentSport = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_rules, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        currentSport = arguments?.getInt(Sports.EXTRA_NAME) ?: 0
        textView.text = Html.fromHtml(
            readFromFile(
                if (currentSport == Sports.FOOTBALL) "rules_football.html" else "rules_basketball.html", context!!
            )
        )
        return root
    }
}