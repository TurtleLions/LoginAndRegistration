package com.example.databases

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault


class LoanAdapter(var loanList: MutableList<Loan>):RecyclerView.Adapter<LoanAdapter.ViewHolder>() {
    companion object{
        val TAG = "Loan Adapter"
        val EXTRA_LOAN = "Loan"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAmountOwedAndName: TextView
        val textViewDescription: TextView
        val textViewDateLoaned: TextView
        val textViewDateRepayed: TextView
        val textViewOriginalAmount: TextView
        val layout: ConstraintLayout

        init {
            textViewAmountOwedAndName = view.findViewById(R.id.item_textView_amountOwedAndName)
            textViewDescription = view.findViewById(R.id.item_textView_description)
            textViewDateLoaned = view.findViewById(R.id.item_textView_dateLoaned)
            textViewDateRepayed = view.findViewById(R.id.item_textView_dateRepaid)
            textViewOriginalAmount = view.findViewById(R.id.item_textView_originalAmount)
            layout = view.findViewById(R.id.ConstraintLayout)
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_loan_detail, viewGroup, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        val context = viewHolder.textViewDescription.context
        val currentLoanData = loanList[position]
        viewHolder.textViewAmountOwedAndName.text = "$${currentLoanData.balanceRemaining()} from ${currentLoanData.loanee}"
        viewHolder.textViewDescription.text = currentLoanData.description
        viewHolder.textViewDateLoaned.text = "Date Loaned: ${currentLoanData.dateLoaned.toString()}"
        if(currentLoanData.repaid){
            viewHolder.textViewDateRepayed.text = "Date Repaid: ${currentLoanData.dateRepaid.toString()}"
        }
        else{
            viewHolder.textViewDateRepayed.text = "Not Repaid"
        }
        viewHolder.textViewOriginalAmount.text = "Total: $${currentLoanData.owed}"
        viewHolder.layout.setOnClickListener {
            val detailActivity = Intent(it.context, LoanDetailActivity::class.java).apply {
                putExtra(EXTRA_LOAN, currentLoanData)
            }
            it.context.startActivity(detailActivity)
        }
        viewHolder.layout.isLongClickable=true
        viewHolder.layout.setOnLongClickListener {
            val popMenu = PopupMenu(context, viewHolder.textViewAmountOwedAndName)
            popMenu.inflate(R.menu.item_loan_menu)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_delete ->{
                        deleteFromBackendless(position)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }
    }
    private fun deleteFromBackendless(position: Int) {
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete ${loanList[position]}")
        Backendless.Data.of(Loan::class.java).remove(loanList[position],
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    // Contact has been deleted. The response is the
                    // time in milliseconds when the object was deleted
                    loanList.removeAt(position)
                    notifyDataSetChanged()
                }

                override fun handleFault(fault: BackendlessFault) {
                    // an error has occurred, the error code can be
                    // retrieved with fault.getCode()
                }
            })
    }

    override fun getItemCount() = loanList.size
}