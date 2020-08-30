package teachers.biniProject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Security.models.MyUserDetails;
import teachers.biniProject.Security.models.User;
import teachers.biniProject.Security.util.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {

        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new GenericException("שגיאה בשם משתמש או סיסמה“"));

        return user.map(MyUserDetails::new).get();
    }

    public User save(User user) {
        if (this.userRepository.existsById(user.getUserName())) {
            throw new GenericException("שם משתמש תפוס בחר שם משתמש אחר");
        }
        user.setRoles(user.getMossadId() != 999 ? "ROLE_USER" : "ROLE_ADMIN");
        return this.userRepository.save(user);
    }

    public User updateUser(User user) {
        if (!this.userRepository.existsById(user.getUserName())) {
            throw new GenericException("לא קיים משתמש עבור " + user.getUserName());
        }
        user.setRoles(user.getMossadId() != 999 ? "ROLE_USER" : "ROLE_ADMIN");
        return this.userRepository.save(user);
    }

    public int getUserMossad(String username) {
        return this.userRepository.findByUserName(username).orElse(null).getMossadId();
    }

    public void deleteByUsername(@RequestParam(name = "username") String username) {
        this.userRepository.deleteById(username);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}