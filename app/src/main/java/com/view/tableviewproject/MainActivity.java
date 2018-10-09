package com.view.tableviewproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.view.entitiy.ParentEntity;
import com.view.tableview.DisplayUtil;
import com.view.tableview.TableView;
import com.view.tableview.listener.OnItemClickListenter;
import com.view.tableview.listener.OnTableViewListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View tv_refresh;
    private View tv_tabview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_tabview = findViewById(R.id.tv_tabview);
        tv_refresh = findViewById(R.id.tv_refresh);
        tv_tabview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TabviewActivity.class));
            }
        });
        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TabviewRefreshActivity.class));
            }
        });

    }
}
