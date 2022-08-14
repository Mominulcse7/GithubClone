package com.mominulcse7.githubclone.features.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mominulcse7.githubclone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}