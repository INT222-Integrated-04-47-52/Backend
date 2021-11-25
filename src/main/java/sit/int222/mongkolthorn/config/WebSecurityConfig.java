package sit.int222.mongkolthorn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sit.int222.mongkolthorn.services.TokenService;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;

    public WebSecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Value("#{'${mongkolthorn.origin.method}'.split(',')}")
    private String[] methodList;
    @Value("#{'${mongkolthorn.origin.host}'.split(',')}")
    private String[] hostList;
    @Value("#{'${mongkolthorn.origin.header}'.split(',')}")
    private String[] headerList;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(
                config -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowCredentials(true);
                    cors.setAllowedOrigins(Arrays.asList(hostList));
                    cors.setAllowedMethods(Arrays.asList(methodList));
                    cors.setAllowedHeaders(Arrays.asList(headerList));

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", cors);

                    config.configurationSource(source);
                }
                ).csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/images","/image/{imageName}").permitAll()
                .antMatchers("/max-closetId").permitAll()
                .antMatchers("/allProducts","/search","/filter/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .and().apply(new TokenFilterConfigurer(tokenService));
    }
}
