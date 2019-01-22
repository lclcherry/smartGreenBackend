package com.micer.backend.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonUtil {

    private static JSONObject jsonObject = new JSONObject();

    /**
     * 将对象转换成json字符串。
     * <p>Title: ObjToJson</p>
     * <p>Description: </p>
     * @param object
     * @return
     */

    public static String convertObj2Json(Object object) {
        String s = null;
        try {
            s = jsonObject.toJSONString(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param s json数据
     * @param clazz 对象中的object类型
     * @return
     */

    public static <T> T convertJson2Obj(String s, Class<T> clazz) {
        T t = null;
        try {
            t = jsonObject.getObject(s, clazz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
        try {
            List<T> list = JSON.parseArray(jsonData, beanType);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}