package com.example.tenantmanagementsystemgroupa.ui.tenants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystemgroupa.databinding.FragmentAddTenantBinding
import com.example.tenantmanagementsystemgroupa.ui.viewmodel.TenantViewModel

// Navigation Practical 3, Part B - the Add Tenant form, now a Fragment inside
// the NavHost instead of a whole Activity. Same fields, same validation.
//
// SAVE does three things: ask the ViewModel to validate and store the tenant,
// then navigate to View Tenants carrying the Tenant through a Safe Args
// argument. The ViewModel owns the storage; this Fragment only handles UI.
class AddTenantFragment : Fragment() {

    private var _binding: FragmentAddTenantBinding? = null
    private val binding get() = _binding!!

    // The SAME ViewModel the Dashboard is watching, because it is scoped to
    // MainActivity. Saving here updates the Dashboard totals automatically.
    private val viewModel: TenantViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onCreateView")

        _binding = FragmentAddTenantBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onViewCreated")

        // saveButton  ←  android:id="@+id/saveButton" in fragment_add_tenant.xml
        binding.saveButton.setOnClickListener {

            // UI JOB: collect the input. The ViewModel trims, checks and saves it
            val tenant = viewModel.addTenant(
                binding.tenantNameEditText.text.toString(),
                binding.phoneEditText.text.toString(),
                binding.rentEditText.text.toString()
            )

            // null = rejected. UI JOB: tell the user
            if (tenant == null) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // SAFE ARGS: this function was generated from the <action> in
            // nav_graph.xml. Because View Tenants declares an <argument> of
            // type Tenant, the function REQUIRES a Tenant: it will not compile
            // without one, and it cannot be given a String by mistake.
            // UI JOB: navigate.
            val action = AddTenantFragmentDirections
                .actionAddTenantFragmentToViewTenantsFragment(tenant)

            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "   AddTenantFragment: onDestroy")
    }
}
