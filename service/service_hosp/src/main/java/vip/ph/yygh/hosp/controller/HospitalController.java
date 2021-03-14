package vip.ph.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vip.ph.yygh.common.utils.Result;
import vip.ph.yygh.hosp.service.HospitalService;
import vip.ph.yygh.model.hosp.Hospital;
import vip.ph.yygh.model.vo.hosp.HospitalQueryVo;

import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;




    // 条件查询带分页
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo){

        Page<Hospital> pageModel = hospitalService.selectHospitalPage(page,limit,hospitalQueryVo);

        return Result.ok(pageModel);
    }

    // 更新医院的上线状态
    @ApiOperation(value = "更新医院的上线状态")
    @GetMapping("updateHospitalStatus/{id}/{status}")
    public Result updateHospitalStatus(@PathVariable String id, @PathVariable Integer status){

        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    @ApiOperation(value = "获取医院详情")
    @GetMapping("show/{id}")
    public Result show(@PathVariable String id) {
          Map<String, Object> map = hospitalService.getHospitalById(id);
        return Result.ok(map);
    }


}
