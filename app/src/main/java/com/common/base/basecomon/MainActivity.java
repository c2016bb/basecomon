package com.common.base.basecomon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;
import com.common.base.basecommon.BaseAdapter.DividerItemDecoration;
import com.common.base.basecommon.BaseAdapter.OnItemAdapterClickListener;
import com.common.base.basecommon.BaseAdapter.RvCommonAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 RecyclerView recyclerView;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json",
            "HTML","kolin","html5"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     recyclerView=(RecyclerView) findViewById(R.id.recycleView);
        setRecycleViewAdapter();
    }
    private class OnItemClick implements OnItemAdapterClickListener {
        @Override
        public void onItemClick(View view, ViewHolder holder, int position, int viewType) {

        }
    }
    RvCommonAdapter rvCommonAdapter;
    private void setRecycleViewAdapter()
    {
        rvCommonAdapter = new RvCommonAdapter.Builder<>(this,android.R.layout.simple_list_item_1,mDatas )
                .setOnItemAdapterClickListener(new OnItemClick())
                .createAdapter(recyclerView, new RvCommonAdapter.InitViewCallBack<String>() {
                    @Override
                    public void convert(ViewHolder viewHolder, String s, int i) {
                        viewHolder.setText(android.R.id.text1,s);
                    }
                });

    }
}
