package com.kite.okweather.beans;

import org.litepal.crud.LitePalSupport;

public class Db_User extends LitePalSupport {
    int id;
    String admin;
    String password;
    int jifen;

    @Override
    public String toString() {
        return "Db_User{" +
                "id=" + id +
                ", admin='" + admin + '\'' +
                ", password='" + password + '\'' +
                ", jifen=" + jifen +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getJifen() {
        return jifen;
    }

    public void setJifen(int jifen) {
        this.jifen = jifen;
    }
}
