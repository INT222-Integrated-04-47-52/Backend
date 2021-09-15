package sit.int222.mongkolthorn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Value("#{'${mongkolthorn.origin.method}'.split(',')}")
    private String[] methodList;
    @Value("#{'${mongkolthorn.origin.host}'.split(',')}")
    String[] hostList;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(hostList).allowedMethods(methodList);
    }
}
