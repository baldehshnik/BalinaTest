package com.sparkfusion.balina.test.ui.window.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.material.navigation.NavigationView
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.databinding.ActivityMainBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
import com.sparkfusion.balina.test.ui.launcher.registerLocationSettingLauncher
import com.sparkfusion.balina.test.ui.launcher.registerTakePictureLauncher
import com.sparkfusion.balina.test.ui.utils.UriCreator
import com.sparkfusion.balina.test.ui.utils.longSnackbar
import com.sparkfusion.balina.test.ui.utils.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

    private var _appBarConfiguration: AppBarConfiguration? = null
    private val appBarConfiguration get() = checkNotNull(_appBarConfiguration)

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = checkNotNull(_binding) {
        throw ViewBindingIsNullException()
    }

    private var _fusedLocationClient: FusedLocationProviderClient? = null
    private val fusedLocationClient: FusedLocationProviderClient get() = checkNotNull(_fusedLocationClient)

    private var _locationCallback: LocationCallback? = null
    private val locationCallback: LocationCallback get() = checkNotNull(_locationCallback)

    private val viewModel: MainViewModel by viewModels()

    private val takePictureLauncher = registerTakePictureLauncher(::handlePictureTake)

    private val locationSettingsLauncher = registerLocationSettingLauncher(::handleLocationSettings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        initNavigation()
        initLocationClients()
        initFabClickListener()

        handleImageLoading()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        _appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_map, R.id.nav_auth), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun initLocationClients() {
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        _locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                viewModel.handleIntent(
                    MainIntent.ChangeCurrentLat(locationResult.lastLocation?.latitude ?: return)
                )
                viewModel.handleIntent(
                    MainIntent.ChangeCurrentLng(locationResult.lastLocation?.longitude ?: return)
                )
            }
        }
    }

    private fun initLoadingAlertDialog(): AlertDialog {
        return AlertDialog.Builder(this).apply {
            setView(ProgressBar(this@MainActivity).apply {
                isIndeterminate = true
            })
            setCancelable(false)
        }.create()
    }

    private fun initFabClickListener() {
        binding.appBarMain.fab.setOnClickListener {
            if (checkLocationPermission()) checkLocationSettings()
            else requestLocationPermission()
        }
    }

    private fun handleImageLoading() {
        val loadingDialog = initLoadingAlertDialog()
        viewModel.sendImageState.observe(this) {
            when (it) {
                SendImageState.Empty -> {}
                SendImageState.Loading -> loadingDialog.show()

                SendImageState.Error -> {
                    loadingDialog.dismiss()
                    binding.root.shortSnackbar(R.string.error_sending_image)
                }

                SendImageState.Success -> {
                    loadingDialog.dismiss()
                    binding.root.shortSnackbar(R.string.image_sent_successfully)
                }
            }
        }
    }

    private fun handlePictureTake(isSuccess: Boolean) {
        if (isSuccess && imageUri != null) {
            val imageBitmap = contentResolver.openInputStream(imageUri!!)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }

            if (imageBitmap != null) {
                viewModel.handleIntent(MainIntent.SendImage(imageBitmap))
            } else {
                binding.root.longSnackbar(R.string.failed_to_capture_photo)
            }
        } else {
            binding.root.longSnackbar(R.string.failed_to_capture_photo)
        }
    }

    private fun handleLocationSettings(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            startLocationUpdates()
            openCamera()
        } else {
            binding.root.shortSnackbar(R.string.location_not_included)
        }
    }

    private fun openCamera() {
        imageUri = UriCreator(this.applicationContext).createImageUri()
        if (imageUri != null) takePictureLauncher.launch(imageUri)
        else binding.root.shortSnackbar(R.string.unable_to_create_image_uri)
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }

    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
            openCamera()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                val intentSenderRequest =
                    IntentSenderRequest.Builder(exception.resolution.intentSender).build()
                locationSettingsLauncher.launch(intentSenderRequest)
            } else {
                binding.root.shortSnackbar(R.string.error_checking_location_settings)
            }
        }
    }

    private fun startLocationUpdates() {
        if (checkLocationPermission()) {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setMinUpdateIntervalMillis(5000)
                .build()

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroy() {
        stopLocationUpdates()
        _locationCallback = null
        _appBarConfiguration = null
        _fusedLocationClient = null
        super.onDestroy()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 100
    }
}
