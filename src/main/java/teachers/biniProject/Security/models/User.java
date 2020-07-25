package teachers.biniProject.Security.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles", nullable = false)
    private String roles;

    @Column(name = "mossad_id")
    private Integer mossadId;

    public User() {
        super();
    }

    public User(String userName, String password, String roles, Integer mossadId) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.mossadId = mossadId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String empId) {
        this.userName = empId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getMossadId() {
        return mossadId;
    }

    public void setMossadId(Integer mossadId) {
        this.mossadId = mossadId;
    }
}
