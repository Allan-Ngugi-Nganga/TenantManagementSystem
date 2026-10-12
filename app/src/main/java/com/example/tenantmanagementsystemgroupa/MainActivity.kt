package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityMainBinding

// Tenant Management System - Lesson 6 Practical (View Binding + Data Binding)
// BBT 3.2 Mobile Application Development - Strathmore
// Name: Allan Ng'ang'a
// Reg:  191250
//
// Lesson 6 Navigation Practical 1 - Part C (Steps 11-12):
// The screen keeps its View Binding / Data Binding behaviour and adds a
// CALL TENANT button. We remember the last saved tenant so the button knows
// whose number to hand to the dialer via an implicit Intent (ACTION_DIAL).
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Remembers the most recently saved tenant (null until the first SAVE).
    private var lastTenant: Tenant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.saveButton.setOnClickListener {

            val name = binding.tenantNameEditText.text.toString()

            val phone = binding.phoneEditText.text.toString()

            val rent = binding.rentEditText.text.toString()

            // Try it yourself 1: reject an empty name.
            if (name.isBlank()) {
                binding.tenantNameEditText.error = "Tenant name is required"
                return@setOnClickListener
            }

            val tenant = Tenant(name, phone, rent)

            binding.tenant = tenant

            // Remember it for the CALL TENANT button.
            lastTenant = tenant

            // Try it yourself 4: clear the inputs after a successful save.
            binding.tenantNameEditText.text.clear()
            binding.phoneEditText.text.clear()
            binding.rentEditText.text.clear()
        }

        binding.callButton.setOnClickListener {

            val tenant = lastTenant

            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Implicit Intent: ask the phone's dialer to open with this number.
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${tenant.phone}"))

            startActivity(intent)
        }
    }
}
