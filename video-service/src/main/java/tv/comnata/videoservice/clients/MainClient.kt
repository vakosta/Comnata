package tv.comnata.videoservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("main-service")
public interface MainClient {
    @RequestMapping(value = "/main/createVideo", method = RequestMethod.PUT)
    String createVideo(@RequestParam String videoUuid);

    @RequestMapping(value = "/main/setVideoProgress", method = RequestMethod.POST)
    String setVideoProgress(@RequestParam String videoUuid, @RequestParam int videoProgress);
}
