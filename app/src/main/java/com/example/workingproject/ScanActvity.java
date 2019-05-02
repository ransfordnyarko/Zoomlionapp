package com.example.workingproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActvity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(ScanActvity.this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {
        HomeActivity.resultView.setText(result.getText());
        onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

}

