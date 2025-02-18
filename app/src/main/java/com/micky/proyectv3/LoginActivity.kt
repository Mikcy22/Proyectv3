package com.micky.proyectv3

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Asegúrate de que el XML es el correcto

        auth = FirebaseAuth.getInstance()

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val forgotPasswordButton = findViewById<Button>(R.id.forgotPasswordLink)

        // Iniciar sesión
        loginButton.setOnClickListener {
            val email = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        // Abrir diálogo de registro
        registerButton.setOnClickListener {
            val dialog = DialogoCrearCuenta()
            dialog.show(supportFragmentManager, "DialogoCrearCuenta")
        }

        // Restaurar contraseña
        forgotPasswordButton.setOnClickListener {
            val email = usernameInput.text.toString().trim()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
            } else {
                resetPassword(email)
            }
        }
    }

    // Función para iniciar sesión
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java) // Redirige a otra pantalla
                    startActivity(intent)
                    finish() // Cierra la actividad actual
                } else {
                    Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Función para restaurar la contraseña
    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
