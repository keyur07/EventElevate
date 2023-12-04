package com.example.eventelevate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eventelevate.Activity.BillingActivity;
import com.example.eventelevate.Model.PaymentDetailsModel;
import com.example.eventelevate.R;

import java.util.List;

public class PaymentsAdapter extends BaseAdapter {
    private BillingActivity billingActivity;
    private final LayoutInflater infalter;
    private List<PaymentDetailsModel.Payment> payment;
    public PaymentsAdapter(BillingActivity billingActivity, List<PaymentDetailsModel.Payment> payment) {
        this.billingActivity=billingActivity;
        this.payment = payment;
        infalter= LayoutInflater.from(billingActivity);
    }

    @Override
    public int getCount() {
        return payment.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = infalter.inflate(R.layout.payments_list_view,parent,false);
        TextView dateofpayment,amount,dates;
        dateofpayment = convertView.findViewById(R.id.dateofpayment);
        amount = convertView.findViewById(R.id.amounts);
        dates = convertView.findViewById(R.id.dates);


        dateofpayment.setText(payment.get(position).getDate());
        amount.setText("$ "+payment.get(position).getAmount());
        dates.setText(payment.get(position).getDate()+" -- "+ payment.get(position).getEndDate());
        return convertView;
    }
}
