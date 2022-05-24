package com.example.echo.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.Songs
import com.example.echo.activities.MainActivity
import com.example.echo.fragments.MainActivityFragment
import com.example.echo.fragments.SongPlayingFragment
import kotlinx.android.synthetic.main.row_custom_mainscreen_adapter.view.*

class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context:Context): RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>(){
    var songDetails:ArrayList<Songs>?=null
    var mcontext: Context?=null
    init {
        songDetails=_songDetails
        mcontext=_context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent?.context).inflate(R.layout.row_custom_mainscreen_adapter,parent,false)
    return MyViewHolder((itemView))
    }

    override fun getItemCount(): Int {
        if(songDetails==null){
            return 0
        }else{
            return (songDetails as ArrayList<Songs>).size
        }
}

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val songObject = songDetails?.get(position)
        holder.trackTitle?.text=songObject?.songTitle
        holder.trackArtist?.text=songObject?.artist
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment= SongPlayingFragment()
            var args=Bundle()
            args.putString("songArtist",songObject?.artist)
            args.putString("path",songObject?.songData)
            args.putString("songTitle",songObject?.songTitle)
            args.putInt("songId",songObject?.songID?.toInt() as Int)
            args.putInt("songPosition",position)
            args.putParcelableArrayList("songData",songDetails)
            songPlayingFragment.arguments=args
            try {
                if (SongPlayingFragment.Statified.mediaplayer?.isPlaying as Boolean) {
                    SongPlayingFragment.Statified.mediaplayer?.stop()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            (mcontext as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.details_fragment,songPlayingFragment)
                .addToBackStack("SongPlayingFragment")
                .commit()        })
    }

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        var trackTitle:TextView?=null
        var trackArtist:TextView?=null
        var contentHolder: RelativeLayout?= null
        init {
            trackTitle=view.findViewById<TextView>(R.id.trackTitle)
            trackArtist=view.findViewById(R.id.trackArtist)
            contentHolder=view.findViewById(R.id.contentRow)

        }

    }
}