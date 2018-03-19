package com.example.hello.weizuoming20180319.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello.weizuoming20180319.R;
import com.example.hello.weizuoming20180319.model.bean.DeatilBean;
import com.example.hello.weizuoming20180319.presenter.DeatailPresenter;
import com.example.hello.weizuoming20180319.util.GlideImageLoader;
import com.example.hello.weizuoming20180319.util.Url;
import com.example.hello.weizuoming20180319.view.Iview.DetailActivityInter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailActivityInter, View.OnClickListener {

    private int pid;
    private DeatailPresenter deatailPresenter;
    private Banner banner;
    private TextView detail_title;
    private TextView detail_bargin_price;
    private TextView detail_yuan_price;
    private TextView detai_add_cart;
    private ImageView detail_image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        banner = findViewById(R.id.banner);
        detail_title = findViewById(R.id.detail_title);
        detail_bargin_price = findViewById(R.id.detail_bargin_price);
        detail_yuan_price = findViewById(R.id.detail_yuan_price);
        detai_add_cart = findViewById(R.id.detai_add_cart);
        detail_image_back = findViewById(R.id.detail_image_back);

        //创建presenter
        deatailPresenter = new DeatailPresenter(this);

        //接收传递的pid
        pid = getIntent().getIntExtra("pid", -1);
        //如果不是默认值代表传递过来数据了
        if (pid != -1){

            //拿着传递的pid请求商品详情的接口,然后展示数据...MVP
            deatailPresenter.getDetailData(Url.DETAIL_URL,pid);
        }

        //初始化banner
        initBanner();

        //设置点击事件
        detai_add_cart.setOnClickListener(this);
        detail_image_back.setOnClickListener(this);

    }

    @Override
    public void onSuccess(DeatilBean deatilBean) {

        //添加删除线
        detail_yuan_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //设置数据显示
        detail_title.setText(deatilBean.getData().getTitle());
        detail_bargin_price.setText("优惠价:"+deatilBean.getData().getBargainPrice());
        detail_yuan_price.setText("原价:"+deatilBean.getData().getPrice());

        String[] strings = deatilBean.getData().getImages().split("\\|");

        final ArrayList<String> imageUrls = new ArrayList<>();
        for (int i = 0;i<strings.length;i++){
            imageUrls.add(strings[i]);
        }

        banner.setImages(imageUrls);
        banner.start();

        //bannner点击事件进行跳转
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(DetailActivity.this,ImageScaleActivity.class);
                //传递的数据...整个轮播图数据的集合需要传递,,,当前展示的图片的位置需要传递postion
                //intent传递可以传的数据...基本数据类型...引用数据类型(必须序列化,所有的类,包括内部类实现serilizable接口)...bundle
                intent.putStringArrayListExtra("imageUrls",imageUrls);
                intent.putExtra("position",position);

                startActivity(intent);
            }
        });


    }

    private void initBanner() {

        //设置banner样式...CIRCLE_INDICATOR_TITLE包含标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detai_add_cart://添加购物车

                Toast.makeText(DetailActivity.this,"正在开发中....",Toast.LENGTH_SHORT).show();

                break;
            case R.id.detail_image_back://返回
                //finish() startActivity() setResult()...context.startActiivty()

                //DetailActivity.this.finish();
                finish();
                break;
        }
    }
}
