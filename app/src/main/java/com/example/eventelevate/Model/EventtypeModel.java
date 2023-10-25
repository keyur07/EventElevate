package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventtypeModel {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("EventTypes")
    @Expose
    private ArrayList<EventType> eventTypes;

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

    public ArrayList<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(ArrayList<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public class EventType {

        @SerializedName("eventTypeId")
        @Expose
        private Integer eventTypeId;
        @SerializedName("eventTypeName")
        @Expose
        private String eventTypeName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("image")
        @Expose
        private String image;


        public EventType(Integer eventTypeId, String eventTypeName, String description, Integer status, String image) {
            super();
            this.eventTypeId = eventTypeId;
            this.eventTypeName = eventTypeName;
            this.description = description;
            this.status = status;
            this.image = image;
        }

        public Integer getEventTypeId() {
            return eventTypeId;
        }

        public void setEventTypeId(Integer eventTypeId) {
            this.eventTypeId = eventTypeId;
        }

        public String getEventTypeName() {
            return eventTypeName;
        }

        public void setEventTypeName(String eventTypeName) {
            this.eventTypeName = eventTypeName;
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