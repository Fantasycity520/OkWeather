package com.kite.okweather.ui.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.kite.okweather.R;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.InternetUtils;
import com.kite.okweather.utils.Utils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;

import java.util.regex.Pattern;

public class Fg_Ui_reg extends BaseFragment {

    EditText ed_reg_admin, ed_reg_email, ed_reg_password, ed_reg_password2;
    Button bt_reg_02, bt_cz;
    private MiniLoadingDialog dialog;


    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    //主界面获取焦点
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    BaseActivity.activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Ui_login()).commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.fg_reg;
    }

    @Override
    protected void initView(View view) {
        dialog = WidgetUtils.getMiniLoadingDialog(getActivity(),"注册中");

        ed_reg_admin = view.findViewById(R.id.ed_reg_admin);
        ed_reg_email = view.findViewById(R.id.ed_reg_email);
        ed_reg_password = view.findViewById(R.id.ed_reg_password);
        ed_reg_password2 = view.findViewById(R.id.ed_reg_password2);

        bt_reg_02 = view.findViewById(R.id.bt_reg_02);
        bt_reg_02.setOnClickListener(this);
        bt_cz = view.findViewById(R.id.bt_cz);
        bt_cz.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initSp() {

    }


    int dataLegal() {
        int res = -1;
        String p_name = "^[a-zA-Z][a-zA-Z0-9]{3,15}$";
        String p_password = "^[\\w@\\$\\^!~,.\\*]{8,16}+$";
        String p_email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        boolean is_b_name = Pattern.matches(p_name, ed_reg_admin.getText().toString());
        boolean is_b_password = Pattern.matches(p_password, ed_reg_password2.getText().toString());
        boolean is_b_email = Pattern.matches(p_email, ed_reg_email.getText().toString());

        boolean name_notNull = !ed_reg_admin.getText().toString().equals("");
        boolean email_notNull = !ed_reg_email.getText().toString().equals("");
        boolean password_notNull = !ed_reg_password2.getText().toString().equals("");

//        Utils_02.log(is_b_name + "\t" + is_b_email + "\t" + is_b_password + "");
//        Utils_02.log(name_notNull + "\t" + email_notNull + "\t" + password_notNull + "");

        if (!ed_reg_password.getText().toString().equals(ed_reg_password2.getText().toString())) {
            ed_reg_password.setText("");
            ed_reg_password2.setText("");
            return 1;
        }
        if (is_b_name && is_b_password && is_b_email) {
            res = 0;
        } else {
            if (!is_b_name) {
                ed_reg_admin.setText("");
                return 2;
            }
            if (!is_b_email) {
                ed_reg_email.setText("");
                return 3;
            }
            if (!is_b_password) {
                ed_reg_password.setText("");
                ed_reg_password2.setText("");
                return 4;
            }
        }
        return res;
    }


    //注册逻辑
    String reg() {
        String ret = "";
        String admin = ed_reg_admin.getText().toString(),
                password = ed_reg_password.getText().toString(),
                email = ed_reg_email.getText().toString();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int reg = InternetUtils.reg(admin, password, email);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (reg) {
                            case 0:
                                Utils.toast("注册成功");
                                BaseActivity.activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new Fg_Ui_login()).commit();
                                break;
                            case 3:
                                Utils.toast("用户已经存在");
                                break;
                            default:
                                Utils.toast("注册失败");
                                break;
                        }
                        dialog.cancel();
                    }
                });
            }
        });
        thread.start();
        return ret;
    }

    @Override
    protected void title() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_reg_02:
                if (dataLegal() == 0) {
                    String admin = ed_reg_admin.getText().toString(),
                            password = ed_reg_password2.getText().toString(),
                            email = ed_reg_email.getText().toString();
                    Utils.log("待提交的注册信息:\t" + "+admin:\t" + admin + "\tpassword:\t" + password + "\temail:\t" + email + "\n");
                    dialog.show();
                    reg();
                } else {
                    int dataLegal = dataLegal();
                    switch (dataLegal) {
                        case 0:
                            Utils.toast("正确即将注册");
                            break;
                        case -1:
                            Utils.toast("输入错误");
                            break;
                        case 1:
                            Utils.toast("两次输入密码不一致");
                            break;
                        case 2:
                            Utils.toast("用户名不合法");
                            break;
                        case 3:
                            Utils.toast("邮箱不合法");
                            break;
                        case 4:
                            Utils.toast("密码不合法");
                            break;
                        default:
                            break;
                    }
                    Utils.toast("输入不合法");
                }
                break;
            case R.id.bt_cz:
                ed_reg_admin.setText("");
                ed_reg_email.setText("");
                ed_reg_password.setText("");
                ed_reg_password2.setText("");
                Utils.toast("重新输入");
                break;
        }
    }
}
