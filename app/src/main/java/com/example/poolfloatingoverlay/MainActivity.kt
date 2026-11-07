package com.example.poolfloatingoverlay

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val requestOverlay = 701
    private val requestMediaProjection = 702
    private val requestNotifications = 703
    private val poolPackageName = "com.miniclip.eightballpool"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(36, 36, 36, 36)
            setBackgroundColor(Color.rgb(20, 25, 35))
        }

        val title = TextView(this).apply {
            text = "Real-Time Auto Aim Tool"
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val startOverlay = Button(this).apply {
            text = "១. បើក Floating Auto Aim & Screen Capture"
            setBackgroundColor(Color.rgb(35, 135, 75))
            setTextColor(Color.WHITE)
            setOnClickListener {
                if (Settings.canDrawOverlays(this@MainActivity)) {
                    requestScreenCapture()
                } else {
                    val uri = Uri.parse("package:$packageName")
                    startActivityForResult(
                        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri),
                        requestOverlay
                    )
                }
            }
        }

        val launchGame = Button(this).apply {
            text = "២. ចូលលេង 8 Ball Pool"
            setBackgroundColor(Color.rgb(25, 110, 190))
            setTextColor(Color.WHITE)
            setOnClickListener {
                launch8BallPool()
            }
        }

        root.addView(title)
        root.addView(startOverlay)
        root.addView(launchGame)
        setContentView(root)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) return
        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), requestNotifications)
    }

    private fun requestScreenCapture() {
        val mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), requestMediaProjection)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestMediaProjection && resultCode == RESULT_OK && data != null) {
            val intent = Intent(this, OverlayService::class.java).apply {
                putExtra(OverlayService.EXTRA_RESULT_CODE, resultCode)
                putExtra(OverlayService.EXTRA_DATA_INTENT, data)
            }
            // The capture service is a foreground service on Android 8 and newer.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
            Toast.makeText(this, "Auto Aim System ដំណើរការ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launch8BallPool() {
        val pm = packageManager
        val intent = pm.getLaunchIntentForPackage(poolPackageName)
        if (intent != null) {
            startActivity(intent)
        } else {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$poolPackageName")))
            } catch (e: Exception) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$poolPackageName")
                    )
                )
            }
        }
    }
}
