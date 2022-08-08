package com.huiyao.gamecenter.data.entity;

/**
 * 邀请好友页面数据模板
 */
public class InviteFriendsData {


    /**
     * invite_code : PHU6LGXY
     * jump_url : http://ad.hyhygame.com/auto/common_link.html?and=499860
     * download_url : https://apk1.hyhygame.com/2061/default/shequapp.499860.apk
     * img : http://res.huiyaohuyu.com//picture//20220706//62c55dd29f7ff.png
     * content : 邀请规则：1、每邀请一位好友注册APP即可获得500积分奖励  2、好友获得积分，邀请人可获得其15%的积分奖励
     */

    private String invite_code;
    private String jump_url;
    private String download_url;
    private String img;
    private String content;
    private int is_bind;
    private String username;
    private String avatar;


    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(int is_bind) {
        this.is_bind = is_bind;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
