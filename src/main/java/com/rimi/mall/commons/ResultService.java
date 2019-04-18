package com.rimi.mall.commons;

/**
 * 返回数据结果
 *
 * @author admin
 * @date 2019-04-12 16:48
 */
public class ResultService implements Result {

    private int code = 200;
    private String msg = "";
    private Object data;

    public ResultService() {
    }

    public ResultService(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultServiceBuilder builder() {
        return new ResultServiceBuilder();
    }

    public static ResultServiceBuilder success() {
        return of(ResultCode.SUCCESS);
    }

    public static ResultServiceBuilder failure(){
        return of(ResultCode.FAILURE);
    }

    public static ResultServiceBuilder of(ResultCode resultCode){
        return builder().codeMsg(resultCode);
    }


    public static class ResultServiceBuilder {

        private int code;
        private String msg;
        private Object data;

        ResultServiceBuilder() {
        }

        public ResultServiceBuilder codeMsg(ResultCode resultCode) {
            return code(resultCode.getCode()).msg(resultCode.getMsg());
        }

        public ResultServiceBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public ResultServiceBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ResultServiceBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResultService build() {
            return new ResultService(this.code, this.msg, this.data);
        }

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

}
