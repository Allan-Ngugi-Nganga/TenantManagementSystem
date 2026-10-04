package com.example.tenantmanagementsystemgroupa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityMainBinding

// Tenant Management System - Lesson 6 Practical (View Binding + Data Binding)
// BBT 3.2 Mobile Application Development - Strathmore
// Name: Allan Ng'ang'a
// Reg:  191250
//
// Steps 1-21 of the practical. View Binding gives us safe access to the views;
// Data Binding lets the layout read a Tenant object directly. MainActivity no
// longer formats the text - it just builds a Tenant and hands it over.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

            // Try it yourself 4: clear the inputs after a successful save.
            binding.tenantNameEditText.text.clear()
            binding.phoneEditText.text.clear()
            binding.rentEditText.text.clear()
        }
    }
}
