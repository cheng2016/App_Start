package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/19 14:11
 * @描述:
 */
public class ScoreData {

    /**
     * status : 0
     * message : ok
     * url :
     * data : {"list":[{"id":11,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-19 15:28:59","type_name":"签到","user_score":74,"score_type":2},{"id":6,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-19 08:40:44","type_name":"签到","user_score":74,"score_type":2},{"id":5,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-18 15:12:40","type_name":"签到","user_score":74,"score_type":2},{"id":2,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-15 10:14:56","type_name":"签到","user_score":74,"score_type":2},{"id":1,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-14 18:19:54","type_name":"签到","user_score":74,"score_type":2}],"user_score":74,"sum_score":74}
     * data_list : []
     */

    private int status;
    private String message;
    private String url;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * list : [{"id":11,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-19 15:28:59","type_name":"签到","user_score":74,"score_type":2},{"id":6,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-19 08:40:44","type_name":"签到","user_score":74,"score_type":2},{"id":5,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-18 15:12:40","type_name":"签到","user_score":74,"score_type":2},{"id":2,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-15 10:14:56","type_name":"签到","user_score":74,"score_type":2},{"id":1,"user_id":25480586,"score":"+8积分","type":"0","create_time":"2022-07-14 18:19:54","type_name":"签到","user_score":74,"score_type":2}]
         * user_score : 74
         * sum_score : 74
         */

        private String user_score;
        private String sum_score;
        private List<ListBean> list;

        public String getUser_score() {
            return user_score;
        }

        public void setUser_score(String user_score) {
            this.user_score = user_score;
        }

        public String getSum_score() {
            return sum_score;
        }

        public void setSum_score(String sum_score) {
            this.sum_score = sum_score;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 11
             * user_id : 25480586
             * score : +8积分
             * type : 0
             * create_time : 2022-07-19 15:28:59
             * type_name : 签到
             * user_score : 74
             * score_type : 2
             */

            private int id;
            private String user_id;
            private String score;
            private String type;
            private String create_time;
            private String type_name;
            private String user_score;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getUser_score() {
                return user_score;
            }

            public void setUser_score(String user_score) {
                this.user_score = user_score;
            }
        }
    }
}
