package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AssetDetailResponse implements Serializable {

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
        @SerializedName("item_purchase_date")
        @Expose
        private String itemPurchaseDate;
        @SerializedName("item_price")
        @Expose
        private String itemPrice;
        @SerializedName("assets_file")
        @Expose
        private String assetsFile;
        @SerializedName("iteam_created_date")
        @Expose
        private String iteamCreatedDate;
        @SerializedName("schedule")
        @Expose
        private List<Schedule> schedule = null;

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

        public String getAssetsBrandName() {
            return assetsBrandName;
        }

        public void setAssetsBrandName(String assetsBrandName) {
            this.assetsBrandName = assetsBrandName;
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

        public String getItemPurchaseDate() {
            return itemPurchaseDate;
        }

        public void setItemPurchaseDate(String itemPurchaseDate) {
            this.itemPurchaseDate = itemPurchaseDate;
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

        public String getIteamCreatedDate() {
            return iteamCreatedDate;
        }

        public void setIteamCreatedDate(String iteamCreatedDate) {
            this.iteamCreatedDate = iteamCreatedDate;
        }

        public List<Schedule> getSchedule() {
            return schedule;
        }

        public void setSchedule(List<Schedule> schedule) {
            this.schedule = schedule;
        }

    }

    public class Schedule implements Serializable{

        @SerializedName("assets_category_id")
        @Expose
        private String assetsCategoryId;
        @SerializedName("assets_maintenance_id")
        @Expose
        private String assetsMaintenanceId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("maintenance_date")
        @Expose
        private String maintenanceDate;
        @SerializedName("maintenance_day")
        @Expose
        private String maintenanceDay;
        @SerializedName("vendor_name")
        @Expose
        private String vendorName;
        @SerializedName("vendor_mobile")
        @Expose
        private String vendorMobile;
        @SerializedName("is_completed")
        @Expose
        private Boolean isCompleted;
        @SerializedName("completed_action")
        @Expose
        private Boolean completedAction;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAssetsCategoryId() {
            return assetsCategoryId;
        }

        public void setAssetsCategoryId(String assetsCategoryId) {
            this.assetsCategoryId = assetsCategoryId;
        }

        public String getMaintenanceDate() {
            return maintenanceDate;
        }

        public void setMaintenanceDate(String maintenanceDate) {
            this.maintenanceDate = maintenanceDate;
        }

        public String getAssetsMaintenanceId() {
            return assetsMaintenanceId;
        }

        public Boolean getCompleted() {
            return isCompleted;
        }

        public void setCompleted(Boolean completed) {
            isCompleted = completed;
        }

        public void setAssetsMaintenanceId(String assetsMaintenanceId) {
            this.assetsMaintenanceId = assetsMaintenanceId;
        }

        public String getMaintenanceDay() {
            return maintenanceDay;
        }

        public void setMaintenanceDay(String maintenanceDay) {
            this.maintenanceDay = maintenanceDay;
        }

        public String getVendorName() {
            return vendorName;
        }

        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }

        public String getVendorMobile() {
            return vendorMobile;
        }

        public void setVendorMobile(String vendorMobile) {
            this.vendorMobile = vendorMobile;
        }

        public Boolean getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public Boolean getCompletedAction() {
            return completedAction;
        }

        public void setCompletedAction(Boolean completedAction) {
            this.completedAction = completedAction;
        }

    }
}
