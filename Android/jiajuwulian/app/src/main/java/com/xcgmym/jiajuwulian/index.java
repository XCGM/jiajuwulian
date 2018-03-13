package com.xcgmym.jiajuwulian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class index extends AppCompatActivity implements View.OnClickListener {

    private EditText username = null;
    private EditText password = null;
    private Button login = null;
    private Button signin = null;

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

        }
        if(view == signin)
        {

        }
    }
}

