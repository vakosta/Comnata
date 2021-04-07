package tv.comnata.videoservice.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerConfig : WebMvcConfigurer {
    @Bean
    fun api(): Docket? {
        val contact = Contact(
            "Vladislav Annenkov",
            "https://t.me/Vakosta",
            "v.a.annenkov@ya.ru"
        )

        val vext: List<VendorExtension<*>> = ArrayList()
        val apiInfo = ApiInfo(
            "Comnata Video API",
            "API for main features of the Comnata.",
            "1.0.0",
            "https://something.com",
            contact,
            "MIT",
            "https://something.com",
            vext
        )

        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .forCodeGeneration(true)
            .securitySchemes(listOf(apiKey()) as List<SecurityScheme>?)
            .select()
            .apis(RequestHandlerSelectors.basePackage("tv.comnata.videoservice.controllers"))
            .paths(PathSelectors.any())
            .build()
    }

    override fun addResourceHandlers(registry: org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    private fun apiKey(): ApiKey {
        return ApiKey("authkey", "Authorization", "sdf")
    }
}