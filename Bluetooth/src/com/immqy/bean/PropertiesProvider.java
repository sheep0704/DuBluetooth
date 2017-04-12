package com.immqy.bean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 配置：保存到value.cfg里面
 * @author KARL-Dujinyang
 * @author https://github.com/sheep0704
 */
public class PropertiesProvider {
    private Properties properties;
    private Context context;
    private String fileName = "immqy.cfg";

    public PropertiesProvider(Context context) {
        this.properties = new Properties();
        this.context = context;
    }

    @SuppressLint("WorldWriteableFiles")
	@SuppressWarnings("deprecation")
	public boolean save(String key, Object value) {
        properties.put(key, String.valueOf(value));
        try {
			FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
            properties.store(outputStream, "");
            outputStream.close();
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            return false;
        }
        return true;
    }

    public Object get(String key) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            return null;
        }
        return properties.getProperty(key);
    }
}
