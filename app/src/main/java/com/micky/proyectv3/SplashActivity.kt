package com.micky.proyectv3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        // Verificar si el usuario ya está autenticado
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            // Si el usuario está autenticado, redirigir a MainActivity
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000) // 2 segundos de delay
        } else {
            // Si no está autenticado, redirigir a LoginActivity
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 2000) // 2 segundos de delay
        }
    }
}