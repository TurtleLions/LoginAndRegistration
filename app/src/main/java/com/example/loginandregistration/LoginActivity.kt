package com.example.loginandregistration

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.loginandregistration.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    companion object{
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
        val username = intent.getStringExtra(RegistrationActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(RegistrationActivity.EXTRA_PASSWORD) ?: ""
        binding.usernameInput.setText(username)
        binding.passwordInput.setText(password)
        binding.signupTextview.setOnClickListener {
            val registrationIntent = Intent(this, RegistrationActivity::class.java).apply {
                putExtra(EXTRA_USERNAME, binding.usernameInput.text.toString())
                putExtra(EXTRA_PASSWORD, binding.passwordInput.text.toString())
            }
            //startActivity(registrationIntent)
            startRegistrationForResult.launch(registrationIntent)
        }
    }
}