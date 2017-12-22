package in.museon.assignment.data.rest.entity.response;

/**
 * @author dev.cobb
 * @version 1.0
 * @since 22 may 2017
 */
public class Response_Upload {
    private  String status;
    private  String msg;

    public Response_Upload(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Response_Upload() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response_Upload{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
