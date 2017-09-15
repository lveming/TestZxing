package com.lm.testzxing;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QRCodeActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    QRCodeReaderView qrCodeReaderView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        textView= (TextView) findViewById(R.id.id_text);

        qrCodeReaderView= (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // 使用此函数启用/禁用解码
        qrCodeReaderView.setQRDecodingEnabled(true);

        // 使用此函数来更改自动对焦间隔(默认为5秒
        qrCodeReaderView.setAutofocusInterval(2000L);

        // 使用此函数启用/禁用手电筒
        qrCodeReaderView.setTorchEnabled(true);

        // 使用此函数来设置前置摄像头预览
        qrCodeReaderView.setFrontCamera();

        // 使用这个功能设置回相机预览
        qrCodeReaderView.setBackCamera();




    }
    // 当一个QR被解码时调用
    // "text" : 用QR编码的文本
    // "points" : 放置 QR 控制点的点
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
            textView.setText(text);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }



    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }
}
