package com.example.databases

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.databases.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    companion object{
        val TAG = "LoginActivity"
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }

    val startRegistrationForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            binding.usernameInput.setText(intent?.getStringExtra(EXTRA_USERNAME))
            binding.passwordInput.setText(intent?.getStringExtra(EXTRA_PASSWORD))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Backendless.initApp( this, Constants.APP_ID, Constants.API_KEY )
        val username = intent.getStringExtra(RegistrationActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(RegistrationActivity.EXTRA_PASSWORD) ?: ""
        binding.usernameInput.setText(username)
        binding.passwordInput.setText(password)
        binding.signupTextview.setOnClickListener {
            val registrationIntent = Intent(this, RegistrationActivity::class.java).apply {
                putExtra(EXTRA_USERNAME, binding.usernameInput.text.toString())
                putExtra(EXTRA_PASSWORD, binding.passwordInput.text.toString())
            }
            startRegistrationForResult.launch(registrationIntent)
        }
        binding.loginButton.setOnClickListener {
            Backendless.UserService.login(
                binding.usernameInput.text.toString(),
                binding.passwordInput.text.toString(),
                object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(user: BackendlessUser?) {
                        // user has been logged in
                        Log.d(TAG, "handleResponse: ${user?.getProperty("username")} has logged in")
                        retrieveAllData()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // login failed, to get the error code call fault.getCode()
                        Log.d(TAG, "handleFault: ${fault.message}")
                    }
                })

        }
    }

    private fun retrieveAllData() {
        Backendless.Data.of(Loan::class.java).find(object : AsyncCallback<List<Loan?>?> {
            override fun handleResponse(foundLoans: List<Loan?>?) {
                // all Contact instances have been found
                Log.d(TAG, "handleResponse: $foundLoans")
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(TAG, "handleFault ${fault.message}")
            }
        })

    }
}