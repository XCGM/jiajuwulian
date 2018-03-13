package com.xcgmym.jiajuwulian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class index extends AppCompatActivity implements View.OnClickListener {

    private EditText username = null;
    private EditText password = null;
    private Button login = null;
    private Button signin = null;
    private MySocket mySocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signin = (Button) findViewById(R.id.signin);

        login.setOnClickListener(this);
        signin.setOnClickListener(this);

        mySocket = ((WuLianApplication) this.getApplication()).getMySocket();
    }

    public void onStop()
    {
        super.onStop();
    }
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        String name = username.getText().toString();
        String pd = password.getText().toString();

        if(view == login)
        {
            if(name.length() == 0)
            {
                name = "ceshizhanghao";
                username.setText(name);
            }
            if(pd.length() == 0)
            {
                pd = "hello123..";
                password.setText(pd);
            }
            JSONObject json = new JSONObject();
            try
            {
                json.put("QingQiu", "DengLu");
                json.put("MingCheng",name);
                json.put("MiMa",pd);
            }catch (JSONException jsone)
            {

            }
            mySocket.faSong(json.toString());
        }
        if(view == signin)
        {
            if(name.length() == 0)
            {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }
            if(pd.length() == 0)
            {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject json = new JSONObject();
            try
            {
                json.put("QingQiu", "ZhuCe");
                json.put("MingCheng",name);
                json.put("MiMa",pd);
            }catch (JSONException jsone)
            {

            }
            mySocket.faSong(json.toString());
        }
    }
}

