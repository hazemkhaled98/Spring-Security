package utils;

import com.example.security.demo.entities.User;
import com.example.security.demo.security.userdetails.SecurityUser;
import com.example.security.demo.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;


class JWTGenerator {
    public static void main(String[] args) {
        UserDetails userDetails = new SecurityUser(User.builder()
                .username("admin")
                .password("admin")
                .authorities("ROLE_ADMIN")
                .build());
        String token = JwtUtil.generateToken(userDetails);
        System.out.println("Generated Token: " + token);
    }
}
