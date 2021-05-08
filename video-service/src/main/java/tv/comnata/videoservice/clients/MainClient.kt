package tv.comnata.videoservice.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("main-service")
interface MainClient {
    @RequestMapping(value = ["/main/createVideo"], method = [RequestMethod.PUT])
    fun createVideo(@RequestParam videoUuid: String?): String?

    @RequestMapping(value = ["/main/setVideoProgress"], method = [RequestMethod.POST])
    fun setVideoProgress(@RequestParam videoUuid: String?, @RequestParam videoProgress: Int): String?
}
