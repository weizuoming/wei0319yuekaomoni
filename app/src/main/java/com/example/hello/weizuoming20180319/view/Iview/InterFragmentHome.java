package com.example.hello.weizuoming20180319.view.Iview;


import com.example.hello.weizuoming20180319.model.bean.FenLeiBean;
import com.example.hello.weizuoming20180319.model.bean.HomeBean;

/**
 * Created by Dash on 2018/1/23.
 */
public interface InterFragmentHome {
    void onSuccess(HomeBean homeBean);

    void onFenLeiDataSuccess(FenLeiBean fenLeiBean);
}
