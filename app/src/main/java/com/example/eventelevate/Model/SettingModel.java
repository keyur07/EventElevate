package com.example.eventelevate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingModel {
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("setting")
    @Expose
    private Setting setting;

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

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public class Setting {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("app_name")
        @Expose
        private String appName;
        @SerializedName("app_logo")
        @Expose
        private String appLogo;
        @SerializedName("update")
        @Expose
        private Integer update;
        @SerializedName("maintenance")
        @Expose
        private Integer maintenance;
        @SerializedName("policy")
        @Expose
        private String policy;
        @SerializedName("version")
        @Expose
        private String version;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppLogo() {
            return appLogo;
        }

        public void setAppLogo(String appLogo) {
            this.appLogo = appLogo;
        }

        public Integer getUpdate() {
            return update;
        }

        public void setUpdate(Integer update) {
            this.update = update;
        }

        public Integer getMaintenance() {
            return maintenance;
        }

        public void setMaintenance(Integer maintenance) {
            this.maintenance = maintenance;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }
}