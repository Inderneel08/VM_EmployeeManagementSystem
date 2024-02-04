package com.example.Vm_Employee_Management.Security;

import java.io.IOException;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Vm_Employee_Management.CustomAccessDeniedHandler.CustomAccessDeniedHandler;
import com.example.Vm_Employee_Management.oAuth.CustomOAuth2UserService;
import com.example.Vm_Employee_Management.oAuth.OAuth2LoginSuccessHandler;
import com.nimbusds.jose.util.Base64;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
public class ProjectSecurityConfig {
    
    // @Autowired
    // private UserDetailsService userDetailsService;

    // @Value("${client-id}")
    // private String googleClientId;

    // @Value("${client-secret}")
    // private String googleClientSecret;

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    public UserDetailsService userDetailsService()
    {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        
        httpSecurity
        .authorizeHttpRequests()
        .requestMatchers("/css/**","/js/**","/images/**").permitAll()
        .requestMatchers("/create_user/**").hasAuthority("ADMIN")
        .requestMatchers("/updateUserRole/**").hasAuthority("ADMIN")
        .requestMatchers("/create_vm/**").hasAuthority("ADMIN")
        .requestMatchers("/oauth2/**").permitAll()
        .requestMatchers("/login/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/home")
        .failureHandler(authenticationFailureHandler)
        .permitAll()
    .and()
    .oauth2Login()
        .loginPage("/login")
        .userInfoEndpoint().userService(oAuth2UserService)
        .and()
        .successHandler(oAuth2LoginSuccessHandler)
        // .defaultSuccessUrl("/home")
        .and()
    .logout()
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .permitAll();

        httpSecurity.addFilterBefore(new RedirectToHomeFilter(), UsernamePasswordAuthenticationFilter.class);

        return(httpSecurity.build());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler()
    {
        return new CustomAccessDeniedHandler(); 
    }

    private static class RedirectToHomeFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if((authentication!=null && authentication.isAuthenticated()) && (("/login".equals(request.getRequestURI()))||("/".equals(request.getRequestURI()))) && !("/updateUserRole".equals(request.getRequestURI()))){
                response.sendRedirect("/home");
            }
            else{
                filterChain.doFilter(request, response);
            }
        }

    }

}
