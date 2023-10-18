package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutUsModel{

@SerializedName("status_code")
@Expose
private Integer statusCode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("about")
@Expose
private List<About> about;

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

public List<About> getAbout() {
return about;
}

public void setAbout(List<About> about) {
this.about = about;
}

    public class About {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("profession")
        @Expose
        private String profession;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}