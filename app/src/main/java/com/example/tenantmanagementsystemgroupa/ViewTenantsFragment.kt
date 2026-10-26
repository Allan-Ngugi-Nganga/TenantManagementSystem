package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
// Generated from fragment_view_tenants.xml (a Data Binding layout)
import com.example.tenantmanagementsystemgroupa.databinding.FragmentViewTenantsBinding

// Navigation Practical 3, Parts B + E - the list of tenants, plus the tenant
// that was just added.
//
// Two techniques meet here:
//  - Safe Args: args.newTenant is the Tenant Add Tenant sent, or null when we
//    arrived from the Dashboard / ⋮ menu / drawer.
//  - Data Binding: binding.tenant = newTenant feeds <variable name="tenant">,
//    and @{tenant.summary()} renders itself.
class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    // SAFE ARGS: reads the arguments that arrived with the action.
    // ViewTenantsFragmentArgs was generated from the <argument> in nav_graph.xml.
    private val args: ViewTenantsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onCreateView")

        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onViewCreated")

        // The tenant sent from Add Tenant, or null if we came from somewhere else.
        // newTenant  ←  <argument android:name="newTenant"> in nav_graph.xml
        val newTenant = args.newTenant

        // DATA BINDING: hand it to <variable name="tenant"> in the layout.
        // newTenantTextView shows @{tenant.summary()}, so it fills itself in.
        binding.tenant = newTenant

        // No new tenant? Hide the "Just added" label, the box and the Call
        // button together - that is what the <Group> in the layout is for.
        if (newTenant == null) {
            binding.newTenantGroup.visibility = View.GONE
        }

        // CALL TENANT: the implicit Intent from Practical 1, now on this screen.
        binding.callButton.setOnClickListener {

            if (newTenant != null) {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${newTenant.phone}"))

                startActivity(intent)
            }
        }

        // Show every saved tenant as text, one after another
        val tenants = TenantRepository.tenants

        if (tenants.isEmpty()) {
            binding.allTenantsTextView.text = "No tenants yet. Add one from the Dashboard."
        } else {
            binding.allTenantsTextView.text = tenants.joinToString("\n\n") { it.summary() }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "   ViewTenantsFragment: onDestroy")
    }
}
