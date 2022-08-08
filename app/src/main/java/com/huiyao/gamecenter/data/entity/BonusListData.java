package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/16 16:59
 * @描述:
 */
public class BonusListData {

    /**
     * status : 0
     * message : ok
     * url :
     * data : [{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":800,"id":8,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":7,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":6,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":5,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":4,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":3,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png","product_name":"勇者养成记","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":2,"number":1,"download_url":"https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk"},{"icon":"http://dabao.huiyaohuyu.com/upload/package/20210109/202101091822005ff983c8cf319.png","product_name":"超强射手","introduction":"畅快射击战斗体验","package_name":"com.cqss1.hyhy","amount":1600,"id":1,"number":1,"download_url":"https://apk1.hyhygame.com/1738/default/chaoqiangsheshou.427078.apk"}]
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
         * icon : http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png
         * product_name : 勇者养成记
         * introduction : 畅快射击战斗体验
         * package_name : com.cqss1.hyhy
         * amount : 800
         * id : 8
         * number : 1
         * download_url : https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk
         */

        private String icon;
        private String product_name;
        private String introduction;
        private String package_name;
        private String amount;
        private int id;
        private int number;
        private String download_url;
        //广告位id 标识不同展示位置
        private String recommend_id;


        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }
    }
}
