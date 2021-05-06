package com.kite.okweather.utils;

import android.content.SharedPreferences;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InternetUtils {

    public static int login(SharedPreferences sp) {
        int code = 0;
        String admin = sp.getString("admin", "");
        String password = sp.getString("password", "");

        String isAutoLogin = sp.getString("isAutoLogin", "-1");
        String isSaveAdmin = sp.getString("isSaveAdmin", "-1");
        try {
            String Url = "http://hn216.api.yesapi.cn";
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("s", "App.SuperTable.FreeFindOne")
                    .add("return_data", "0")
                    .add("model_name", "t_user")
                    .add("database", "super")
                    .add("logic", "and")
                    .add("where", "[[\"username\", \"=\", \"" + admin + "\"]]")
                    .add("app_key", "54DA278ED064A7639DB265F4FBBFCDEA")
                    .add("sign", "602958A072CB52319942596B17A1CD19")
                    .build();
            Request request = new Request.Builder()
                    .url(Url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String res_password, res_admin;
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            if (jsonObject1.getInt("err_code") == 0) {
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                res_password = jsonObject2.getString("password");
                if ((password.equals(res_password) && (jsonObject.getInt("ret")) == 200)) {
                    res_admin = jsonObject2.getString("username");
                    res_password = jsonObject2.getString("password");
                    sp.edit().putString("admin", res_admin).putString("password", res_password).apply();
//                    Utils.log("登录的账户:\t" + admin);
//                    Utils.log("登录的密码:\t" + password);
//                    Utils.log("isSaveAdmin:\t" + isSaveAdmin);
//                    Utils.log("isAutoLogin:\t" + isAutoLogin);
                    code = jsonObject1.getInt("err_code");
                    Utils.log(jsonObject2.toString());
                } else {
                    Utils.log("密码错误");
                    sp.edit().putString("isSaveAdmin", "no").putString("isAutoLogin", "no").apply();
                    sp.edit().putString("admin", "-1").putString("password", "-1").apply();
                    code = jsonObject1.getInt("err_code");
                }
            } else if (jsonObject1.getInt("err_code") == 3) {
                sp.edit().putString("isSaveAdmin", "no").putString("isAutoLogin", "no").apply();
                sp.edit().putString("admin", "-1").putString("password", "-1").apply();
                Utils.log("密码错误");
                code = jsonObject1.getInt("err_code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }

    public static int reg(String name, String password, String email) {
        int reg = -1;
        try {
            String Url = "http://hn216.api.yesapi.cn";
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("s", "App.SuperTable.CheckCreate")
                    .add("return_data", "0")
                    .add("model_name", "t_user")
                    .add("database", "super")
                    .add("data", "{\"username\":\"" + name + "\",\"password\":\"" + password + "\",\"email\":\"" + email + "\"}")
                    .add("check_field", "username")
                    .add("app_key", "54DA278ED064A7639DB265F4FBBFCDEA")
                    .add("sign", "0A8F438BD5656C395CBE3BCF354A1229")
                    .build();
            Request request = new Request.Builder()
                    .url(Url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            Utils.log(jsonObject1.toString());
            if (jsonObject.getInt("ret") == 200) {
                if (jsonObject1.getInt("err_code") == 3) {
                    return 3;
                }
                if (jsonObject1.getInt("err_code") == 0) {
                    return 0;
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reg;
    }
}
