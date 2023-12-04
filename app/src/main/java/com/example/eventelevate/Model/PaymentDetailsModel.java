package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentDetailsModel {

@SerializedName("status_code")
@Expose
private Integer statusCode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("payment")
@Expose
private List<Payment> payment;

public Integer getStatusCode() {
return statusCode;
}

public void setStatusCode(Integer statusCode) {
this.statusCode = statusCode;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Payment> getPayment() {
return payment;
}

public void setPayment(List<Payment> payment) {
this.payment = payment;
}

    public class Payment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("transaction_id")
        @Expose
        private String transactionId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("end_date")
        @Expose
        private String endDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

    }

}