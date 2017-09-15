package com.lm.testzxing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;

public class DecoderActivity extends AppCompatActivity
    implements ActivityCompat.OnRequestPermissionsResultCallback, OnQRCodeReadListener {

  private static final int MY_PERMISSION_REQUEST_CAMERA = 0;

  private ViewGroup mainLayout;

  private TextView resultTextView;
  private QRCodeReaderView qrCodeReaderView;
  private CheckBox flashlightCheckBox;
  private CheckBox enableDecodingCheckBox;
  private PointsOverlayView pointsOverlayView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_decoder);

    mainLayout = (ViewGroup) findViewById(R.id.main_layout);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_GRANTED) {
      initQRCodeReaderView();
    } else {
      requestCameraPermission();
    }
  }

  @Override protected void onResume() {
    super.onResume();

    if (qrCodeReaderView != null) {
      qrCodeReaderView.startCamera();
    }
  }

  @Override protected void onPause() {
    super.onPause();

    if (qrCodeReaderView != null) {
      qrCodeReaderView.stopCamera();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
      return;
    }

    if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      Snackbar.make(mainLayout, "相机被授予许可", Snackbar.LENGTH_SHORT).show();
      initQRCodeReaderView();
    } else {
      Snackbar.make(mainLayout, "摄像头的权限请求被拒绝了.", Snackbar.LENGTH_SHORT)
          .show();

    }
  }

  // Called when a QR is decoded
  // "text" : the text encoded in QR
  // "points" : points where QR control points are placed
  @Override public void onQRCodeRead(String text, PointF[] points) {
    resultTextView.setText(text);
    pointsOverlayView.setPoints(points);
  }

  private void requestCameraPermission() {
    //版本判断
    if (Build.VERSION.SDK_INT >= 23) {
      ActivityCompat.requestPermissions(this, new String[]{
              Manifest.permission.CAMERA
      }, MY_PERMISSION_REQUEST_CAMERA);
    }else {
      Toast.makeText(this,"版本低于23,请到设置中给予权限",Toast.LENGTH_LONG).show();
      finish();
    }
  }

  private void initQRCodeReaderView() {
    View content = getLayoutInflater().inflate(R.layout.content_decoder, mainLayout, true);

    qrCodeReaderView = (QRCodeReaderView) content.findViewById(R.id.qrdecoderview);
    resultTextView = (TextView) content.findViewById(R.id.result_text_view);
    flashlightCheckBox = (CheckBox) content.findViewById(R.id.flashlight_checkbox);
    enableDecodingCheckBox = (CheckBox) content.findViewById(R.id.enable_decoding_checkbox);
    pointsOverlayView = (PointsOverlayView) content.findViewById(R.id.points_overlay_view);
    // 使用此函数来更改自动对焦间隔(默认为5秒
    qrCodeReaderView.setAutofocusInterval(2000L);
    qrCodeReaderView.setOnQRCodeReadListener(this);
    qrCodeReaderView.setBackCamera();
    flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        // 使用此函数启用/禁用手电筒
        qrCodeReaderView.setTorchEnabled(isChecked);
      }
    });
    enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        qrCodeReaderView.setQRDecodingEnabled(isChecked);
      }
    });
    qrCodeReaderView.startCamera();
  }
}