/*

package com.micky.proyectokotlin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class DialogoCrearCuenta : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view:View=inflater.inflate(R.layout.fragment_dialogo_crear_cuenta, container, false)

        var correo = view.findViewById<EditText>(R.id.usernameRegisterInput)
        var pass = view.findViewById<EditText>(R.id.passwordRegisterInput)
        var boton_crear_cuenta = view.findViewById<Button>(R.id.registerButtonInput)

        boton_crear_cuenta.setOnClickListener {

            if(pass.text.toString()!=""){

                if(correo.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.text.toString()).matches()){
                    crear_cuenta_firebase(correo.text.toString(), pass.text.toString())
                }else{
                    Toast.makeText(requireContext(), "Escriba un correo valido", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), "Escriba la contraseña", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }


/*

    fun crear_cuenta_firebase(correo:String, pass:String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("Correo", task.result.user?.email.toString())
                    intent.putExtra("Proveedor", "usuario/contraseña")
                    startActivity(intent)
                    Toast.makeText(requireContext(), "Cuenta creada", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Contraseña corta/usuario existente", Toast.LENGTH_SHORT).show()
                }
            }




    }

*/

    fun crear_cuenta_firebase(correo: String, pass: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                        if (verificationTask.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Cuenta creada. Verifica tu correo.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error al enviar el correo de verificación.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("Correo", user?.email.toString())
                    intent.putExtra("Proveedor", "usuario/contraseña")
                    startActivity(intent)

                } else {
                    Toast.makeText(requireContext(), "Contraseña corta/usuario existente", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun iniciarSesion(correo: String, pass: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null && user.isEmailVerified) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Por favor, verifica tu correo antes de iniciar sesión.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Error en el inicio de sesión.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun restaurarPassword(correo: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de restauración enviado.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error al enviar correo de restauración.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
*/

package com.micky.proyectv3

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth

class DialogoCrearCuenta : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialogo_crear_cuenta, container, false)

        val correo = view.findViewById<EditText>(R.id.usernameRegisterInput)
        val pass = view.findViewById<EditText>(R.id.passwordRegisterInput)
        val botonCrearCuenta = view.findViewById<Button>(R.id.registerButtonInput)

        botonCrearCuenta.setOnClickListener {
            val emailText = correo.text.toString().trim()
            val passText = pass.text.toString().trim()

            if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(requireContext(), "Escriba un correo válido", Toast.LENGTH_SHORT).show()
            } else if (passText.isEmpty() || passText.length < 6) {
                Toast.makeText(requireContext(), "Escriba una contraseña de al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                crearCuentaFirebase(emailText, passText)
            }
        }

        return view
    }

    private fun crearCuentaFirebase(correo: String, pass: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                        if (verificationTask.isSuccessful) {
                            Toast.makeText(requireContext(), "Cuenta creada. Verifica tu correo.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al enviar el correo de verificación.", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Error en el registro. ¿Usuario existente?", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
