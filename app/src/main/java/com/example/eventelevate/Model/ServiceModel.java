package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServiceModel {



    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("servicetype")
    @Expose
    private ArrayList<Servicetype> servicetype;




    public ServiceModel(Integer statusCode, String message, ArrayList<Servicetype> servicetype) {
        this.statusCode = statusCode;
        this.message = message;
        this.servicetype = servicetype;
    }

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

    public ArrayList<Servicetype> getServicetype() {
        return servicetype;
    }

    public void setServicetype(ArrayList<Servicetype> servicetype) {
        this.servicetype = servicetype;
    }

    public class Servicetype {
        @SerializedName("serviceId")
        @Expose
        private Integer serviceId;
        @SerializedName("serviceName")
        @Expose
        private String serviceName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("image")
        @Expose
        private String image;


        public Servicetype(Integer serviceId, String serviceName, String description, Integer status, String image) {
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.description = description;
            this.status = status;
            this.image = image;
        }


        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}