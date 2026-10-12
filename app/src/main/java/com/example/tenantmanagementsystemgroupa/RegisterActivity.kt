package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityRegisterBinding

// Navigation Practical 1 - Part B (Steps 5-8)
// BBT 3.2 Mobile Application Development - Strathmore
// REGISTER validates the three fields, then returns to Login carrying the
// email via putExtra("EMAIL"). FLAG_ACTIVITY_CLEAR_TOP replaces the stale
// Login underneath instead of stacking a second one on top.
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.registerButton.setOnClickListener {

            val fullName = binding.fullNameEditText.text.toString().trim()

            val email = binding.registerEmailEditText.text.toString().trim()

            val password = binding.registerPasswordEditText.text.toString()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Account created. Please log in.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)

            intent.putExtra("EMAIL", email)

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)
        }

        // The "Log in" link: Register was opened from Login, so Login is still
        // underneath. Closing Register simply reveals it again.
        binding.loginLinkTextView.setOnClickListener {
            finish()
        }
    }
}
