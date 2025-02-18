package com.micky.proyectv3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.micky.proyectv3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: NavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar FirebaseAuth 🔥
        auth = FirebaseAuth.getInstance()

        navigationView = findViewById(R.id.nav_view) // ID de tu NavigationView en activity_main.xml

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.todos, R.id.deportes, R.id.arcade, R.id.accion
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        // Configurar el listener para los elementos del menú
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_home)
                    true
                }
                R.id.todos -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.todos)
                    true
                }
                R.id.deportes -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.deportes)
                    true
                }
                R.id.arcade -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.arcade)
                    true
                }
                R.id.accion -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.accion)
                    true
                }
                R.id.nav_logout -> {
                    cerrarSesion()
                    true
                }
                else -> false
            }
        }
    }

    // Función para cerrar sesión
    private fun cerrarSesion() {
        auth.signOut() // Cierra sesión en Firebase
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        // Redirigir al Login (LoginActivity)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpia el historial de actividades
        startActivity(intent)
        finish() // Cierra la actividad actual
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
