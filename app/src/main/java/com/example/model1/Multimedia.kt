package com.example.model1

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Multimedia : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_multimedia)

        val playButton: Button = findViewById(R.id.playButton)
        val stopButton: Button = findViewById(R.id.stopButton)
        val videoView: VideoView = findViewById(R.id.videoView)

        // MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.songs)
        playButton.setOnClickListener {
            animateButton(playButton)
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        stopButton.setOnClickListener {
            animateButton(stopButton)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                mediaPlayer.seekTo(0)
            }
        }

        // VideoView
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.sample_video}")
        videoView.setVideoURI(videoUri)

        val mediaController = android.widget.MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnPreparedListener {
            videoView.start()
        }
    }

    private fun animateButton(button: Button) {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(button, scaleX, scaleY)
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
