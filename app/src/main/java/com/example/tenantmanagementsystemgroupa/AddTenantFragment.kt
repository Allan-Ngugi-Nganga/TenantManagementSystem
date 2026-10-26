package com.example.tenantmanagementsystemgroupa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystemgroupa.databinding.FragmentAddTenantBinding

// Navigation Practical 3, Part B - the Add Tenant form, now a Fragment inside
// the NavHost instead of a whole Activity. Same fields, same validation.
//
// SAVE does three things: validate, add the Tenant to the shared
// TenantRepository, then navigate to View Tenants carrying the Tenant through
// a Safe Args argument.
class AddTenantFragment : Fragment() {

    private var _binding: FragmentAddTenantBinding? = null
    private val binding get() = _binding!!

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

            val name = binding.tenantNameEditText.text.toString().trim()

            val phone = binding.phoneEditText.text.toString().trim()

            val rent = binding.rentEditText.text.toString().trim()

            // None of the three fields may be empty: show the error on the
            // first empty one and stop.
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

            // Try it yourself (2): a phone number is ten digits in Kenya.
            // Count only the digits so 0712 345 678 still passes.
            if (phone.count { it.isDigit() } < 10) {
                binding.phoneEditText.error = "Enter a valid phone number"
                return@setOnClickListener
            }

            val tenant = Tenant(name, phone, rent)

            // Add to the shared list. The Dashboard and View Tenants read it.
            TenantRepository.addTenant(tenant)

            // A Fragment is not a Context, so ask for the one it is attached to.
            Toast.makeText(requireContext(), "${tenant.name} saved", Toast.LENGTH_SHORT).show()

            // SAFE ARGS: this function was generated from the <action> in
            // nav_graph.xml. Because View Tenants declares a <argument> of type
            // Tenant, the function REQUIRES a Tenant: it will not compile
            // without one, and it cannot be given a String by mistake.
            val action = AddTenantFragmentDirections
                .actionAddTenantFragmentToViewTenantsFragment(tenant)

            // Ask the NavController to follow that action.
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
