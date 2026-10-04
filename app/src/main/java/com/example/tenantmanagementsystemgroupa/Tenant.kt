package com.example.tenantmanagementsystemgroupa

// A data class whose main job is to hold one tenant's data. Kotlin generates
// a readable toString() automatically. summary() describes how a tenant is
// shown as text, and the XML calls it through @{tenant.summary()}.
data class Tenant(
    val name: String,
    val phone: String,
    val rent: String
) {
    fun summary(): String {
        return "Tenant: $name\nPhone: $phone\nRent paid: KSh $rent"
    }
}
