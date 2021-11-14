package com.example.nhandiengiongnoi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    protected static final int RESULT_SPEECH = 1;
    TextView Noidung;
    TextToSpeech textToSpeech;
    LottieAnimationView nhapnhay, dong, ha;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Noidung = findViewById(R.id.noidung);
        nhapnhay = findViewById(R.id.nghe);
        dong = findViewById(R.id.loanhay);
        nhapnhay.pauseAnimation();
        // Chuyển giọng nói thành text
        LottieAnimationView btnSpeak = findViewById(R.id.ghiam);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View  view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    Noidung.setHint("Đang nghe...");
                    nhapnhay.playAnimation();
                }catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // Chuyển text thành giọng nói
        LottieAnimationView btnnoi = findViewById(R.id.noi);
        btnnoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();

                if ( Noidung.getText().toString().equals("Bây giờ là mấy giờ") ){
                    texttospeech("Bây giờ là "+ today.format("%k:%M:%S"));
                    return;
                }

                if ( Noidung.getText().toString().equals("Hôm nay là ngày bao nhiêu")){
                    texttospeech("Hôm nay là ngày "+ today.monthDay +
                            " tháng "+ today.month + " năm "+ today.year);
                    return;
                }

                if ( Noidung.getText().toString().equals("Ai đẹp trai nhất FPT") ){
                    texttospeech("Thầy Bình dạy công nghệ thông tin ở FPT Poly là đẹp trai nhất!");
                    return;
                }

                if ( Noidung.getText().toString().isEmpty() ){
                    texttospeech("Chưa có gì để nói cả. Hãy dạy tôi đi!");
                    return;
                }


                    texttospeech( Noidung.getText().toString() );

            }
        });


        //Clean văn bản
        LottieAnimationView btnxoa = findViewById(R.id.xoa);
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dong.setVisibility(View.VISIBLE);
                Noidung.setText("");
                Noidung.setHint("Hãy nói gì đi...");
            }
        });


    }

    public void  texttospeech(String text){
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if ( status != TextToSpeech.ERROR ){
                    textToSpeech.setLanguage( new Locale("vi_VN") );
                    textToSpeech.setSpeechRate((float) 1);
                    textToSpeech.speak(text , TextToSpeech.QUEUE_FLUSH , null);

                }
            }
        });
    }


    // Set văn bản sau khi nói
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> text =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Noidung.setText(text.get(0));
                    dong.setVisibility(View.GONE);
                    nhapnhay.pauseAnimation();
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    if ( Noidung.getText().toString().contains("là mấy giờ") ){
                        texttospeech("Bây giờ là "+ today.format("%k:%M:%S"));
                        return;
                    }

                    if ( Noidung.getText().toString().contains("là ngày bao nhiêu")){
                        texttospeech("Hôm nay là ngày "+ today.monthDay +
                                " tháng "+ today.month + " năm "+ today.year);
                        return;
                    }

                    if ( Noidung.getText().toString().contains("đẹp trai nhất") || Noidung.getText().toString().contains("thầy Bình")){
                        texttospeech("Thầy Bình dạy công nghệ thông tin ở FPT Poly là đẹp trai nhất!");
                        return;
                    }

                }
                break;
        }
    }

    // Chạy Permission
    @Override
    protected void onStart() {
        clickrequest();
        super.onStart();
    }

    // Check Permission
    private void clickrequest() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("Nếu bạn không cho phép thì chức năng 'Nhập văn bản bằng giọng nói' không thể sử dụng được!\n \nNếu bạn muốn bật lại nó thì hãy vào phần [Setting] > [Quyền truy cập] > [Bật các quyền truy cập]")
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .check();
    }
}