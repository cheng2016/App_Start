package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/17 11:08
 * @描述:
 */
public class GameGiftBagData {


    /**
     * status : 0
     * message : ok
     * url :
     * data : [{"product_id":14,"last_create_time":1656384900,"product_name":"弓箭猎人","icon":"http://res.hyhygame.com/picture/20220628/62ba68d694ced.png","introduction":"原始部落的原始生活","package_name":"com.gjlr.hyhy","download_url":"https://apk1.hyhygame.com/2011/default/gongjianlieren.496750.apk","gift":[{"product_id":14,"id":62,"name":"头像框礼包","content":"魔晶原石*10 部落印章*10 珍珠*188 头像框*1","sort_index":1,"create_time":1656384820,"start_time":1656345600,"end_time":1687881600,"code":"feiyuyouxi","is_receive":1,"recommend_id":"gift_62"},{"product_id":14,"id":63,"name":"进阶礼包","content":"魔晶原石*10 部落印章*10 珍珠*188 狩猎纹章*48","sort_index":2,"create_time":1656384842,"start_time":1656345600,"end_time":1687881600,"code":"yzycj888","is_receive":1,"recommend_id":"gift_63"},{"product_id":14,"id":64,"name":"专属礼包1","content":"魔晶原石*2 狩猎纹章*2 金币*10000","sort_index":3,"create_time":1656384866,"start_time":1656345600,"end_time":1687881600,"code":"dalao666","is_receive":1,"recommend_id":"gift_64"},{"product_id":14,"id":65,"name":"专属礼包2","content":"猎人钥匙*2 鲜花礼物*2 金币*10000 扫荡券*3","sort_index":4,"create_time":1656384884,"start_time":1656345600,"end_time":1687881600,"code":"","is_receive":0,"recommend_id":"gift_65"},{"product_id":14,"id":66,"name":"专属礼包3","content":"珍珠*50 狩猎纹章*5 金币*10000","sort_index":5,"create_time":1656384900,"start_time":1656345600,"end_time":1687881600,"code":"","is_receive":0,"recommend_id":"gift_66"}]}]
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
         * product_id : 14
         * last_create_time : 1656384900
         * product_name : 弓箭猎人
         * icon : http://res.hyhygame.com/picture/20220628/62ba68d694ced.png
         * introduction : 原始部落的原始生活
         * package_name : com.gjlr.hyhy
         * download_url : https://apk1.hyhygame.com/2011/default/gongjianlieren.496750.apk
         * gift : [{"product_id":14,"id":62,"name":"头像框礼包","content":"魔晶原石*10 部落印章*10 珍珠*188 头像框*1","sort_index":1,"create_time":1656384820,"start_time":1656345600,"end_time":1687881600,"code":"feiyuyouxi","is_receive":1,"recommend_id":"gift_62"},{"product_id":14,"id":63,"name":"进阶礼包","content":"魔晶原石*10 部落印章*10 珍珠*188 狩猎纹章*48","sort_index":2,"create_time":1656384842,"start_time":1656345600,"end_time":1687881600,"code":"yzycj888","is_receive":1,"recommend_id":"gift_63"},{"product_id":14,"id":64,"name":"专属礼包1","content":"魔晶原石*2 狩猎纹章*2 金币*10000","sort_index":3,"create_time":1656384866,"start_time":1656345600,"end_time":1687881600,"code":"dalao666","is_receive":1,"recommend_id":"gift_64"},{"product_id":14,"id":65,"name":"专属礼包2","content":"猎人钥匙*2 鲜花礼物*2 金币*10000 扫荡券*3","sort_index":4,"create_time":1656384884,"start_time":1656345600,"end_time":1687881600,"code":"","is_receive":0,"recommend_id":"gift_65"},{"product_id":14,"id":66,"name":"专属礼包3","content":"珍珠*50 狩猎纹章*5 金币*10000","sort_index":5,"create_time":1656384900,"start_time":1656345600,"end_time":1687881600,"code":"","is_receive":0,"recommend_id":"gift_66"}]
         */

        private int product_id;
        private int last_create_time;
        private String product_name;
        private String icon;
        private String introduction;
        private String package_name;
        private String download_url;
        private String recommend_id;
        private List<GiftBean> gift;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getLast_create_time() {
            return last_create_time;
        }

        public void setLast_create_time(int last_create_time) {
            this.last_create_time = last_create_time;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public List<GiftBean> getGift() {
            return gift;
        }

        public void setGift(List<GiftBean> gift) {
            this.gift = gift;
        }

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }


        public static class GiftBean {
            /**
             * product_id : 14
             * id : 62
             * name : 头像框礼包
             * content : 魔晶原石*10 部落印章*10 珍珠*188 头像框*1
             * sort_index : 1
             * create_time : 1656384820
             * start_time : 1656345600
             * end_time : 1687881600
             * code : feiyuyouxi
             * is_receive : 1
             * recommend_id : gift_62
             */

            private int product_id;
            private int id;
            private String name;
            private String content;
            private int sort_index;
            private int create_time;
            private int start_time;
            private int end_time;
            private String code;
            private int is_receive;
            private String recommend_id;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getSort_index() {
                return sort_index;
            }

            public void setSort_index(int sort_index) {
                this.sort_index = sort_index;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
                this.end_time = end_time;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getIs_receive() {
                return is_receive;
            }

            public void setIs_receive(int is_receive) {
                this.is_receive = is_receive;
            }

            public String getRecommend_id() {
                return recommend_id;
            }

            public void setRecommend_id(String recommend_id) {
                this.recommend_id = recommend_id;
            }


        }
    }
}
