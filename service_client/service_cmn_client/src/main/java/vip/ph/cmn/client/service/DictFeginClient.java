package vip.ph.cmn.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-cmn")
public interface DictFeginClient {

    @GetMapping("/admin/cmn/dict/getNameLevel/{dictCode}/{value}")
    public String getNameLevel(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    @GetMapping("/admin/cmn/dict/getNameLevel/{value}")
    public String getNameLevel(@PathVariable("value") String value);
}
