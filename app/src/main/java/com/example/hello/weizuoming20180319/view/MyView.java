package com.example.hello.weizuoming20180319.view;

import okhttp3.ResponseBody;

/**
 * Created by 韦作铭 on 2018/3/19.
 */

public interface MyView {
    void getReView(ResponseBody responseBody);
    void getError(Throwable throwable);
}
