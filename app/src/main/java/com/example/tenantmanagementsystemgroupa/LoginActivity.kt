package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityLoginBinding

// Navigation Practical 1 - Part A/B/C
// BBT 3.2 Mobile Application Development - Strathmore
// The app's first screen. LOGIN opens MainActivity with an explicit Intent;
// the Register link opens RegisterActivity; the help link uses an implicit
// Intent (ACTION_VIEW) to ask the browser to show a web page.
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Step 8: Register sends its email here; if present, pre-fill the field.
        val registeredEmail = intent.getStringExtra("EMAIL")

        if (registeredEmail != null) {
            binding.emailEditText.setText(registeredEmail)
        }

        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString().trim()

            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Explicit Intent: open this exact screen.
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

            finish()
        }

        // Step 8: open Register with an explicit Intent.
        binding.registerTextView.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)
        }

        // Steps 9-10: implicit Intent - ask any app that can show a web page.
        binding.helpTextView.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.strathmore.edu"))

            startActivity(intent)
        }
    }
}
