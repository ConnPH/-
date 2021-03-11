package vip.ph.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vip.ph.yygh.common.utils.MD5;
import vip.ph.yygh.common.utils.Result;
import vip.ph.yygh.hosp.service.HospSetService;
import vip.ph.yygh.model.hosp.HospitalSet;
import vip.ph.yygh.model.vo.hosp.HospitalQueryVo;
import vip.ph.yygh.model.vo.hosp.HospitalSetQueryVo;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

@Api(tags = "医院设置信息")
@RestController
@RequestMapping("admin/hosp")
@CrossOrigin
public class HospSetController {


    @Resource
    private HospSetService hospSetService;



    @ApiOperation("查询所有医院配置信息")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospSetService.list();
        return Result.ok(list);

    }

    @ApiOperation("逻辑删除医院配置信息")
    @DeleteMapping("delete/{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean result = hospSetService.removeById(id);
        if (result){
            return Result.ok();
        }else {
           return Result.fail();
        }
    }


    @ApiOperation("分页查询")
    @PostMapping("page/{current}/{limit}")
    public Result page(@RequestBody(required = false) HospitalSetQueryVo hospitalQueryVo, @PathVariable Long current, @PathVariable Long limit){


        Page<HospitalSet> page = new Page<>(current,limit);

        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalQueryVo.getHosname();
        String hoscode = hospitalQueryVo.getHoscode();

        if (!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hospitalQueryVo.getHosname());
        }

        if (!StringUtils.isEmpty(hoscode)){
            wrapper.eq("hoscode",hospitalQueryVo.getHoscode());
        }


        Page<HospitalSet> pages = hospSetService.page(page, wrapper);

        return Result.ok(pages);
    }





    @ApiOperation("添加医院设置")
    @PostMapping("saveHospSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet){

        //设置状态
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));

        boolean save = hospSetService.save(hospitalSet);

        if (save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }


    @ApiOperation("根据id获取医院设置信息")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation("修改医院设置")
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean update = hospSetService.updateById(hospitalSet);

        if (update){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }


    @ApiOperation("批量删除医院设置")
    @DeleteMapping("batchRemoveHospSet")
    public Result batchRemoveHospSet(@RequestBody List<Long> ids){

        hospSetService.removeByIds(ids);

        return Result.ok();
    }


    @ApiOperation("医院设置的锁定和解锁")
    @PutMapping("lockHospSet/{id}/{status}")
    public Result lockHospSet(@PathVariable Long id, @PathVariable Integer status){
        // 根据id查询医院设置信息

        HospitalSet hospitalSet = hospSetService.getById(id);
        hospitalSet.setStatus(status);
        hospSetService.updateById(hospitalSet);
        return Result.ok();
    }


    @ApiOperation("发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){

        HospitalSet hospitalSet = hospSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();

        return Result.ok();

    }




}
