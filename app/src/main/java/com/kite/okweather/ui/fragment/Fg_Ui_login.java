package com.kite.okweather.ui.fragment;

import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Db_User;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.ui.adapter.CityAdapter;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.InternetUtils;
import com.kite.okweather.utils.Utils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.BaseDialog;

import org.litepal.LitePal;

import java.util.List;


public class Fg_Ui_login extends BaseFragment {

    Button bt_reg, bt_login;
    CheckBox cb_login, cb_login2;
    EditText ed_login_admin, ed_login_password;
    SharedPreferences sp_login;

    static BaseDialog dialog;

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    //主界面获取焦点
    int i = 0;

    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    //  Utils.log(i++);
                    if (i > 1) {
                        return false;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.fg_login;
    }

    @Override
    protected void initView(View view) {
        initSp();
        dialog = WidgetUtils.getMiniLoadingDialog(getActivity(), "登陆中");

        bt_reg = view.findViewById(R.id.bt_reg);
        bt_reg.setOnClickListener(this);
        bt_login = view.findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        cb_login = view.findViewById(R.id.cb_login);
        cb_login2 = view.findViewById(R.id.cb_login2);
        ed_login_admin = view.findViewById(R.id.ed_login_admin);
        ed_login_password = view.findViewById(R.id.ed_login_password);
        cb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cb_login.isChecked()) {
                    cb_login2.setChecked(false);
                    loginTo();
                }
                //Utils_02.log("----------------");
                loginTo();
                //Utils_02.log("----------------");
            }
        });
        cb_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_login2.isChecked()) {
                    cb_login.setChecked(true);
                    loginTo();
                }
                //Utils_02.log("----------------");
                loginTo();
                //Utils_02.log("----------------");
            }
        });
    }

    @Override
    protected void initData() {
        String isSaveAdmin = sp_login.getString("isSaveAdmin", "");
        String isAutoLogin = sp_login.getString("isAutoLogin", "");

        if (isSaveAdmin.equals("yes")) {
            cb_login.setChecked(true);
//            Utils_02.log("进入页面 保存了账户密码:");
//            Utils_02.log("admin:\t" + sp_login.getString("admin", ""));
//            Utils_02.log("password:\t" + sp_login.getString("password", ""));
            ed_login_admin.setText(sp_login.getString("admin", ""));
            ed_login_password.setText(sp_login.getString("password", ""));
        } else {
            // Utils.log("进入页面 无账户密码:");
            cb_login.setChecked(false);
        }
        if (isAutoLogin.equals("yes")) {
            cb_login2.setChecked(true);
            loginLogin2();
        } else {
            cb_login2.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reg:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Ui_reg()).commit();
                break;
            case R.id.bt_login:
                dialog.show();
                Utils.toast("登录");
                loginLogin2();
                break;
        }


    }

    @Override
    protected void title() {

    }

    /**
     * 判断是否自动登录
     * 密码是否保存
     */
    private void loginTo() {
        if (cb_login.isChecked()) {
            sp_login.edit().putString("isSaveAdmin", "yes").apply();
            //Utils_02.log("保存密码");
            cb_login.setChecked(true);
        } else {
            sp_login.edit().putString("isSaveAdmin", "no").apply();
            //Utils_02.log("清除密码");
            cb_login.setChecked(false);
        }
        if (cb_login2.isChecked()) {
            sp_login.edit().putString("isAutoLogin", "yes").apply();
            //Utils_02.log("自动登录");
            cb_login2.setChecked(true);
        } else {
            sp_login.edit().putString("isAutoLogin", "no").apply();
            //Utils_02.log("取消自动登录");
            cb_login2.setChecked(false);
        }
    }

    /**
     * 点击登录逻辑
     */


    private void loginLogin2() {
        String admin_ed = "";
        String password_ed = "";

        Boolean admin_And_password_isNotNull = (!(ed_login_admin.getText().toString().equals(""))) && (!(ed_login_password.getText().toString().equals("")));

        if (admin_And_password_isNotNull) {
            admin_ed = ed_login_admin.getText().toString();
            password_ed = ed_login_password.getText().toString();
            //Utils.log("输入的账户密码为:\tadmin:\t" + admin_ed + "\tpassword:\t" + password_ed);
            sp_login.edit().putString("admin", admin_ed).putString("password", password_ed).apply();
            Utils.toast(admin_ed);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int code = InternetUtils.login(sp_login);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (code == 0) {
                                Utils.toast("登录成功");
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Main()).commit();
                                yesLogin();
                            } else {
                                Utils.toast("登录失败");
                            }
                            dialog.cancel();
                        }
                    });
                }
            }).start();
        } else {
            Utils.toast("请输入账户密码");
        }
    }

    private void yesLogin() {
        String admin = ed_login_admin.getText().toString();
        String password = ed_login_password.getText().toString();
        Db_User user = new Db_User();
        user.setAdmin(admin);
        user.setPassword(password);
        user.save();
        itemToTop2(LitePal.findAll(Db_User.class).size());
    }


    /**
     * 把当前点击的item 置顶
     *
     * @param position
     */
    public static void itemToTop2(int position) {
        if (position == 1) {

        } else {
            Utils.log("增加后:" + position);

            List<Db_User> lists = LitePal.findAll(Db_User.class);
            Db_User city_1 = lists.get(0);
            Db_User city_item = lists.get(position - 1);

            city_item.updateAll("id = ?", city_1.getId() + "");
            city_1.updateAll("id=?", lists.get(position - 1).getId() + "");
        }
    }

    public void initSp() {
        sp_login = getActivity().getSharedPreferences("login", 0);
    }


}
