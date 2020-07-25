package teachers.biniProject.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teachers.biniProject.Exeption.GenericException;
import teachers.biniProject.Security.models.MyUserDetails;
import teachers.biniProject.Security.models.User;
import teachers.biniProject.Security.util.UserRepository;

import java.util.ArrayList;
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
}