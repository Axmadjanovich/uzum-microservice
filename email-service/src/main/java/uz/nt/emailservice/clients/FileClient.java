package uz.nt.emailservice.clients;

import dto.ResponseDto;
import jdk.jfr.RecordingState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "file-service")
public interface FileClient {
    @GetMapping(name = "file")
    ResponseDto<byte[]> getFileBytes(@RequestParam Integer id,String size);

}
