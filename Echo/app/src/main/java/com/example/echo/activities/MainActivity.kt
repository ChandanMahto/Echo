package com.example.echo.activities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.adapters.NavigationDrawerAdapter
import com.example.echo.fragments.MainActivityFragment
import com.example.echo.fragments.SongPlayingFragment

class MainActivity : AppCompatActivity() {
    var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    var images_for_navdrawer = intArrayOf(R.drawable.navigation_allsongs,R.drawable.navigation_favorites,R.drawable.navigation_settings,
        R.drawable.navigation_aboutus)
    object statified{
        var drawerLayout: DrawerLayout?=null
        var notificationManager:NotificationManager?=null
    }
    var trackNotificationBuilder: Notification?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar= findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        navigationDrawerIconsList.add("All Songs")
        navigationDrawerIconsList.add("Favourite")
        navigationDrawerIconsList.add("Settings")
        navigationDrawerIconsList.add("Contact Us")
        MainActivity.statified.drawerLayout=findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this@MainActivity,MainActivity.statified.drawerLayout,toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        MainActivity.statified.drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()
        val mainScreenFragment= MainActivityFragment()
        this.supportFragmentManager
            .beginTransaction().add(R.id.details_fragment, mainScreenFragment, "MainActivityFragment")
            .commit()

        var _navigationAdapter=NavigationDrawerAdapter(navigationDrawerIconsList,images_for_navdrawer,this)
        _navigationAdapter.notifyDataSetChanged()
        var navigation_recycler_view=findViewById<RecyclerView>(R.id.navigationRecyclerView)
        navigation_recycler_view.layoutManager=LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator=DefaultItemAnimator()
        navigation_recycler_view.adapter=_navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)
        val intent = Intent(this@MainActivity,MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this@MainActivity,System.currentTimeMillis().toInt(),
               intent,0)
        trackNotificationBuilder = Notification.Builder(this)
            .setContentTitle("A track is being played in the background")
            .setSmallIcon(R.drawable.echo_logo)
            .setContentIntent(pIntent)
            .setOngoing(true)
            .setAutoCancel(true)
            .build()
        statified.notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStart() {
        super.onStart()
        try {
             statified.notificationManager?.cancel(1978)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            if(SongPlayingFragment.Statified.mediaplayer?.isPlaying as Boolean){
                statified.notificationManager?.notify(1978,trackNotificationBuilder)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            statified.notificationManager?.cancel(1978)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}
