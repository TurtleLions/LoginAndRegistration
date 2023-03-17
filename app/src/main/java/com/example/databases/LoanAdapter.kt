package com.example.databases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView

class LoanAdapter(var loanList: MutableList<Loan>):RecyclerView.Adapter<LoanAdapter.ViewHolder>() {
    companion object{
        val TAG = "Loan Adapter"
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
            textViewDateRepayed = view.findViewById(R.id.item_textView_dateRepayed)
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
        if(currentLoanData.isRepaid){
            viewHolder.textViewDateRepayed.text = "Date Repaid: ${currentLoanData.dateRepaid.toString()}"
        }
        else{
            viewHolder.textViewDateRepayed.text = "Not Repaid"
        }
        viewHolder.textViewOriginalAmount.text = "Total: $${currentLoanData.owed}"
    }
    override fun getItemCount() = loanList.size
}