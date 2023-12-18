package com.swaraajsports.technician.network;

import com.swaraajsports.technician.response.AssetDetailResponse;
import com.swaraajsports.technician.response.AssetListResponse;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.response.ComplainResponse;
import com.swaraajsports.technician.response.CompletedAssetResponse;
import com.swaraajsports.technician.response.DashboardResponse;
import com.swaraajsports.technician.response.EmployeeAttendanceResponse;
import com.swaraajsports.technician.response.LocationResponse;
import com.swaraajsports.technician.response.LoginResponse;
import com.swaraajsports.technician.response.MissingAssetResponse;
import com.swaraajsports.technician.response.NotificationResponce;
import com.swaraajsports.technician.response.SocietyResponse;
import com.swaraajsports.technician.response.VersionResponce;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Single;

public interface RestCall {

    @FormUrlEncoded
    @POST("location_controller_new.php")
    Single<LocationResponse> getCountry(@Field("getCountries") String getCountries, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("location_controller_new.php")
    Single<LocationResponse> getState(@Field("getState") String getState, @Field("country_id") String country_id, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("location_controller_new.php")
    Single<LocationResponse> getCity(@Field("getCity") String getCity, @Field("state_id") String state_id, @Field("language_id") String language_id);


    @Multipart
    @POST("society_list_controller.php")
    Single<SocietyResponse> getSocietyList(@Part("getSociety") RequestBody tag, @Part("society_id") RequestBody society_id, @Part("country_id") RequestBody country, @Part("state_id") RequestBody state, @Part("city_id") RequestBody city, @Part("language_id") RequestBody language_id);


    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> getOtp(@Field("technicianLogin") String technicianLogin, @Field("society_id") String society_id, @Field("country_code") String country_code, @Field("emp_mobile") String user_mobile, @Field("is_firebase") boolean isFirebase, @Field("otp_type") String otp_type, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<LoginResponse> verifyOtp(@Field("technicianVerify") String technicianVerify, @Field("society_id") String society_id, @Field("emp_mobile") String emp_mobile, @Field("country_code") String country_code, @Field("otp") String otp, @Field("language_id") String language_id, @Field("emp_token") String emp_token, @Field("device_mac") String device_mac, @Field("is_firebase") boolean isFirebase, @Field("user_mobile") String user_mobile);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> getLogout(@Field("user_logout") String user_logout, @Field("emp_id") String user_id, @Field("country_code") String country_code, @Field("user_mobile") String user_mobile);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<DashboardResponse> getDashboardData(@Field("getDashboardData") String getDashboardData, @Field("emp_id") String user_id, @Field("society_id") String society_id, @Field("device_mac") String device_mac, @Field("language_id") String language_id, @Field("emp_token") String emp_token, @Field("country_code") String country_code, @Field("device") String device, @Field("version_code") String version_code, @Field("phone_brand") String phone_brand, @Field("phone_model") String phone_model, @Field("user_mobile") String user_mobile);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<ComplainResponse> getEventList(@Field("getComplainNew") String getComplainNew, @Field("society_id") String society_id, @Field("emp_id") String gatekeeper_id, @Field("complain_status") String complain_status, @Field("language_id") String language_id);


    @FormUrlEncoded
    @POST("technicianController.php")
    Single<ComplainResponse> getComplainDetail(@Field("getComplainDetail") String tag, @Field("society_id") String society_id, @Field("emp_id") String user_id, @Field("complain_id") String complain_id, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> closeComplainRequest(@Field("closeComplainRequest") String closeComplainRequest, @Field("society_id") String society_id, @Field("emp_id") String user_id, @Field("emp_name") String emp_name, @Field("complain_id") String complain_id, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> closeComplainAuto(@Field("closeComplainAuto") String closeComplainAuto, @Field("society_id") String society_id, @Field("emp_id") String user_id, @Field("complain_id") String complain_id, @Field("on_hold") String on_hold, @Field("hold_msg") String hold_msg, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> inProgressComplain(@Field("inProgressComplain") String inProgressComplain, @Field("society_id") String society_id, @Field("emp_id") String user_id, @Field("complain_id") String complain_id, @Field("language_id") String language_id);


    @FormUrlEncoded
    @POST("technicianController.php")
    Single<NotificationResponce> getNotification(@Field("getNotification") String tag, @Field("emp_id") String employee_id, @Field("read") String read, @Field("lng") String lng, @Field("society_id") String society_id, @Field("language_id") String language_id);

    //gatekeeper_id=gatekeeper_id
    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> removeNotification(@Field("DeleteUserNotification") String tag, @Field("guard_notification_id") String guard_notification_id, @Field("lng") String lng, @Field("emp_id") String gatekeeper_id, @Field("language_id") String language_id);

    //emp_id=gatekeeper_id
    @FormUrlEncoded
    @POST("technicianController.php")
    Single<CommonResponse> removeAllNotification(@Field("DeleteUserNotificationAll") String tag, @Field("lng") String lng, @Field("emp_id") String emp_id, @Field("language_id") String language_id);


    @FormUrlEncoded
    @POST("event_passes_controller.php")
    Single<CommonResponse> scannerData(@Field("passAllow") String passAllow, @Field("pass_id") String pass_id, @Field("society_id") String society_id, @Field("gatekeeper_id") String gatekeeper_id, @Field("language_id") String language_id);

    @FormUrlEncoded
    @POST("event_passes_controller.php")
    Single<CommonResponse> scannerDataDetail(@Field("passDetail") String passAllow, @Field("pass_id") String pass_id, @Field("society_id") String society_id, @Field("gatekeeper_id") String gatekeeper_id, @Field("language_id") String language_id);


    @Multipart
    @POST("technicianController.php")
    Single<ComplainResponse> editComplain(@Part("replyComplain") RequestBody tag, @Part("society_id") RequestBody society_id, @Part("complain_id") RequestBody complain_id, @Part("emp_id") RequestBody emp_id, @Part("compalain_title") RequestBody compalain_title, @Part("complain_review_msg") RequestBody complain_description, @Part MultipartBody.Part complain_photo, @Part("unit_id") RequestBody unit_id, @Part("user_id") RequestBody user_id, @Part("user_name") RequestBody user_name, @Part("complaint_category") RequestBody complain_category_id, @Part("complaint_status") RequestBody caomplaintStatus, @Part("complain_no") RequestBody complain_no, @Part MultipartBody.Part audioFile, @Part("language_id") RequestBody language_id);


    @FormUrlEncoded
    @POST("technicianController.php")
    Single<EmployeeAttendanceResponse> getEmpAttend(@Field("getEmpAttendNew") String tag, @Field("society_id") String society_id, @Field("unit_id") String unit_id, @Field("emp_id") String emp_id, @Field("language_id") String language_id);


    @FormUrlEncoded
    @POST("assets_controller.php")
    Single<AssetListResponse> getAssets(@Field("getAssets") String tag, @Field("society_id") String society_id, @Field("emp_id") String emp_id);


    @FormUrlEncoded
    @POST("assets_controller.php")
    Single<AssetDetailResponse> getAssetsMaintance(@Field("getAssetsMaintance") String tag, @Field("society_id") String society_id, @Field("emp_id") String emp_id);

    @FormUrlEncoded
    @POST("assets_controller.php")
    Single<CompletedAssetResponse> myCompletedMaintaince(@Field("myCompletedMaintaince") String tag, @Field("society_id") String society_id, @Field("emp_id") String emp_id);

    @FormUrlEncoded
    @POST("assets_controller.php")
    Single<MissingAssetResponse> myMissingMaintaince(@Field("myMissingMaintaince") String tag, @Field("society_id") String society_id, @Field("emp_id") String emp_id);


    @Multipart
    @POST("assets_controller.php")
    Single<CommonResponse> assetsMaintenanceCompleted(@Part("assetsMaintenanceCompleted") RequestBody tag, @Part("society_id") RequestBody society_id, @Part("assets_id") RequestBody assets_id, @Part("assets_category_id") RequestBody assets_category_id, @Part("emp_id") RequestBody emp_id, @Part("assets_maintenance_id") RequestBody assets_maintenance_id, @Part("maintenance_date") RequestBody maintenance_date, @Part("maintenance_amount") RequestBody maintenance_amount, @Part("remark") RequestBody remark, @Part("completed_by") RequestBody completed_by, @Part MultipartBody.Part maintenance_photo, @Part MultipartBody.Part maintenance_invoice);


    @FormUrlEncoded
    @POST("version_controller.php")
    Single<VersionResponce> getVersion(@Field("getVersion") String getVersion, @Field("version_app") String version_app, @Field("mobile_app") String mobile_app);

}
