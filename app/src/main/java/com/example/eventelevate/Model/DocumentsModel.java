package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentsModel {

@SerializedName("status_code")
@Expose
private Integer statusCode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("document_status")
@Expose
private List<DocumentStatus> documentStatus;

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

public List<DocumentStatus> getDocumentStatus() {
return documentStatus;
}

public void setDocumentStatus(List<DocumentStatus> documentStatus) {
this.documentStatus = documentStatus;
}
    public class DocumentStatus {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("photo")
        @Expose
        private String photo;
        @SerializedName("photo_status")
        @Expose
        private Integer photoStatus;
        @SerializedName("photoid")
        @Expose
        private String photoid;
        @SerializedName("photoid_status")
        @Expose
        private Integer photoidStatus;
        @SerializedName("comments")
        @Expose
        private String comments;

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

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Integer getPhotoStatus() {
            return photoStatus;
        }

        public void setPhotoStatus(Integer photoStatus) {
            this.photoStatus = photoStatus;
        }

        public String getPhotoid() {
            return photoid;
        }

        public void setPhotoid(String photoid) {
            this.photoid = photoid;
        }

        public Integer getPhotoidStatus() {
            return photoidStatus;
        }

        public void setPhotoidStatus(Integer photoidStatus) {
            this.photoidStatus = photoidStatus;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

    }


}