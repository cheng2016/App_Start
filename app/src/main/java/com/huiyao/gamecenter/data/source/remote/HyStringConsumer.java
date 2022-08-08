package com.huiyao.gamecenter.data.source.remote;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;

/***
 * rxjava 返回
 */
public abstract class HyStringConsumer implements Consumer<String> {
    public static final String TAG = HyStringConsumer.class.getSimpleName();

    public HyStringConsumer() {

    }

    @Override
    public void accept(String s) throws Exception {
        disposeBaseData(s);
    }


    private void disposeBaseData(String s) {
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                int status = -1;
                String message = "";
                if (jsonObject.has("status")) {
                    status = jsonObject.optInt("status");
                } else if (jsonObject.has("Code")) {
                    //u9 接口部分返回状态码为code
                    status = jsonObject.optInt("Code");
                }else if(jsonObject.has("result")){
                    status = jsonObject.optInt("result");
                }

                if (jsonObject.has("message")) {
                    message = jsonObject.optString("message");
                }
                if (jsonObject.has("Message")) {
                    message = jsonObject.optString("Message");
                }
                onAcceptComplete(status, message, s);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, "HyStringConsumer解析状态码错误>>>" + e.getMessage());
            }
        } else {
            Log.i(TAG, "HyStringConsumer返回数据为空");
        }
    }


    protected abstract void onAcceptComplete(int status, String message, String completeInfo);


    /*public  interface StringConsumerCallback{
        void onAcceptComplete(int status,String message,String completeInfo);
    };*/


}
