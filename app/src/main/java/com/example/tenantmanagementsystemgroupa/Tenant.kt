package com.example.tenantmanagementsystemgroupa

// Serializable is built into Java/Kotlin: no plugin needed.
import java.io.Serializable

// A data class whose main job is to hold one tenant's data. Kotlin generates
// a readable toString() automatically. summary() describes how a tenant is
// shown as text, and the XML calls it through @{tenant.summary()}.
//
// ": Serializable" means "a Tenant can be packed up and sent to another
// screen". Safe Args needs this in Practical 3.
data class Tenant(
    val name: String,
    val phone: String,
    val rent: String
) : Serializable {
    fun summary(): String {
        return "Tenant: $name\nPhone: $phone\nRent paid: KSh $rent"
    }
}
