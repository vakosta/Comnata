package tv.comnata.mainservice.configs

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

@Configuration
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {
    @Bean
    fun oauth2RestTemplate(
        @Qualifier("oauth2ClientContext") oauth2ClientContext: OAuth2ClientContext?,
        details: OAuth2ProtectedResourceDetails?
    ): OAuth2RestTemplate {
        return OAuth2RestTemplate(details, oauth2ClientContext)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()

            .anyRequest().permitAll()
    }
}