package tv.comnata.videoservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("main-service")
public interface MainClient {
    @RequestMapping("/main/createVideo")
    String createVideo(String videoUuid);

    @RequestMapping("/main/setVideoProgress")
    String setVideoProgress(@RequestParam String videoUuid, @RequestParam int videoProgress);
}
