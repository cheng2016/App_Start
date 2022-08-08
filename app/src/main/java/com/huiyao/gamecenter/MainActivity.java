package com.huiyao.gamecenter;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huiyao.gamecenter.data.source.remote.AObserver;
import com.huiyao.gamecenter.data.source.remote.HttpApi;
import com.huiyao.gamecenter.data.source.remote.HttpFactory;
import com.huiyao.gamecenter.view.PointProcessBar;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button testBtn;
    PointProcessBar pointProcessBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hy_mainactivity_test);
        testBtn = (Button) findViewById(R.id.test);
        testBtn.setOnClickListener(this);
        pointProcessBar = (PointProcessBar) findViewById(R.id.point_processbar);
        setPointProcessBar();

    }





    private void setPointProcessBar(){

        List<String> textTitle = new ArrayList<>();
        textTitle.add("第7天");
        textTitle.add("第30天");
        textTitle.add("第60天");
        textTitle.add("第90天");


        List<Integer> nodeDayDaysList = new ArrayList<>();

        nodeDayDaysList.add(7);
        nodeDayDaysList.add(30);
        nodeDayDaysList.add(60);
        nodeDayDaysList.add(90);

        pointProcessBar.show(textTitle,nodeDayDaysList);
        pointProcessBar.setTotalDay(100);
        pointProcessBar.setCurrentDay(20);



    }



    @SuppressLint("CheckResult")
    public void testHttp(){
        HttpApi httpApi = HttpFactory.getHttpApiService();

        HashMap<String,String> parm = new HashMap<>();
        parm.put("a","111");
        parm.put("b","111");
        parm.put("c","111");
        parm.put("d","111");


        httpApi.AppInitData(parm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AObserver<String>() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.i("HttpFactory","observable观察者获取到的信息>>>"+s);


                    }
                });



        httpApi.fileDownload("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {


                    }

                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }
                });



    }





    @Override
    public void onClick(View view) {
        if(view == testBtn){
            //testHttp();
        }
    }
}
