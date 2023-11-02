package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel {

@SerializedName("status_code")
@Expose
private Integer statusCode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("loactions")
@Expose
private List<Loaction> loactions;

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

public List<Loaction> getLoactions() {
return loactions;
}

public void setLoactions(List<Loaction> loactions) {
this.loactions = loactions;
}

    public class Loaction {

        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("next")
        @Expose
        private String next;
        @SerializedName("previous")
        @Expose
        private Object previous;
        @SerializedName("results")
        @Expose
        private List<Result> results;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public Object getPrevious() {
            return previous;
        }

        public void setPrevious(Object previous) {
            this.previous = previous;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public class Result {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("code")
            @Expose
            private String code;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("slug")
            @Expose
            private String slug;
            @SerializedName("slug_language")
            @Expose
            private String slugLanguage;
            @SerializedName("country")
            @Expose
            private String country;
            @SerializedName("latitude")
            @Expose
            private Double latitude;
            @SerializedName("longitude")
            @Expose
            private Double longitude;
            @SerializedName("radius")
            @Expose
            private Integer radius;
            @SerializedName("photo")
            @Expose
            private String photo;
            @SerializedName("telephone")
            @Expose
            private String telephone;
            @SerializedName("currency")
            @Expose
            private String currency;
            @SerializedName("locale")
            @Expose
            private String locale;
            @SerializedName("loyalty_type")
            @Expose
            private String loyaltyType;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getSlugLanguage() {
                return slugLanguage;
            }

            public void setSlugLanguage(String slugLanguage) {
                this.slugLanguage = slugLanguage;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public Double getLatitude() {
                return latitude;
            }

            public void setLatitude(Double latitude) {
                this.latitude = latitude;
            }

            public Double getLongitude() {
                return longitude;
            }

            public void setLongitude(Double longitude) {
                this.longitude = longitude;
            }

            public Integer getRadius() {
                return radius;
            }

            public void setRadius(Integer radius) {
                this.radius = radius;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public String getLoyaltyType() {
                return loyaltyType;
            }

            public void setLoyaltyType(String loyaltyType) {
                this.loyaltyType = loyaltyType;
            }

        }

    }
}