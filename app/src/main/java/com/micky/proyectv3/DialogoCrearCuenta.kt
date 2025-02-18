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

            when {
                emailText.isEmpty() -> {
                    correo.error = "Ingrese un correo"
                    correo.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(emailText).matches() -> {
                    correo.error = "Correo no válido"
                    correo.requestFocus()
                }
                passText.isEmpty() -> {
                    pass.error = "Ingrese una contraseña"
                    pass.requestFocus()
                }
                passText.length < 6 -> {
                    pass.error = "Mínimo 6 caracteres"
                    pass.requestFocus()
                }
                else -> {
                    crearCuentaFirebase(emailText, passText)
                }
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
                            Toast.makeText(requireContext(), "Cuenta creada. Verifique su correo.", Toast.LENGTH_LONG).show()
                            dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Error al enviar verificación.", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Error en el registro. ¿Usuario ya registrado?", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun restaurarPassword(correo: String) {
        if (correo.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Correo de restauración enviado.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Error al enviar correo de restauración.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()

        // Si necesitas redirigir a otra pantalla tras cerrar sesión:
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        dismiss() // Cierra el diálogo si está abierto
    }


}
