package in.museon.assignment.data.rest.entity.response;

import java.util.ArrayList;

import in.museon.assignment.data.domian.User;

/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class Response_Profile {
    private String status;
    private ArrayList<User> list;
    private String msg;

    public Response_Profile() {
    }

    public Response_Profile(String status, ArrayList<User> list, String msg) {
        this.status = status;
        this.list = list;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<User> getList() {
        return list;
    }

    public void setList(ArrayList<User> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Responce_reg{" +
                "status='" + status + '\'' +
                ", list=" + list +
                ", msg='" + msg + '\'' +
                '}';
    }
}
