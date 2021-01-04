package com.camerax.basic

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.camerax.basic.databinding.ActMainBinding
import com.camerax.basic.utils.Constants
import com.camerax.basic.utils.debug
import com.camerax.basic.utils.getOutputDirectory
import com.camerax.basic.utils.createFile
import com.camerax.basic.utils.toast
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.Executors

class MainAct : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActMainBinding
    private lateinit var previewView: PreviewView
    private lateinit var imagePreview: Preview
    private lateinit var imageAnalysis: ImageAnalysis
    private lateinit var imageCapture: ImageCapture
    private lateinit var outputDirectory: File
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
        initViewFinder()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                previewView.post { startCamera() }
            } else {
                toast("Permissions not granted by the user.")
                finish()
            }
        }
    }

    private fun initBinding() {
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViews() {
        previewView = binding.previewView

        outputDirectory = getOutputDirectory(this)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        binding.cameraCaptureButton.setOnClickListener {
            takePicture()
        }
    }

    private fun initViewFinder() {
        // Request camera permissions
        if (allPermissionsGranted()) {
            previewView.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun startCamera() {
        imagePreview = Preview.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetRotation(previewView.display.rotation)
        }.build()

        imageAnalysis = ImageAnalysis.Builder().apply {
            setImageQueueDepth(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        }.build()
        imageAnalysis.setAnalyzer(executor, LuminosityAnalyzer())

        imageCapture = ImageCapture.Builder().apply {
            setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            setFlashMode(ImageCapture.FLASH_MODE_AUTO)
        }.build()

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            val camera =
                cameraProvider.bindToLifecycle(this, cameraSelector, imagePreview, imageCapture)
            previewView.preferredImplementationMode = PreviewView.ImplementationMode.TEXTURE_VIEW
            imagePreview.setSurfaceProvider(previewView.createSurfaceProvider(camera.cameraInfo))
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePicture() {
        val file = createFile(
            outputDirectory,
            Constants.FILENAME,
            Constants.PHOTO_EXTENSION
        )
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(outputFileOptions, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "Photo capture succeeded: ${file.absolutePath}"
                previewView.post { debug(msg) }
            }

            override fun onError(exception: ImageCaptureException) {
                val msg = "Photo capture failed: ${exception.message}"
                previewView.post { debug(msg) }
            }
        })
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
