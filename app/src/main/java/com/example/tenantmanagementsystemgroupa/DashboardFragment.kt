package com.example.tenantmanagementsystemgroupa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
// Generated from fragment_dashboard.xml (View Binding)
import com.example.tenantmanagementsystemgroupa.databinding.FragmentDashboardBinding

// Navigation Practical 3, Parts B + C - the app's home screen.
//
// Two things to notice:
//  1. Navigation is type-safe. Safe Args generated DashboardFragmentDirections
//     from nav_graph.xml, so a wrong action name is a compile error instead of
//     a runtime crash.
//  2. This Fragment logs its whole lifecycle. A Fragment's VIEW can be
//     destroyed while the Fragment object lives on in the back stack, which is
//     exactly what onDestroyView() below and _binding = null protect against.
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // 1. The Fragment OBJECT is created (no view yet)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onCreate")
    }

    // 2. Build the VIEW from fragment_dashboard.xml
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onCreateView")

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root   // hand the view to the NavHost to display
    }

    // 3. The view exists: fill in the metrics and set the click listeners.
    //    This runs AGAIN whenever we come back from another destination, so the
    //    totals are always fresh (the whole point of Part C's step 3).
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onViewCreated")

        // Read from the shared list of tenants
        binding.tenantCountTextView.text = "Tenants\n${TenantRepository.tenants.size}"

        binding.totalRentTextView.text = "Total rent\nKSh %,.0f".format(TenantRepository.totalRent())

        // Dashboard -> Add Tenant
        binding.addTenantButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToAddTenantFragment()
            findNavController().navigate(action)
        }

        // Dashboard -> View Tenants, with no tenant to highlight. Safe Args
        // allows the call with no argument because newTenant defaults to @null.
        binding.viewTenantsButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToViewTenantsFragment()
            findNavController().navigate(action)
        }
    }

    // 4. Visible
    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onStart")
    }

    // 5. In front, user can interact
    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onResume")
    }

    // 6. Leaving the front
    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onPause")
    }

    // 7. No longer visible
    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onStop")
    }

    // 8. The VIEW is destroyed (the Fragment object may still be on the back
    //    stack). Clearing the binding lets the old view be garbage collected.
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onDestroyView")
        _binding = null
    }

    // 9. The Fragment OBJECT is destroyed
    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "   DashboardFragment: onDestroy")
    }
}
