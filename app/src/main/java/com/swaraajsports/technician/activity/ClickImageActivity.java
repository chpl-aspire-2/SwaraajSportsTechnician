package com.swaraajsports.technician.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.swaraajsports.technician.util.PreferenceManager;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class ClickImageActivity extends AppCompatActivity {

    Options options;
    ArrayList<String> returnValue = new ArrayList<>();
    private File imagePath;


    PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


                options = Options.init()
                        .setRequestCode(111)
                        .setCount(bundle.getInt("picCount"))
                        .setFrontfacing(false)
                        .setMode(Options.Mode.Picture)
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                        .setPath("/storage/self/primary");

                Pix.start(ClickImageActivity.this, options);
            }else {
                openCameraImagePickerIntent();
            }
        }else {
            BasicFunctions.toast(this,"Data Missing picCount",1);
        }

    }

    private void openCameraImagePickerIntent() {
        int FRONT_CAMERA = 0;
        int BACK_CAMERA = 1;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING",BACK_CAMERA);
        startActivityForResult(intent, 14596);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                returnValue  = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                File f = new File(returnValue.get(0));
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("listPic",returnValue);
                returnIntent.putExtra("onPhotoTaken", f.getAbsolutePath());
                setResult(Activity.RESULT_OK, returnIntent);
            }
            finish();
        }else if (requestCode == 14596 && resultCode == RESULT_OK){
            saveBitmap((Bitmap) data.getExtras().get("data"));

            if (imagePath!=null) {
                Intent returnIntent = new Intent();
                returnValue.add(imagePath.getAbsolutePath());
                returnIntent.putStringArrayListExtra("listPic", returnValue);
                returnIntent.putExtra("onPhotoTaken", imagePath.getAbsolutePath());
                setResult(Activity.RESULT_OK, returnIntent);
            }

            finish();

        }
    }


    public void saveBitmap(Bitmap bitmap) {
        imagePath = BasicFunctions.getOutputMediaFile(ClickImageActivity.this);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            BasicFunctions.log("GREC", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Pix.start(ClickImageActivity.this, options);
            } else {
                Toast.makeText(ClickImageActivity.this, "Storage permission required!" , Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}