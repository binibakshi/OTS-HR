package teachers.biniProject.Controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Security.MyUserDetailsService;
import teachers.biniProject.Security.models.AuthenticationRequest;
import teachers.biniProject.Security.models.AuthenticationResponse;
import teachers.biniProject.Security.models.User;
import teachers.biniProject.Security.util.JwtUtil;

import java.util.Date;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@NotNull @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/authenticate/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(@RequestParam(name = "token") String token,
                                                        @RequestParam(name = "username") String username) throws Exception {
        try {
            if (jwtTokenUtil.extractExpiration(token).before(new Date())) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                final String jwt = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            }
        } catch (ExpiredJwtException e) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException e) {
            throw new Exception("Error in create token", e);
        } catch (Exception e) {
            throw new Exception("Some Error accrued ", e);
        }
        return null;
    }

    @RequestMapping(value = "/authenticate/isExpired", method = RequestMethod.GET)
    public boolean isTokenExpired(@RequestParam(name = "token") String token) throws Exception {
        try {
            jwtTokenUtil.extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (BadCredentialsException e) {
            throw new Exception("Error in create token", e);
        } catch (Exception e) {
            throw new Exception("Some Error accrued ", e);
        }
        return false;
    }


    @RequestMapping(value = "/getMossad", method = RequestMethod.GET)
    public int getUserMossad(@RequestParam(name = "username") String username) {
        return this.myUserDetailsService.getUserMossad(username);
    }

    @RequestMapping(value = "/auth/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.myUserDetailsService.findAll();
    }

    @RequestMapping(value = "/auth/save", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user) {
        return this.myUserDetailsService.save(user);
    }

    @RequestMapping(value = "/auth/update", method = RequestMethod.POST)
    public User updateUser(@RequestBody User user) {
        return this.myUserDetailsService.updateUser(user);
    }

    @RequestMapping(value = "/auth/delete", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam(name = "username") String username) {
        this.myUserDetailsService.deleteByUsername(username);
    }
}
