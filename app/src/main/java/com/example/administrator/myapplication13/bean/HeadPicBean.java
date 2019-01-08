package com.example.administrator.myapplication13.bean;

public class HeadPicBean {

    private String headPath;
    private String message;
    private String status;
    private String SUCCESS_PARAMETER="0000";
    public boolean isSuccess(){
        return status.equals(SUCCESS_PARAMETER);
    }
    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
