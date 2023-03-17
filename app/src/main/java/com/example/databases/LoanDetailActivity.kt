package com.example.databases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.databases.databinding.ActivityLoanDetailBinding

class LoanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoanDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}