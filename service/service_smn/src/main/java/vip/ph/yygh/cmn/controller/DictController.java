package vip.ph.yygh.cmn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ph.yygh.cmn.service.DictService;
import vip.ph.yygh.common.utils.Result;
import vip.ph.yygh.model.cmn.Dict;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "数据字典")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {
    @Resource
    private DictService dictService;


    @ApiOperation("根据id查询子数据列表 递归")
    @GetMapping("findAllChildData/{id}")
    public Result findAllChildData(@PathVariable Long id){

       List<Dict> list =  dictService.findChildData(id);

        return Result.ok(list);
    }


}
