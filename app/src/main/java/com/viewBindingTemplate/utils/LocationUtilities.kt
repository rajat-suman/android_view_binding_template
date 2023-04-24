package com.viewBindingTemplate.utils

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.activity.result.ActivityResultLauncher
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AddressComponent
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.viewBindingTemplate.BuildConfig
import java.io.IOException
import java.util.*

fun Context.getAddress(latitude: Double, longitude: Double): Address? {
    return try {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            addresses[0]
        } else null
    } catch (ignored: IOException) {
        ignored.printStackTrace()
        null
    }
}

fun fetchAddress(
    addressList: List<AddressComponent>,
    returnData: (city: String?, state: String?, country: String?, postalCode: String?, region: String?) -> Unit,
) {
    var (city, state, country, postalCode, region) = listOf<String?>(null, null, null, null, null)
    addressList.forEach {
        when {
            it.types.contains("postal_code") -> {
                postalCode = it.name //postalCode
            }
            it.types.contains("administrative_area_level_2") || it.types.contains("locality") -> {
                city = it.name //city
            }
            it.types.contains("administrative_area_level_3") -> {
                state = it.name //state
            }
            it.types.contains("administrative_area_level_1") -> {
                region = it.name //region
            }
            it.types.contains("country") -> {
                country = it.name //country
            }
        }
    }
    returnData(city, state, country, postalCode, region)
}

fun Context.locationSearch(requestCode: ActivityResultLauncher<Intent>) {
    if (!Places.isInitialized()) {
        Places.initialize(this, BuildConfig.API_KEY)
    }
    val fields: List<Place.Field> = listOf(
        Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS
    )
    val intentPlacePicker =
        Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)

    requestCode.launch(intentPlacePicker)
}