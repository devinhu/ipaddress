package com.example.huxinwu.ipaddress;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huxinwu.ipaddress.action.IPAction;
import com.example.huxinwu.ipaddress.model.AddressItem;
import com.example.huxinwu.ipaddress.model.AddressResult;
import com.example.huxinwu.ipaddress.model.IPResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_text;
    private Button btn_submit;
    private TextView text_result;
    private TextView txt_copy;

    private IPAction action;
    private String ip;
    private String latlng;

    private StringBuilder addressStr;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                text_result.setText(String.valueOf(addressStr));
            }else{
                Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_text = findViewById(R.id.edit_text);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        text_result = findViewById(R.id.text_result);
        txt_copy = findViewById(R.id.txt_copy);
        txt_copy.setOnClickListener(this);

        action = new IPAction();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_submit){
            ip = edit_text.getText().toString();
            if(TextUtils.isEmpty(ip)){
                Toast.makeText(this, "ip不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        IPResult response = action.getIPResult(ip);
                        if(response != null){
                            latlng = response.getLat() + "," + response.getLon();
                            AddressResult result = action.getAddressResult(latlng);
                            addressStr = new StringBuilder();
                            if(result != null && result.getResults() != null && result.getResults().size() > 0){
                                addressStr.append(result.getResults().get(0).getFormatted_address());
                                handler.sendEmptyMessage(0);
                            }
                        }else{
                            handler.sendEmptyMessage(1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(1);
                    }
                }
            }).start();
        }

        if(v.getId() == R.id.txt_copy){
            String text = text_result.getText().toString();
            if(TextUtils.isEmpty(text)){
                Toast.makeText(this, "结果为空", Toast.LENGTH_SHORT).show();
                return;
            }
            ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            ClipData myClip = ClipData.newPlainText("text", text_result.getText().toString());
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }
}
