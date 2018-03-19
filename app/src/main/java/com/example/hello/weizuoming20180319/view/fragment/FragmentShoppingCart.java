package com.example.hello.weizuoming20180319.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hello.weizuoming20180319.R;
import com.example.hello.weizuoming20180319.model.bean.MyBean;
import com.example.hello.weizuoming20180319.model.bean.MyJieSuan;
import com.example.hello.weizuoming20180319.presenter.Presenter;
import com.example.hello.weizuoming20180319.util.Url;
import com.example.hello.weizuoming20180319.view.MyView;
import com.example.hello.weizuoming20180319.view.adapter.MyChaXunAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by Dash on 2018/1/23.
 */
public class FragmentShoppingCart extends Fragment implements MyView {

    private Presenter presenter;
    private int num;
    private String price;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                MyJieSuan myHeJiBean= (MyJieSuan) msg.obj;
                sumAll.setText("合计:¥"+myHeJiBean.getPrice());
                numAll.setText("去结算("+myHeJiBean.getNum()+")");
                num = myHeJiBean.getNum();
                price = myHeJiBean.getPrice();
            }
        }
    };
    private MyChaXunAdapter adapter;
    private RecyclerView recyclerView;
    private CheckBox cheAll;
    private TextView numAll;
    private TextView sumAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart_layout,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        cheAll = view.findViewById(R.id.che_all);
        numAll = view.findViewById(R.id.num_all);
        sumAll = view.findViewById(R.id.sum_all);
     //   ButterKnife.bind(getActivity());
        presenter = new Presenter(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private boolean isChild(List<MyBean.DataBean> list) {
        for (int i=0;i<list.size();i++){
            if (!list.get(i).isCheckbox()){
                return false;
            }
        }
        return true;
    }

    private boolean ischecked(int i, List<MyBean.DataBean> list) {
        for (int j=0;j<list.get(i).getList().size();j++){
            if (list.get(i).getList().get(j).getSelected()==0){
                return false;
            }
        }
        return true;
    }
    @Override
    public void getReView(ResponseBody responseBody) {
        try {
            MyBean myBean = new Gson().fromJson(responseBody.string(), MyBean.class);
            List<MyBean.DataBean> list = myBean.getData();
            //二级列表全部选中一级列表选中
            for (int i=0;i<list.size();i++){
                if (ischecked(i,list)){
                    list.get(i).setCheckbox(true);
                }
            }
            cheAll.setChecked(isChild(list));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                    ,LinearLayoutManager.VERTICAL,false));
            adapter = new MyChaXunAdapter(getActivity(), myBean, presenter, handler);

            recyclerView.setAdapter(adapter);
            adapter.setNumSumAll();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUrl(Url.chaxun);
        presenter.attachView(this);
    }




    @Override
    public void getError(Throwable throwable) {

    }


    @OnClick({R.id.che_all, R.id.num_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.che_all:
                if (adapter!=null){
                    adapter.allChed(cheAll.isChecked());
                }
                break;
            case R.id.num_all:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.jiebang();
        if (presenter!=null){
            presenter.dettach();
        }
    }
}
