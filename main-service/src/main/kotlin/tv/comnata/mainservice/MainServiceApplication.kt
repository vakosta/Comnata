package tv.comnata.mainservice

import io.dekorate.kubernetes.annotation.ImagePullPolicy
import io.dekorate.kubernetes.annotation.KubernetesApplication
import io.dekorate.kubernetes.annotation.Probe
import io.dekorate.kubernetes.annotation.ServiceType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@KubernetesApplication(
    livenessProbe = Probe(httpActionPath = "/"),
    readinessProbe = Probe(httpActionPath = "/"),
    serviceType = ServiceType.NodePort,
    imagePullPolicy = ImagePullPolicy.Always
)
class MainServiceApplication

fun main(args: Array<String>) {
    runApplication<MainServiceApplication>(*args)
}
