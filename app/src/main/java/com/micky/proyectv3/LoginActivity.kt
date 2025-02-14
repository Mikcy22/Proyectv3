
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val correo = findViewById<EditText>(R.id.usernameInput)
        val pass = findViewById<EditText>(R.id.passwordInput)
        val boton_login = findViewById<Button>(R.id.loginButton)
        val boton_crear_cuenta = findViewById<Button>(R.id.registerButton)

        boton_login.setOnClickListener {

                if(pass.text.toString()!=""){

                    if(correo.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.text.toString()).matches()){
                            login_firebase(correo.text.toString(), pass.text.toString())
                    }else{
                        Toast.makeText(applicationContext, "Escriba un correo valido", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext, "Escriba la contraseña", Toast.LENGTH_SHORT).show()
                }

        }

        boton_crear_cuenta.setOnClickListener {
            DialogoCrearCuenta().show(supportFragmentManager,null)

        }


    }

    fun login_firebase(correo:String, pass:String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Correo", task.result.user?.email.toString())
                    intent.putExtra("Proveedor", "usuario/contraseña")
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Usuario o la contraseña no registrado", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


