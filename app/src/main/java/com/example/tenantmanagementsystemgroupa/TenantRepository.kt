package com.example.tenantmanagementsystemgroupa

// An object is a class with exactly one instance, shared by the whole app.
// The Dashboard, Add Tenant and View Tenants screens all see this same list.
object TenantRepository {

    val tenants = mutableListOf<Tenant>()

    fun addTenant(tenant: Tenant) {
        tenants.add(tenant)
    }

    // Converts each tenant's rent from text to a number. If the text isn't a
    // valid number, it uses 0.0 instead of crashing.
    fun totalRent(): Double {
        return tenants.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
    }
}
