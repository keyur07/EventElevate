package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderProfileModel {

@SerializedName("status_code")
@Expose
private Integer statusCode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("service_data")
@Expose
private List<ServiceDatum> serviceData;
@SerializedName("user_data")
@Expose
private List<UserDatum> userData;
@SerializedName("images")
@Expose
private List<Image> images;

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

public List<ServiceDatum> getServiceData() {
return serviceData;
}

public void setServiceData(List<ServiceDatum> serviceData) {
this.serviceData = serviceData;
}

public List<UserDatum> getUserData() {
return userData;
}

public void setUserData(List<UserDatum> userData) {
this.userData = userData;
}

public List<Image> getImages() {
return images;
}

public void setImages(List<Image> images) {
this.images = images;
}

    public class ServiceDatum {

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
        @SerializedName("userid")
        @Expose
        private Integer userid;

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

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

    }

    public class UserDatum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }
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
            private Object photographerId;
            @SerializedName("food_id")
            @Expose
            private Integer foodId;
            @SerializedName("music_id")
            @Expose
            private Object musicId;
            @SerializedName("package_id")
            @Expose
            private Integer packageId;

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

            public Object getPhotographerId() {
                return photographerId;
            }

            public void setPhotographerId(Object photographerId) {
                this.photographerId = photographerId;
            }

            public Integer getFoodId() {
                return foodId;
            }

            public void setFoodId(Integer foodId) {
                this.foodId = foodId;
            }

            public Object getMusicId() {
                return musicId;
            }

            public void setMusicId(Object musicId) {
                this.musicId = musicId;
            }

            public Integer getPackageId() {
                return packageId;
            }

            public void setPackageId(Integer packageId) {
                this.packageId = packageId;
            }

        }


    }