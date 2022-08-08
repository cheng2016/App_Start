package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/8/2 12:04
 * @描述:
 */
public class VipDetail {


    /**
     * status : 0
     * message : ok
     * url :
     * data : [{"title":"专属服务","content":"达到条件后，将有专属客服与您联系，您可享受专属客服一对一QQ，语音，电话，短信，邮件等多元化服务渠道带来的贴心服务。使用说明：达到条件后，您将获得专属客服联系方式，我们的客服将会主动与您取得联系。"},{"title":"申诉优先","content":"用户进行被盗申诉操作时，VIP4（含）以上账号将能得到客服人员优先处理。进行被盗申诉。"},{"title":"误操作恢复","content":"用户进行账号修复操作时，VIP4（含）以上账号将能得到客服人员优先处理。进行账号修复。使用说明：进行账号修复操作时会显示当前排队人数。"}]
     * data_list : []
     */

    private int status;
    private String message;
    private String url;
    private List<DataBean> data;
    private List<?> data_list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<?> getData_list() {
        return data_list;
    }

    public void setData_list(List<?> data_list) {
        this.data_list = data_list;
    }

    public static class DataBean {
        /**
         * title : 专属服务
         * content : 达到条件后，将有专属客服与您联系，您可享受专属客服一对一QQ，语音，电话，短信，邮件等多元化服务渠道带来的贴心服务。使用说明：达到条件后，您将获得专属客服联系方式，我们的客服将会主动与您取得联系。
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
