package com.example.databases

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.databases.databinding.ActivityLoanListBinding

class LoanListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoanListBinding
    companion object{
        val EXTRA_USERID = "User Id"
    }
    private lateinit var userId: String
    private lateinit var adapter: LoanAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoanListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent.getStringExtra(LoginActivity.EXTRA_USERID).toString()
        retrieveAllData(userId)
        binding.fabLoanListAdd.setOnClickListener {
            val loanDetailIntent = Intent(this, LoanDetailActivity::class.java).apply{
                putExtra(EXTRA_USERID, userId)
            }
            startActivity(loanDetailIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(this::userId.isInitialized&&this::adapter.isInitialized){
            retrieveAllData(userId)
        }
    }
    override fun onRestart() {
        super.onRestart()
        retrieveAllData(userId)
    }
    private fun retrieveAllData(userId: String) {
        val whereClause = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Loan::class.java).find(queryBuilder, object :
            AsyncCallback<List<Loan?>?> {
            override fun handleResponse(foundLoans: List<Loan?>?) {
                // all Contact instances have been found
                Log.d(LoginActivity.TAG, "handleResponse: $foundLoans")
                adapter = LoanAdapter(foundLoans as MutableList<Loan>)
                binding.recyclerViewLoanList.adapter=adapter
                binding.recyclerViewLoanList.layoutManager = LinearLayoutManager(this@LoanListActivity)
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(LoginActivity.TAG, "handleFault ${fault.message}")
            }
        })

    }
}