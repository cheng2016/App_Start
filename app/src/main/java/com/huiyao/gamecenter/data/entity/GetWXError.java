package com.huiyao.gamecenter.data.entity;

public class GetWXError {
    /**
     * errcode : 40003
     * errmsg : invalid openid
     */
    public GetWXError() {
    }

    public GetWXError(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
