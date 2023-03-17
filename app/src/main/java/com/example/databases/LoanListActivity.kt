package com.example.databases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databases.databinding.ActivityLoanListBinding

class LoanListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoanListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoanListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}