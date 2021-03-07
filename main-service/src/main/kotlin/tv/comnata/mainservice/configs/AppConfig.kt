package tv.comnata.mainservice.configs

import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.util.unit.DataSize
import javax.servlet.MultipartConfigElement

@Configuration
@ComponentScan("tv.comnata.mainservice")
class AppConfig {
    @Bean
    fun multipartConfigElement(): MultipartConfigElement {
        val factory = MultipartConfigFactory()
        factory.setMaxFileSize(DataSize.ofGigabytes(7))
        factory.setMaxRequestSize(DataSize.ofGigabytes(7))
        return factory.createMultipartConfig()
    }
}