package com.wzq.jz_app.model.bean;

/**
 * 作者：wzq
 * 邮箱：wang_love152@163.com
 * 通用javabean
 */

public class BaseBean {
    /**
     * status : 100
     * message : 成功！
     */

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}