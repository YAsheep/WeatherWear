package com.yasheep.weatherwear.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.yasheep.weatherwear.R
import com.yasheep.weatherwear.logic.model.Place
import com.yasheep.weatherwear.WeatherActivity

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val placeName = view.findViewById<TextView>(R.id.placeName)
        private val placeAddress = view.findViewById<TextView>(R.id.placeAddress)
        private lateinit var place: Place

        fun showData(place: Place){
            this.place = place
            placeName.text = place.name
            placeAddress.text = place.address
        }

        init {
            // 写在这里并不影响取数据,得益于监听器的作用
            itemView.setOnClickListener {
                val intent = Intent(fragment.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                // 关闭当前activity？
                fragment.requireActivity().finish()
            }
        }
    }
    //This method calls onCreateViewHolder to create a new ViewHolder and
    // initializes some private fields to be used by RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 利用layoutInflater生成了新的view对象，布局对应的就是项布局
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_tiem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.showData(place)
    }
}