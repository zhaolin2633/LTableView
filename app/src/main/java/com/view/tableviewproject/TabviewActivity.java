package com.view.tableviewproject;

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

public class TabviewActivity extends AppCompatActivity {

    private LinearLayout mContentView;


    public List<ParentEntity> getChildData() {
        ArrayList<ParentEntity> parentEntities = new ArrayList<>();
        ParentEntity child = new ParentEntity(ParentEntity.TYPE_NROMAL);
        child.name = "环球";
        ArrayList<String> itemEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemEntityList.add("12345678");
        }
        child.itemEntity = itemEntityList;
        parentEntities.add(child);
        child = new ParentEntity(ParentEntity.TYPE_NROMAL);
        child.name = "华阳";
        itemEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemEntityList.add("123456");
        }
        child.itemEntity = itemEntityList;
        parentEntities.add(child);
        child = new ParentEntity(ParentEntity.TYPE_NROMAL);
        child.name = "春熙路";
        itemEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemEntityList.add("12348");
        }
        child.itemEntity = itemEntityList;
        parentEntities.add(child);
        child = new ParentEntity(ParentEntity.TYPE_NROMAL);
        child.name = "世纪城";
        itemEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemEntityList.add("12345678895");
        }
        child.itemEntity = itemEntityList;
        parentEntities.add(child);
        return parentEntities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabview);
        mContentView = (LinearLayout) findViewById(R.id.contentView);
        initDisplayOpinion();
        ArrayList<String> mTitleData = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            if (i%2==0) {
                mTitleData.add("Title2222" + i);
            } else
                mTitleData.add("Title" + i);
        }

        ArrayList<ParentEntity> parentEntities = new ArrayList<>();
        for (int i = 0; i <100 ; i++) {
            ParentEntity parentEntity = new ParentEntity(ParentEntity.TYPE_TITLE);
            List<String> itemEntityList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                itemEntityList.add("12345678+"+j);
            }
            int index=i;
            parentEntity.itemEntity = itemEntityList;
            parentEntity.name = "四川省"+index;
            parentEntities.add(parentEntity);
        }
        final TableView mLockTableView = new TableView(this, mContentView, mTitleData, parentEntities);
        mLockTableView
                .setOnItemClickListenter(new OnItemClickListenter() {
                    @Override
                    public void onItemParentClick(View item, int position) {
                        mLockTableView.setIsOpen(position, getChildData());
                    }

                    @Override
                    public void onItemChildClick(View item, int position) {

                    }
                })
                .show(); //显示表格,此方法必须调用
        mLockTableView.setTableViewListener(new OnTableViewListener() {
            @Override
            public void onTableViewScrollChange(int x, int y) {
                int i = x + y;
            }
        });
//        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
//        mLockTableView.getTableScrollView().setLoadingMoreEnabled(true);
//        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);

    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }
}
