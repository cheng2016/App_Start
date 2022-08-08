package com.huiyao.gamecenter.common;

/**
 * Event bus 通用消息类型
 * @param
 */
public class CommonEventMessage{
    /**
     * 1.登录成功 标签
     * tag: 2.微信绑定成功标签
     * 3.提现成功
     */

    private int tag;
    private String message;
    private Object data;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
