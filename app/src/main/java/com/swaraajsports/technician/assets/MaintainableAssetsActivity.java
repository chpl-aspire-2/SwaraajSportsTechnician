package com.swaraajsports.technician.assets;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.askPermission.PermissionHandler;
import com.ajitmaurya.basicsetup.askPermission.Permissions;
import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.ajitmaurya.basicsetup.view.CustomEditText;
import com.swaraajsports.technician.util.PickFileActivity;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.BaseActivityClass;
import com.swaraajsports.technician.activity.ClickImageActivity;
import com.swaraajsports.technician.adapter.MaintainableAssetAdapter;
import com.swaraajsports.technician.response.AssetDetailResponse;
import com.swaraajsports.technician.response.CommonResponse;
import com.swaraajsports.technician.util.VariableBag;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@SuppressLint("NonConstantResourceId")
public class MaintainableAssetsActivity extends BaseActivityClass {

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.recyData)
    RecyclerView recyData;

    @BindView(R.id.psBar)
    ProgressBar psBar;

    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tv_title)
    TextView tv_title;

    ActivityResultLauncher<Intent> waitResultForPhoto,waitResultForFile,waitResultForFilePhoto;
    ArrayList<String> filePathsTemp = new ArrayList<>();

    MultipartBody.Part fileToUpload = null;
    MultipartBody.Part fileToUploadPhoto = null;
    TextView tv_bill_pay_file;

    private boolean flgPic = false;

    ImageView iv_profile;

    @Override
    protected int getContentView() {
        return R.layout.activity_maintable_assets;//your layout
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        recyData.setLayoutManager(new LinearLayoutManager(this));
        tv_title.setText("Upcoming Maintenance");

        psBar.setVisibility(View.VISIBLE);
        recyData.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);


        getApiCallData();

        waitResultForPhoto= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        String onPhotoTaken;
                        if (result.getData() != null) {
                            onPhotoTaken = result.getData().getStringExtra("onPhotoTaken");
                            filePathsTemp.add(onPhotoTaken);
                            flgPic = true;
                            BasicFunctions.displayImageBG(MaintainableAssetsActivity.this, iv_profile, filePathsTemp.get(0));

                        }

                    }
                });


        waitResultForFile= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                        fileToUpload = VariableBag.partsFilePick;
                        if (tv_bill_pay_file!=null) {
                            tv_bill_pay_file.setText(result.getData().getStringExtra("filePath"));
                        }
                    }
                    }
                });


        waitResultForFilePhoto= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        String onPhotoTaken;
                        if (result.getData() != null) {
                            onPhotoTaken = result.getData().getStringExtra("onPhotoTaken");

                            File file = new File(BasicFunctions.compressImage(MaintainableAssetsActivity.this,onPhotoTaken));
                            RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                            fileToUpload = MultipartBody.Part.createFormData("maintenance_invoice", file.getName(), requestBody);

                        }

                    }
                });


        ivBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = getContentResolver().query( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void getApiCallData() {

        getCallSociety().getAssetsMaintance("getAssetsMaintance",
                        preferenceManager.getSocietyId(),
                        preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<AssetDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {

                            psBar.setVisibility(View.GONE);
                            recyData.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);

                            tvNoData.setText(e.getLocalizedMessage());
                        });
                    }

                    @Override
                    public void onNext(AssetDetailResponse assetListResponse) {

                        runOnUiThread(() -> {

                            if (assetListResponse.getMaintenance()!=null && assetListResponse.getMaintenance().size()>0){

                                recyData.setVisibility(View.VISIBLE);
                                tvNoData.setVisibility(View.GONE);

                                MaintainableAssetAdapter assetAdapter = new MaintainableAssetAdapter(assetListResponse.getMaintenance(),MaintainableAssetsActivity.this);
                                recyData.setAdapter(assetAdapter);

                                assetAdapter.setUp(new MaintainableAssetAdapter.ClickRow() {
                                    @Override
                                    public void click(AssetDetailResponse.Maintenance as, AssetDetailResponse.Schedule scdule) {

                                        fileToUpload =null;
                                        final Dialog dialog = new Dialog(MaintainableAssetsActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.submit_comple_work);

                                        CustomEditText etAmount = dialog.findViewById(R.id.etNoofUnitsPrv);
                                        CustomEditText etRemark = dialog.findViewById(R.id.etNoofUnits);
                                        iv_profile = dialog.findViewById(R.id.iv_profile);

                                        tv_bill_pay_file = dialog.findViewById(R.id.tv_bill_pay_file);
                                        ImageView tv_bill_pay_amount = dialog.findViewById(R.id.tv_bill_pay_amount);

                                        tv_bill_pay_amount.setOnClickListener(new OnSingleClickListener() {
                                            @Override
                                            public void onSingleClick(View v) {

                                                StrictMode.VmPolicy.Builder builder2 = new StrictMode.VmPolicy.Builder();
                                                StrictMode.setVmPolicy(builder2.build());

                                                final CharSequence[] options = {
                                                        "Take Photo",
                                                        "Choose From File",
                                                        "Cancel"};
                                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MaintainableAssetsActivity.this);
                                                builder.setTitle("Select Option");
                                                builder.setItems(options, (dialog2, item) -> {
                                                    if (options[item].equals("Take Photo")) {

                                                        Permissions.check(MaintainableAssetsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.camera_storege_per), null, new PermissionHandler() {
                                                            @Override
                                                            public void onGranted() {

                                                                VariableBag.partsFilePick = null;

                                                                dialog2.dismiss();
                                                                Intent i = new Intent(MaintainableAssetsActivity.this, ClickImageActivity.class);
                                                                i.putExtra("picCount",1);
                                                                i.putExtra("isGallery", false);
                                                                waitResultForFilePhoto.launch(i);


                                                            }
                                                        });

                                                    }  else if (options[item].equals("Choose From File")) {


                                                        Permissions.check(MaintainableAssetsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.storege_per), null, new PermissionHandler() {
                                                            @Override
                                                            public void onGranted() {
                                                                VariableBag.partsFilePick = null;

                                                                dialog2.dismiss();
                                                                Intent intent = new Intent(MaintainableAssetsActivity.this, PickFileActivity.class);
                                                                intent.putExtra("partValue", "maintenance_invoice");
                                                                waitResultForFile.launch(intent);


                                                            }
                                                        });

                                                    } else if (options[item].equals("Cancel")) {

                                                        dialog2.dismiss();
                                                    }
                                                });
                                                builder.show();







                                            }
                                        });


                                        iv_profile.setOnClickListener(new OnSingleClickListener() {
                                            @Override
                                            public void onSingleClick(View v) {

                                                flgPic = false;
                                                iv_profile.setImageResource(R.drawable.addphotos);

                                                Intent i = new Intent(MaintainableAssetsActivity.this, ClickImageActivity.class);
                                                i.putExtra("picCount",1);
                                                i.putExtra("isGallery", false);
                                                waitResultForPhoto.launch(i);

                                            }
                                        });

                                        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                                        btnCancel.setOnClickListener(new OnSingleClickListener() {
                                            @Override
                                            public void onSingleClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
                                        btnSubmit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {



                                                if (flgPic){

                                                    if (filePathsTemp.size() > 0) {
                                                        File file = new File(BasicFunctions.compressImage(MaintainableAssetsActivity.this, filePathsTemp.get(0)));
                                                        RequestBody requestBody = RequestBody.create(file,MediaType.parse("multipart/form-data"));
                                                        fileToUploadPhoto = MultipartBody.Part.createFormData("maintenance_photo", file.getName(), requestBody);


                                                        RequestBody tag = RequestBody.create("assetsMaintenanceCompleted",MediaType.parse("text/plain"));
                                                        RequestBody societyId = RequestBody.create(preferenceManager.getSocietyId(),MediaType.parse("text/plain"));
                                                        RequestBody assetId = RequestBody.create(as.getAssetsId(),MediaType.parse("text/plain"));
                                                        RequestBody assets_category_id = RequestBody.create(scdule.getAssetsCategoryId(),MediaType.parse("text/plain"));
                                                        RequestBody empId = RequestBody.create(preferenceManager.getUserId(),MediaType.parse("text/plain"));
                                                        RequestBody assetMainId = RequestBody.create(scdule.getAssetsMaintenanceId(),MediaType.parse("text/plain"));
                                                        RequestBody maintDate = RequestBody.create(scdule.getMaintenanceDate(),MediaType.parse("text/plain"));
                                                        RequestBody maintAmount = RequestBody.create(etAmount.getText().toString(),MediaType.parse("text/plain"));
                                                        RequestBody remark = RequestBody.create(etRemark.getText().toString(),MediaType.parse("text/plain"));
                                                        RequestBody completed_by = RequestBody.create(preferenceManager.getUserName(),MediaType.parse("text/plain"));



                                                        getCallSociety().assetsMaintenanceCompleted(tag,
                                                                societyId,assetId,assets_category_id,
                                                                empId,assetMainId,
                                                                maintDate,
                                                                maintAmount,
                                                                remark,
                                                                completed_by,
                                                                fileToUploadPhoto,
                                                                fileToUpload).subscribeOn(Schedulers.io())
                                                                .observeOn(Schedulers.newThread())
                                                                .subscribe(new Subscriber<CommonResponse>() {
                                                                    @Override
                                                                    public void onCompleted() {

                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                dialog.dismiss();
                                                                            }
                                                                        });

                                                                    }

                                                                    @Override
                                                                    public void onNext(CommonResponse commonResponse) {

                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                dialog.dismiss();
                                                                                getApiCallData();
                                                                            }
                                                                        });

                                                                    }
                                                                });



                                                    }else {
                                                        BasicFunctions.toast(MaintainableAssetsActivity.this,"Capture image of work",1);

                                                    }





                                                }else {

                                                    BasicFunctions.toast(MaintainableAssetsActivity.this,"Capture image of work",1);

                                                }


                                            }
                                        });

                                        dialog.show();

                                    }
                                });
                                

                            }else {

                                recyData.setVisibility(View.GONE);
                                tvNoData.setVisibility(View.VISIBLE);

                                tvNoData.setText(assetListResponse.getMessage());
                            }

                            psBar.setVisibility(View.GONE);



                        });

                    }
                });

    }

}