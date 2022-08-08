package com.huiyao.gamecenter.data.entity;


/***
 * 用户信息实体类
 */
public class UserInfoEntity {
    /**sdk 中 角色互通用的 用户标识 app 中不使用*/
    public String uid;
    /**辉耀渠道 用户 token*/
    public String token;
    /**辉耀渠道 用户id*/
    public String guid;
    /**u9平台userid*/
    public String UserId;

    public String username;
    public String password;
    public String app;
    public String channel;
    public String device;
    public String mobile;
    public String is_create;
    public String age;
    public String open_real_name_auth;
    public String online_time_limit;
    public String register_time;
    public String last_login_time;
    public String is_open_user_monitor;
    public String is_open_monitor;




    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "uid='" + uid + '\'' +
                ", token='" + token + '\'' +
                ", guid='" + guid + '\'' +
                ", UserId='" + UserId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", app='" + app + '\'' +
                ", channel='" + channel + '\'' +
                ", device='" + device + '\'' +
                ", mobile='" + mobile + '\'' +
                ", is_create='" + is_create + '\'' +
                ", age='" + age + '\'' +
                ", open_real_name_auth='" + open_real_name_auth + '\'' +
                ", online_time_limit='" + online_time_limit + '\'' +
                ", register_time='" + register_time + '\'' +
                ", last_login_time='" + last_login_time + '\'' +
                ", is_open_user_monitor='" + is_open_user_monitor + '\'' +
                ", is_open_monitor='" + is_open_monitor + '\'' +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIs_create() {
        return is_create;
    }

    public void setIs_create(String is_create) {
        this.is_create = is_create;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOpen_real_name_auth() {
        return open_real_name_auth;
    }

    public void setOpen_real_name_auth(String open_real_name_auth) {
        this.open_real_name_auth = open_real_name_auth;
    }

    public String getOnline_time_limit() {
        return online_time_limit;
    }

    public void setOnline_time_limit(String online_time_limit) {
        this.online_time_limit = online_time_limit;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getIs_open_user_monitor() {
        return is_open_user_monitor;
    }

    public void setIs_open_user_monitor(String is_open_user_monitor) {
        this.is_open_user_monitor = is_open_user_monitor;
    }

    public String getIs_open_monitor() {
        return is_open_monitor;
    }

    public void setIs_open_monitor(String is_open_monitor) {
        this.is_open_monitor = is_open_monitor;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


}



