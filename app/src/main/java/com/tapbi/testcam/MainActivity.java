package com.tapbi.testcam;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import android.view.TextureView;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AndroidCameraApi";

    // Button cho capture ảnh
    private Button takePictureButton;

    // preview camera
    private TextureView textureView;

    // kiểm tra trạng thái  ORIENTATION của ảnh đầu ra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }

        final Camera camera = Camera.open(1);
        CameraPreview cameraPreview = new CameraPreview(MainActivity.this, camera);

        // preview is required. But you can just cover it up in the layout.
        FrameLayout previewFL = findViewById(R.id.fl);
        previewFL.addView(cameraPreview);
        camera.startPreview();

        // take picture button
        findViewById(R.id.btn_takepicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        // path of where you want to save it
                        File pictureFile = new File(getFilesDir() + "/images/pic0");

                        try {
                            FileOutputStream fos = new FileOutputStream(pictureFile);
                            fos.write(data);
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}
