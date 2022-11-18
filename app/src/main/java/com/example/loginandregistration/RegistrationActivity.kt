package com.example.loginandregistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginandregistration.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    companion object{
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""
        binding.rusernameInput.setText(username)
        binding.rpasswordInput.setText(password)

        binding.registerButton.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.putExtra(EXTRA_USERNAME, binding.rusernameInput.text.toString())
            loginIntent.putExtra(EXTRA_PASSWORD, binding.rpasswordInput.text.toString())
            startActivity(loginIntent)
        }
    }
}