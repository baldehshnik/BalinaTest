package com.sparkfusion.balina.test.ui.screen.map

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val viewModel: SlideshowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        supportMapFragment?.getMapAsync { googleMap ->
            imagesObserver(googleMap)
        }

        return view
    }

    private fun imagesObserver(googleMap: GoogleMap) {
        viewModel.images.observe(viewLifecycleOwner) { state ->
            googleMap.clear()

            when (state) {
                ImagesLoadingState.Loading -> {}
                ImagesLoadingState.Failure -> {}
                is ImagesLoadingState.Success -> {
                    state.data.forEach { image ->
                        loadMark(image, googleMap)
                    }
                }
            }
        }
    }

    private fun loadMark(image: GetImageModel, googleMap: GoogleMap) {
        val latLng = LatLng(image.lat, image.lng)

        Glide.with(this)
            .asBitmap()
            .load(image.url)
            .override(100, 100)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val markerOptions = MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(resource))
                        .title("Image ID: ${image.id}")

                    googleMap.addMarker(markerOptions)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
