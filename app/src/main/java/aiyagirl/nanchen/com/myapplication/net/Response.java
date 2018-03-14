package aiyagirl.nanchen.com.myapplication.net;

public class Response<T> {
    public static final int SUCCESS = 200;
    public static final int NEED_LOGIN = 401;
    public static final int ERROR_NET = 400;
    private int code;
    private String msg;
    private T body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


}
