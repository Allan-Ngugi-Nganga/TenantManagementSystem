package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityMainBinding

// Tenant Management System - Lesson 6 Practical (View Binding + Data Binding)
// BBT 3.2 Mobile Application Development - Strathmore
// Name: Allan Ng'ang'a
// Reg:  191250
//
// Lesson 6 Navigation Practical 2 (Parts A + B):
//  - We log every Activity lifecycle callback so we can watch Android
//    create/pause/stop/destroy the screen in Logcat (tag: TENANT_LIFECYCLE).
//  - The Activity still holds the last saved tenant (for CALL/SHARE), and we
//    now save it in onSaveInstanceState() and restore it in onCreate() so it
//    survives a rotation.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Remembers the most recently saved tenant (null until the first SAVE).
    // Lost on rotation unless we save it (see onSaveInstanceState below).
    private var lastTenant: Tenant? = null

    // Try it yourself (4): how many times SAVE was tapped. An Int in the Bundle.
    private var saveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Write a message to Logcat every time onCreate() runs.
        // "TENANT_LIFECYCLE" is a tag: a label we search for in Logcat.
        Log.d("TENANT_LIFECYCLE", "MainActivity: onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // ── RESTORE STATE ─────────────────────────────────────────
        // savedInstanceState is the Bundle we filled in onSaveInstanceState().
        // It is null the FIRST time the Activity opens (nothing saved yet).
        if (savedInstanceState != null) {

            // Read each value back using the SAME keys we saved with.
            val name = savedInstanceState.getString("tenant_name")
            val phone = savedInstanceState.getString("tenant_phone")
            val rent = savedInstanceState.getString("tenant_rent")

            // Only rebuild the tenant if all three were saved.
            if (name != null && phone != null && rent != null) {

                val tenant = Tenant(name, phone, rent)

                lastTenant = tenant          // so CALL/SHARE work again
                binding.tenant = tenant      // so the summary appears again
            }

            // Try it yourself (4): read the SAVE tap counter back (default 0).
            saveCount = savedInstanceState.getInt("save_count", 0)

            if (saveCount > 0) {
                Toast.makeText(this, "SAVE tapped $saveCount time(s)", Toast.LENGTH_SHORT).show()
            }
        }

        // Try it yourself: welcome the user. Login sent the email with putExtra,
        // so read it back here and greet the person who just logged in.
        val email = intent.getStringExtra("EMAIL")

        if (email != null) {
            Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()
        }

        binding.saveButton.setOnClickListener {

            val name = binding.tenantNameEditText.text.toString().trim()

            val phone = binding.phoneEditText.text.toString().trim()

            val rent = binding.rentEditText.text.toString().trim()

            // Try it yourself: validate the tenant. None of the three fields
            // may be empty; show an error on the first empty one.
            if (name.isBlank()) {
                binding.tenantNameEditText.error = "Required"
                return@setOnClickListener
            }

            if (phone.isBlank()) {
                binding.phoneEditText.error = "Required"
                return@setOnClickListener
            }

            if (rent.isBlank()) {
                binding.rentEditText.error = "Required"
                return@setOnClickListener
            }

            val tenant = Tenant(name, phone, rent)

            binding.tenant = tenant

            // Remember it for the CALL and SHARE buttons.
            lastTenant = tenant

            // Try it yourself (4): count this SAVE.
            saveCount++

            // Clear the inputs after a successful save.
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

        // Try it yourself: share a tenant. Hand the saved tenant's details to
        // any app that can send text (WhatsApp, Messages, Gmail).
        binding.shareButton.setOnClickListener {

            val tenant = lastTenant

            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/plain"

            intent.putExtra(Intent.EXTRA_TEXT, tenant.summary())

            startActivity(Intent.createChooser(intent, "Share tenant"))
        }
    }

    // ── SAVE STATE ────────────────────────────────────────────────
    // Android calls this just before destroying the Activity (for example,
    // when the phone rotates). outState is the Bundle that Android keeps.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("TENANT_LIFECYCLE", "MainActivity: onSaveInstanceState")

        // Copy into a val so Kotlin knows it can't change after the check.
        val tenant = lastTenant

        if (tenant != null) {
            // Store each field under its own KEY. onCreate() reads the same keys.
            outState.putString("tenant_name", tenant.name)
            outState.putString("tenant_phone", tenant.phone)
            outState.putString("tenant_rent", tenant.rent)
        }

        // Try it yourself (4): save the SAVE tap counter too.
        outState.putInt("save_count", saveCount)
    }

    // ── LIFECYCLE METHODS ─────────────────────────────────────────
    // Android calls each of these automatically. We only add a log line so we
    // can watch the order in Logcat.

    // The screen is now VISIBLE
    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onStart")
    }

    // The screen is in FRONT and the user can interact with it
    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onResume")
    }

    // The screen is LEAVING the front (e.g. Home was pressed)
    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onPause")
    }

    // The screen is NO LONGER VISIBLE
    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onStop")
    }

    // The user came BACK to a stopped screen
    override fun onRestart() {
        super.onRestart()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onRestart")
    }

    // The screen is being DESTROYED
    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onDestroy")
    }
}
