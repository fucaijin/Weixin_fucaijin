package com.fucaijin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final Button bt_change_mode = (Button) findViewById(R.id.bt_change_mode);
        bt_change_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_password.getInputType() == 128){//如果现在是显示密码模式
                    et_password.setInputType(129);//设置为隐藏密码
                    bt_change_mode.setText("密码不可见");
                }else {
                    et_password.setInputType(128);//设置为显示密码
                    bt_change_mode.setText("密码可见");
                }
                et_password.setSelection(et_password.getText().length());//设置光标的位置到末尾
            }
        });


    }
}
