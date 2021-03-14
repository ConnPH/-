package vip.ph.yygh.cmn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import vip.ph.yygh.cmn.service.DictService;
import vip.ph.yygh.common.utils.Result;
import vip.ph.yygh.model.cmn.Dict;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "数据字典")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {
    @Resource
    private DictService dictService;



    @GetMapping("exportData")
    public void exportData(HttpServletResponse response){
        dictService.exportData(response);
    }

    @ApiOperation("根据id查询子数据列表 递归")
    @GetMapping("findAllChildData/{id}")
    public Result findAllChildData(@PathVariable Long id){

       List<Dict> list =  dictService.findChildData(id);

        return Result.ok(list);
    }



    @GetMapping("/getNameLevel/{dictCode}/{value}")
    public String getNameLevel(@PathVariable String dictCode, @PathVariable String value){
        String dictName = dictService.getNameLevel(dictCode,value);
        return dictName;
    }

    @GetMapping("/getNameLevel/{value}")
    public String getNameLevel(@PathVariable String value){
        String dictName = dictService.getNameLevel("",value);
        return dictName;
    }

    // 根据dictcode查询子节点
    @ApiOperation(value = "根据dictcode查询子节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode){
        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return Result.ok(dictList);
    }


}
