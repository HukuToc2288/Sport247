package ru.hukutoc2288.sport247

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.hukutoc2288.sport247.ui.dashboard.PlayersFragment
import ru.hukutoc2288.sport247.ui.home.HomeFragment
import ru.hukutoc2288.sport247.ui.notifications.GalleryFragment
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

fun readFromFile(fileName: String?, context: Context): String {
    var inputStream: InputStream? = null
    var inputStreamReader: InputStreamReader? = null
    inputStream = context.resources.assets.open(fileName!!, 0)
    inputStreamReader = InputStreamReader(inputStream)
    val data = ByteArray(inputStream.available())
    inputStream.read(data)
    inputStreamReader.close()
    inputStream.close()
    return String(data, Charset.forName("UTF-8"))
}

class MainActivity : AppCompatActivity() {

    private var currentSport: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        currentSport = intent.getIntExtra(Sports.EXTRA_NAME, Sports.FOOTBALL)
        val pager = findViewById<ViewPager2>(R.id.pager)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.nav_view)
        pager.adapter = ViewPagerFragmentStateAdapter(supportFragmentManager, lifecycle, currentSport)
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //Log.d("pager", "selected "+position)
                bottomNavigation.selectedItemId = when (position) {
                    0 -> R.id.navigation_rules
                    1 -> R.id.navigation_players
                    else -> R.id.navigation_gallery
                }
            }
        })
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            pager.setCurrentItem(
                when (item.itemId) {
                    R.id.navigation_rules -> 0
                    R.id.navigation_players -> 1
                    else -> 2
                }, true
            )
            true
        }
    }

    class ViewPagerFragmentStateAdapter(fm: FragmentManager, lifecycle: Lifecycle, private val currentSport: Int) :
        FragmentStateAdapter(fm, lifecycle) {

        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> HomeFragment()
                1 -> PlayersFragment()
                else -> GalleryFragment()
            }
            val bundle = Bundle()
            bundle.putInt(Sports.EXTRA_NAME, currentSport)
            fragment.arguments = bundle
            return fragment
        }
    }
}