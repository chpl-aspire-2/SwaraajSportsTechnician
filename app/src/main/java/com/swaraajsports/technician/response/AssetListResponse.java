package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AssetListResponse implements Serializable {


    @SerializedName("assets")
    @Expose
    private List<Asset> assets = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
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

    public class Asset implements Serializable{

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
        @SerializedName("handover_date")
        @Expose
        private String handoverDate;
        @SerializedName("takeover_date")
        @Expose
        private String takeoverDate;
        @SerializedName("iteam_created_date")
        @Expose
        private String iteamCreatedDate;

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

        public String getHandoverDate() {
            return handoverDate;
        }

        public void setHandoverDate(String handoverDate) {
            this.handoverDate = handoverDate;
        }

        public String getTakeoverDate() {
            return takeoverDate;
        }

        public void setTakeoverDate(String takeoverDate) {
            this.takeoverDate = takeoverDate;
        }

        public String getIteamCreatedDate() {
            return iteamCreatedDate;
        }

        public void setIteamCreatedDate(String iteamCreatedDate) {
            this.iteamCreatedDate = iteamCreatedDate;
        }

    }

}
