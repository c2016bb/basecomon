package com.common.base.basecomon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.common.base.basecommon.BaseAdapter.Base.ViewHolder;
import com.common.base.basecommon.BaseAdapter.Decoration.ItemDecorationType;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.common.base.basecommon.BaseAdapter.RvCommonAdapter;
import com.common.base.basecommon.BaseAdapter.listener.OnLoadMoreListener;

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
//        setRecycleViewAdapter();
        setRecycleViewAdapter12();
    }
    private class OnItemClick implements OnItemAdapterClickListener {
        @Override
        public void onItemClick(View view, ViewHolder holder, int position, int viewType) {
//            mDatas.clear();
//             adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this,"点击了"+viewType,Toast.LENGTH_LONG).show();
        }
    }
    RvCommonAdapter rvCommonAdapter;
    RvComAdapter adapter;
    private  class OnLoadMore implements OnLoadMoreListener{
            @Override
            public void onLoadMore(ViewHolder holder) {

            }
        }
    private void addLists(){
        for (int i=0;i<50;i++){
            mDatas.add("item-->"+i);
        }
    }


        private void setRecycleViewAdapter12()
             {
                 addLists();
            adapter = new  RvComAdapter.Builder(this,R.layout.layout_item,mDatas ).
                    setOnItemAdapterClickListener(new OnItemClick())
                    .setLayoutManagerType(RvComAdapter.GRIDLAYOUTMANAGER,3)
//                    .setOrientation(OrientationHelper.HORIZONTAL)
                    .setOnLoadMoreListener(new OnLoadMore())
                    .setDefaltDecoration(ItemDecorationType.NOLASTITEM,R.drawable.shape_divider)
                    .setEmptyLayoutId(R.layout.layout_empty)
//                    .setOnEmptyListener(new OnEmptyListener() {
//                        @Override
//                        public void onEmpty(ViewHolder hodler) {
//                             hodler.setOnClickListener(R.id.empty_tv, new View.OnClickListener() {
//                                 @Override
//                                 public void onClick(View v) {
//                                     Toast.makeText(MainActivity.this,"点击了空数据",Toast.LENGTH_LONG).show();
//                                 }
//                             });
//                        }
//                    })
                    .into(recyclerView, new InitViewCallBack<String>() {
                        @Override
                        public void convert(ViewHolder holder, String s, int position) {
                            holder.setText(R.id.item_tv,s);
                        }
                    });

        }


    private void setRecycleViewAdapter()
    {
        rvCommonAdapter = new RvCommonAdapter.Builder<>(this,android.R.layout.simple_list_item_1,mDatas )
                .setOnItemAdapterClickListener(new OnItemClick())
                .setOnLoadMoreListener(new OnLoadMore())
                .createAdapter(recyclerView, new InitViewCallBack<String>() {
                    @Override
                    public void convert(ViewHolder viewHolder, String s, int i) {
                        viewHolder.setText(android.R.id.text1,s);
                    }
                });

    }
}
