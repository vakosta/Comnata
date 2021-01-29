package tv.comnata.websocketserver.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
public interface UserClient {
    @RequestMapping("/user/test")
    String test();
}
