package vip.ph.yygh.hosp.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.ph.yygh.common.utils.Result;
import vip.ph.yygh.hosp.service.DepartmentService;
import vip.ph.yygh.model.vo.hosp.DepartmentVo;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
public class DepartmentController {



    @Autowired
    DepartmentService departmentService;

    //根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }




}
