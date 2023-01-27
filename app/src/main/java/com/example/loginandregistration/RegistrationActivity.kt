package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginandregistration.LoginActivity
import com.example.loginandregistration.databinding.ActivityRegistrationBinding
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validateUsername
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validatePassword
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validateName
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validateEmail

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
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_USERNAME, binding.rusernameInput.text.toString())
                    putExtra(EXTRA_PASSWORD, binding.rpasswordInput.text.toString())
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            else{
                Toast.makeText(this, "Please check all fields are inputted properly", Toast.LENGTH_SHORT).show()
            }




        }
    }
}