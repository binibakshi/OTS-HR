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

    // 999 for administration
    @Column(name = "mossad_id", nullable = false)
    private Integer mossadId;

    @Column(name = "firstname", length = 55)
    private String firstName;

    @Column(name = "lastname", length = 55)
    private String lastName;

    public User() {
        super();
    }

    public User(String userName, String password, String roles, Integer mossadId, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.mossadId = mossadId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getMossadId() {
        return mossadId;
    }

    public void setMossadId(Integer mossadId) {
        this.mossadId = mossadId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
