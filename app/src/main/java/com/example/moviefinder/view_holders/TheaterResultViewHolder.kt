package com.example.moviefinder.view_holders

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.database.model.Theater
import com.example.moviefinder.databinding.ListItemTheaterResultBinding
import kotlinx.android.synthetic.main.list_item_theater_result.view.*

class TheaterResultViewHolder(private val theaterResultBinding: ListItemTheaterResultBinding) :
    RecyclerView.ViewHolder(theaterResultBinding.root) {

    fun bind(theater: Theater) {
        theaterResultBinding.theater = theater

        itemView.btn_call_theater.setOnClickListener {
            callTheater(theater.phone, itemView.context);
        }

        itemView.btn_navigate_to_theater.setOnClickListener {
            navigateToTheater(theater.lat, theater.lng, itemView.context)
        }

    }

    fun callTheater(phoneNumber: String, context: Context) {

        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        context.startActivity(callIntent)

    }

    fun navigateToTheater(lat: Double, lng: Double, context: Context) {

        val googleMapsIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("google.navigation:q=$lat,$lng")
            setPackage("com.google.android.apps.maps")
        }

        context.startActivity(googleMapsIntent)

    }


}