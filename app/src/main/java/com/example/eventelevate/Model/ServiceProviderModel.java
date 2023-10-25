package com.example.eventelevate.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceProviderModel {

@SerializedName("status_code")
@Expose
private Integer statusCode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("servicetype")
@Expose
private List<Servicetype> servicetype;

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

public List<Servicetype> getServicetype() {
return servicetype;
}

public void setServicetype(List<Servicetype> servicetype) {
this.servicetype = servicetype;
}


public class Servicetype {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("paymenttype")
    @Expose
    private String paymenttype;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("images")
    @Expose
    private List<Image> images;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }



    public class Image {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("photographer_id")
        @Expose
        private Integer photographerId;
        @SerializedName("food_id")
        @Expose
        private Object foodId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public Integer getPhotographerId() {
            return photographerId;
        }

        public void setPhotographerId(Integer photographerId) {
            this.photographerId = photographerId;
        }

        public Object getFoodId() {
            return foodId;
        }

        public void setFoodId(Object foodId) {
            this.foodId = foodId;
        }

    }

}

}