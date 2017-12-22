package in.museon.assignment.data.domian;


public class User {

    private String id;

    private String register_time;

    private String status;

    private String expiration;

    private String name;

    private String profile_pic;

    private String mobile;

    public User(String id, String register_time, String status, String expiration, String name, String profile_pic, String mobile) {
        this.id = id;
        this.register_time = register_time;
        this.status = status;
        this.expiration = expiration;
        this.name = name;
        this.profile_pic = profile_pic;
        this.mobile = mobile;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
