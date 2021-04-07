package tv.comnata.videoservice;

import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Probe;
import io.dekorate.kubernetes.annotation.ServiceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableFeignClients
@EnableDiscoveryClient
@EnableResourceServer
@SpringBootApplication
@KubernetesApplication(
        livenessProbe = @Probe(httpActionPath = "/"),
        readinessProbe = @Probe(httpActionPath = "/"),
        serviceType = ServiceType.NodePort,
        imagePullPolicy = ImagePullPolicy.IfNotPresent
)
public class VideoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoServiceApplication.class, args);
    }
}
