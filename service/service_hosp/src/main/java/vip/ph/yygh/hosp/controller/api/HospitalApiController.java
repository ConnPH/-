package vip.ph.yygh.hosp.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ph.yygh.common.exception.YyghException;
import vip.ph.yygh.common.helper.HttpRequestHelper;
import vip.ph.yygh.common.utils.MD5;
import vip.ph.yygh.common.utils.Result;
import vip.ph.yygh.common.utils.ResultCodeEnum;
import vip.ph.yygh.hosp.service.DepartmentService;
import vip.ph.yygh.hosp.service.HospSetService;
import vip.ph.yygh.hosp.service.HospitalService;
import vip.ph.yygh.hosp.service.ScheduleService;
import vip.ph.yygh.model.hosp.Department;
import vip.ph.yygh.model.hosp.Hospital;
import vip.ph.yygh.model.hosp.Schedule;
import vip.ph.yygh.model.vo.hosp.DepartmentQueryVo;
import vip.ph.yygh.model.vo.hosp.ScheduleQueryVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "APi")
@RestController
@RequestMapping("/api/hosp")
public class HospitalApiController {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    HospSetService hospSetService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //TODO 签名校验

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    //查询排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //科室编号
        String depcode = (String)paramMap.get("depcode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //上传排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //TODO 签名校验
        scheduleService.save(paramMap);
        return Result.ok();
    }


    @ApiOperation("删除接口")
    @PostMapping("/department/remove")
    public Result departmentRemove(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
//必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
//必填
        String depcode = (String)paramMap.get("depcode");

        departmentService.departmentRemove(hoscode, depcode);
        return Result.ok();

    }


    @ApiOperation("查询科室")
    @PostMapping("department/list")
    public Result departmentList(HttpServletRequest request){
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
//必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
//非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));


        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        departmentQueryVo.setDepcode(depcode);
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);

    }


    @ApiOperation("上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取传递过来的医院的信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospSetService.getSignKey(hoscode);

        String signKeyMD5 = MD5.encrypt(signKey);

        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.saveDepartment(paramMap);

        return Result.ok();
    }


    @ApiOperation("上传医院")
    @PostMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request){
        //获取传递过来的医院的信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospSetService.getSignKey(hoscode);

        String signKeyMD5 = MD5.encrypt(signKey);

        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        String logoData = (String) paramMap.get("logoData");
        logoData.replaceAll(" ",  "+");
        paramMap.put("logoData",logoData);
        hospitalService.saveHospital(paramMap);
        return Result.ok();
    }



    @ApiOperation("查询医院")
    @PostMapping("hospital/show")
    public Result HospitalShow(HttpServletRequest request){
        //获取传递过来的医院的信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospSetService.getSignKey(hoscode);

        String signKeyMD5 = MD5.encrypt(signKey);

        if (!sign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        // 根据医院编号进行查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);

    }

}
