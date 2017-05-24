package com.mengqi.dong.androidiocframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mengqi.dong.baseioclibrary.CheckNet;
import com.mengqi.dong.baseioclibrary.OnClick;
import com.mengqi.dong.baseioclibrary.ViewById;
import com.mengqi.dong.baseioclibrary.ViewUtils;


public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv_test)
    private TextView mTextTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);

        mTextTv.setText("test ioc");
    }

    @OnClick({R.id.tv_test})
    @CheckNet                 //没网就不执行该方法，直接打印toast
    private void login(View view){
        Toast.makeText(this,"tv test event",Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.iv_test})
    private void onIvClick(View view){
        Toast.makeText(this,"iv test event",Toast.LENGTH_LONG).show();
    }

}
