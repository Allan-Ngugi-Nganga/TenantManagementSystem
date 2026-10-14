package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onCreate")

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

    // ── Try it yourself (2): watch Register's own lifecycle ───────
    // Open Register, then tap "Already have an account? Log in" (finish()):
    // Register runs onPause -> onStop -> onDestroy and Login is revealed.
    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "RegisterActivity: onDestroy")
    }
}
