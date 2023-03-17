package com.example.databases

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.databases.RegistrationUtil.RegistrationUtil.validateEmail
import com.example.databases.RegistrationUtil.RegistrationUtil.validateName
import com.example.databases.RegistrationUtil.RegistrationUtil.validatePassword
import com.example.databases.RegistrationUtil.RegistrationUtil.validateUsername
import com.example.databases.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    companion object{
        val TAG = "RegistrationActivity"
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val u = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val p = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""
        binding.rusernameInput.setText(u)
        binding.rpasswordInput.setText(p)

        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val username = binding.rusernameInput.text.toString()
            val password = binding.rpasswordInput.text.toString()
            val cpassword = binding.rcpasswordInput.text.toString()
            val email = binding.emailInput.text.toString()

            if(validateUsername(username)&&validatePassword(password, cpassword)&& validateName(name)&& validateEmail(email)){
                val user = BackendlessUser()
                user.setProperty("name", binding.nameInput.text.toString())
                user.setProperty("email", binding.emailInput.text.toString())
                user.setProperty("username", binding.rusernameInput.text.toString())
                user.setProperty("password", binding.rpasswordInput.text.toString())

                Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(registeredUser: BackendlessUser?) {
                        // user has been registered and now can login
                        Log.d(TAG, "handleResponse: ${user.getProperty("username")} has been registered")
                        val resultIntent = Intent().apply {
                            putExtra(EXTRA_USERNAME, binding.rusernameInput.text.toString())
                            putExtra(EXTRA_PASSWORD, binding.rpasswordInput.text.toString())
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Log.d(TAG, "handleFault: ${fault.message}")
                    }
                })
            }
            else{
                Toast.makeText(this, "Please check all fields are inputted properly", Toast.LENGTH_SHORT).show()
            }




        }
    }
}