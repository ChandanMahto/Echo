package com.example.echo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.activities.MainActivity
import com.example.echo.fragments.AboutUsFragment
import com.example.echo.fragments.FavouriteFragment
import com.example.echo.fragments.MainActivityFragment
import com.example.echo.fragments.SettingsFragment

class NavigationDrawerAdapter(_contentList: ArrayList<String>, _getImages: IntArray, _context: Context)
    : RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>() {
    var contentList: ArrayList<String>?=null
    var getImages: IntArray?=null
    var mcontext:Context?= null
    init {
        this.contentList=_contentList
        this.getImages=_getImages
        this.mcontext=_context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
       var viewItem= LayoutInflater.from(parent?.context).inflate(R.layout.row_custom_navigationdrawer,parent,false)
        val returnView=NavViewHolder(viewItem)
        return returnView
    }

    override fun getItemCount(): Int {
        return contentList?.size as Int
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder?.icon_GET?.setBackgroundResource(getImages?.get(position) as Int)
        holder?.text_Get?.setText(contentList?.get(position))
        holder?.contentHolder?.setOnClickListener({
            if(position==0){
                val mainScreenFragment= MainActivityFragment()
                (mcontext as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.details_fragment,mainScreenFragment)
                    .commit()
            }else if(position==1){
                val favouriteFragment= FavouriteFragment()
                (mcontext as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.details_fragment,favouriteFragment)
                    .commit()
            }
            else if(position ==2){
                val settingFragment= SettingsFragment()
                (mcontext as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.details_fragment,settingFragment)
                    .commit()
            }
            else{
                val aboutUsFragment= AboutUsFragment()
                (mcontext as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.details_fragment,aboutUsFragment)
                    .commit()
            }
            MainActivity.statified.drawerLayout?.closeDrawers()
        })
    }

    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var icon_GET: ImageView?= null
        var text_Get: TextView?=null
        var contentHolder: RelativeLayout?=null
        init {
            icon_GET=itemView?.findViewById(R.id.icon_navdrawer)
            text_Get=itemView?.findViewById(R.id.text_navdrawer)
            contentHolder=itemView?.findViewById(R.id.navdrawer_item_content_holder)
        }
    }
}