package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MissingAssetResponse implements Serializable{


    @SerializedName("maintenance")
    @Expose
    private List<Maintenance> maintenance = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Maintenance> getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(List<Maintenance> maintenance) {
        this.maintenance = maintenance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public class Maintenance implements Serializable{

        @SerializedName("assets_id")
        @Expose
        private String assetsId;
        @SerializedName("remark")
        @Expose
        private String remark;
        @SerializedName("assets_category")
        @Expose
        private String assetsCategory;
        @SerializedName("assets_brand_name")
        @Expose
        private String assetsBrandName;
        @SerializedName("assets_name")
        @Expose
        private String assetsName;
        @SerializedName("assets_location")
        @Expose
        private String assetsLocation;
        @SerializedName("sr_no")
        @Expose
        private String srNo;
        @SerializedName("item_price")
        @Expose
        private String itemPrice;
        @SerializedName("assets_file")
        @Expose
        private String assetsFile;
        @SerializedName("maintenance_type")
        @Expose
        private String maintenanceType;
        @SerializedName("maintenance_schedule_date")
        @Expose
        private String maintenanceScheduleDate;
        @SerializedName("maintenance_complete_at")
        @Expose
        private String maintenanceCompleteAt;
        @SerializedName("iteam_created_date")
        @Expose
        private String iteamCreatedDate;
        @SerializedName("maintenance_photo")
        @Expose
        private String maintenancePhoto;
        @SerializedName("maintenance_invoice")
        @Expose
        private String maintenance_invoice;
        @SerializedName("maintenance_amount")
        @Expose
        private String maintenanceAmount;

        public String getMaintenanceAmount() {
            return maintenanceAmount;
        }

        public String getMaintenance_invoice() {
            return maintenance_invoice;
        }

        public void setMaintenance_invoice(String maintenance_invoice) {
            this.maintenance_invoice = maintenance_invoice;
        }

        public void setMaintenanceAmount(String maintenanceAmount) {
            this.maintenanceAmount = maintenanceAmount;
        }

        public String getAssetsId() {
            return assetsId;
        }

        public void setAssetsId(String assetsId) {
            this.assetsId = assetsId;
        }

        public String getAssetsCategory() {
            return assetsCategory;
        }

        public void setAssetsCategory(String assetsCategory) {
            this.assetsCategory = assetsCategory;
        }

        public String getMaintenancePhoto() {
            return maintenancePhoto;
        }

        public void setMaintenancePhoto(String maintenancePhoto) {
            this.maintenancePhoto = maintenancePhoto;
        }

        public String getMaintenanceCompleteAt() {
            return maintenanceCompleteAt;
        }

        public void setMaintenanceCompleteAt(String maintenanceCompleteAt) {
            this.maintenanceCompleteAt = maintenanceCompleteAt;
        }

        public String getAssetsBrandName() {
            return assetsBrandName;
        }

        public void setAssetsBrandName(String assetsBrandName) {
            this.assetsBrandName = assetsBrandName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAssetsName() {
            return assetsName;
        }

        public void setAssetsName(String assetsName) {
            this.assetsName = assetsName;
        }

        public String getAssetsLocation() {
            return assetsLocation;
        }

        public void setAssetsLocation(String assetsLocation) {
            this.assetsLocation = assetsLocation;
        }

        public String getSrNo() {
            return srNo;
        }

        public void setSrNo(String srNo) {
            this.srNo = srNo;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getAssetsFile() {
            return assetsFile;
        }

        public void setAssetsFile(String assetsFile) {
            this.assetsFile = assetsFile;
        }

        public String getMaintenanceType() {
            return maintenanceType;
        }

        public void setMaintenanceType(String maintenanceType) {
            this.maintenanceType = maintenanceType;
        }

        public String getMaintenanceScheduleDate() {
            return maintenanceScheduleDate;
        }

        public void setMaintenanceScheduleDate(String maintenanceScheduleDate) {
            this.maintenanceScheduleDate = maintenanceScheduleDate;
        }

        public String getIteamCreatedDate() {
            return iteamCreatedDate;
        }

        public void setIteamCreatedDate(String iteamCreatedDate) {
            this.iteamCreatedDate = iteamCreatedDate;
        }

    }
}
