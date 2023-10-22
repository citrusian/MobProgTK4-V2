package com.example.mobprogtk4.Maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.mobprogtk4.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Preview(showBackground = true)
@Composable
fun MapsJetpack(
    onProfile: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header and Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Maps",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                )
                Button(
                    onClick = onProfile,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Profile")
                }
            }

            // Maps

            val context = LocalContext.current
            val apiKey = remember { context.getString(R.string.gmapsAPIkey) }
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val userLocation = remember { mutableStateOf<LatLng?>(null) }
            RequestLocationUpdates(fusedLocationClient, context, userLocation)
            GoogleMapsView(userLocation.value ?: LatLng(1.35, 103.87))
        }
    }
}

@Composable
fun GoogleMapsView(userLocation: LatLng) {
    Log.d("GoogleMapsView Enter", "$userLocation")
    val updatedUserLocation by rememberUpdatedState(userLocation)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(updatedUserLocation, 18f)
    }

    // Use LaunchedEffect to update the camera position when userLocation changes
    LaunchedEffect(userLocation) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(updatedUserLocation, 18f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        if (updatedUserLocation != null) {
            Marker(
                state = MarkerState(position = updatedUserLocation),
                title = "Your Location",
                draggable = false,
            )
        }
        Log.d("GoogleMapsView Exit", "$updatedUserLocation")
    }
}


@Composable
private fun RequestLocationUpdates(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    userLocation: MutableState<LatLng?>
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        // Permissions are granted. You can request location updates here.
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(100)
            .setFastestInterval(50)
            .setMaxWaitTime(100)

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val lastLocation = locationResult.lastLocation
                    val newUserLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
                    userLocation.value = newUserLocation
                }
            },
            Looper.getMainLooper()
        )
    }
    else{
        LocationPermissionHandler{}
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(onLocationPermissionGranted: () -> Unit) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(permissionState) {
        if (permissionState.status.isGranted) {
            onLocationPermissionGranted()
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}


