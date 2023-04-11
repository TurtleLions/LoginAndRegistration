package com.example.databases

import android.os.Bundle
import android.text.InputType
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.databases.databinding.ActivityLoanDetailBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class LoanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoanDetailBinding
    companion object{
        val TAG = "Detail Activity"
        val EXTRA_LOAN = "Loan"
    }
    private lateinit var loan: Loan
    var loanIsEditable = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loan = intent.getParcelableExtra<Loan>(EXTRA_LOAN) ?: Loan()
        if(loan.ownerId.isNullOrBlank()){
            loanIsEditable = false
            toggleEditable()
        }
        else{
            loanIsEditable=true
            toggleEditable()
        }
        binding.checkBoxLoanDetailIsFullyRepaid.isChecked = loan.repaid
        binding.editTextLoanDetailDesc.setText(loan.description)
        binding.editTextLoanDetailInitialLoan.setText(loan.owed.toString())
        binding.editTextLoanDetailBorrower.setText(loan.loanee)
        binding.editTextLoanDetailAmountRepaid.setText(loan.amountRepaid.toString())
        binding.textViewLoanDetailAmountStillOwed.text = String.format("Still Owed %.2f", loan.owed - loan.amountRepaid)
        binding.buttonLoanDetailSave.setOnClickListener {
            val newLoan = loan.copy()
            newLoan.repaid = binding.checkBoxLoanDetailIsFullyRepaid.isChecked
            newLoan.owed=binding.editTextLoanDetailInitialLoan.text.toString().toDouble()
            newLoan.loanee=binding.editTextLoanDetailBorrower.text.toString()
            newLoan.amountRepaid = binding.editTextLoanDetailAmountRepaid.text.toString().toDouble()
            newLoan.description=binding.editTextLoanDetailDesc.text.toString()
            Log.d(TAG, newLoan.toString())
            if(newLoan.ownerId==""){
                newLoan.ownerId=intent.getStringExtra(LoanListActivity.EXTRA_USERID)!!
                newLoan.objectId=null
            }
            Backendless.Data.of(Loan::class.java)
                .save(newLoan, object : AsyncCallback<Loan?> {
                    override fun handleResponse(response: Loan?){
                        Toast.makeText(this@LoanDetailActivity, "Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Log.d(TAG,fault.code)
                    }
                })

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_loan_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item_loan_detail_edit -> {
                toggleEditable()
                true
            }
            R.id.menu_item_loan_detail_delete -> {
                deleteFromBackendless()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteFromBackendless() {
        Backendless.Data.of(Loan::class.java).remove( loan,
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    // Person has been deleted. The response is the
                    // time in milliseconds when the object was deleted
                    Toast.makeText(this@LoanDetailActivity, "${loan.loanee} Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("BirthdayDetail", "handleFault: ${fault.message}")
                }
            })
    }


    private fun toggleEditable() {
        if (loanIsEditable) {
            loanIsEditable = false
            binding.buttonLoanDetailSave.isEnabled = false
            binding.buttonLoanDetailSave.visibility = View.GONE
            binding.checkBoxLoanDetailIsFullyRepaid.isEnabled = false
            binding.editTextLoanDetailBorrower.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailBorrower.isEnabled = false
            binding.editTextLoanDetailDesc.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailDesc.isEnabled = false
            binding.editTextLoanDetailAmountRepaid.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailAmountRepaid.isEnabled = false
            binding.editTextLoanDetailInitialLoan.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailInitialLoan.isEnabled = false
            binding.checkBoxLoanDetailIsFullyRepaid.isClickable = false
        } else {
            loanIsEditable = true
            binding.buttonLoanDetailSave.isEnabled = true
            binding.buttonLoanDetailSave.visibility = View.VISIBLE
            binding.checkBoxLoanDetailIsFullyRepaid.isEnabled = true
            binding.editTextLoanDetailBorrower.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextLoanDetailBorrower.isEnabled = true
            binding.editTextLoanDetailDesc.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextLoanDetailDesc.isEnabled = true
            binding.editTextLoanDetailAmountRepaid.inputType = InputType.TYPE_CLASS_NUMBER
            binding.editTextLoanDetailAmountRepaid.isEnabled = true
            binding.editTextLoanDetailInitialLoan.inputType = InputType.TYPE_CLASS_NUMBER
            binding.editTextLoanDetailInitialLoan.isEnabled = true
            binding.checkBoxLoanDetailIsFullyRepaid.isClickable = true
        }
    }
}
