package com.rizki.submissionakhir

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rizki.submissionakhir.view.setting.SettingAppPreferences
import com.rizki.submissionakhir.view.setting.SettingModelFactory
import com.rizki.submissionakhir.view.setting.SettingViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        val pref = SettingAppPreferences.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }, SPLASH_DELAY)
            })
    }

    companion object {
        const val SPLASH_DELAY = 3000L
    }

}