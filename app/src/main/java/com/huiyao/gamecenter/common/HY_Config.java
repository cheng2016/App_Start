package com.huiyao.gamecenter.common;

import android.content.Context;

import com.huiyao.gamecenter.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HY_Config
{
    private static final String TAG = "HY";
    private static HY_Config xmConfig;
    private Map<String, String> map = new HashMap<String, String>();

    public static HY_Config getInstance(Context context)
    {
        if (xmConfig == null)
        {
            synchronized (HY_Config.class)
            {
                if (xmConfig == null)
                {
                    xmConfig = new HY_Config(context);
                }
            }
        }
        return xmConfig;
    }

    private HY_Config(Context context)
    {
        try
        {
            InputStream is = context.getAssets().open(
                    HY_Constants.HY_GAME_CONFIG);
            
            InputStreamReader reader = new InputStreamReader(is);
            char[] buf = new char[1024];
            StringBuffer buffer = new StringBuffer();
            while (reader.read(buf) > 0)
            {
                buffer.append(buf);
            }
            is.close();
            JSONObject json = new JSONObject(buffer.toString());
            Iterator<?> iter = json.keys();
            String key = null;
            while (iter.hasNext())
            {
                key = (String) iter.next();
                Logger.d(TAG, key);
                this.map.put(key, json.get(key).toString());
            }
            Logger.d(TAG, "assets下已经存在渠道参数配置的文件：" + HY_Constants.HY_GAME_CONFIG);

        }
        catch (IOException e)
        {
            Logger.d(TAG, "打包工具没有将信息 " + HY_Constants.HY_GAME_CONFIG
                    + "配置在assets下");
            throw new IllegalStateException(e);
        }
        catch (JSONException e)
        {
            throw new IllegalStateException(e);
        }
    }

    public String get(String key)
    {
        return (String) this.map.get(key);
    }
}
