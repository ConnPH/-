package vip.ph.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vip.ph.cmn.client.service.DictFeginClient;
import vip.ph.yygh.hosp.repository.HospitalRepository;
import vip.ph.yygh.hosp.service.HospitalService;
import vip.ph.yygh.model.hosp.Hospital;
import vip.ph.yygh.model.vo.hosp.HospitalQueryVo;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {


    @Autowired
    HospitalRepository hospitalRepository;

    @Resource
    private DictFeginClient dictFeginClient;

    @Override
    public void saveHospital(Map<String, Object> paramMap) {

        // 0、把参数的map集合转换为对象
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);

        // 1、判断是否存在相同的数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        // 2、 如果存在，进行更新

        if (hospitalExist != null){
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {
            // 3、如果不存在，进行添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public Hospital getByHoscode(String hoscode) {

        // id 查询医院信息
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);

        return hospital;
    }

    @Override
    public Page<Hospital> selectHospitalPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {

        Pageable pageable = PageRequest.of(page-1,limit);

        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);

        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);


        Example<Hospital> example = Example.of(hospital,matcher);

        Page<Hospital> all = hospitalRepository.findAll(example, pageable);


        all.getContent().stream().forEach(item -> {
            this.setHospitalHosType(item);
        });


        return all;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospitalById(String id) {
        Map<String,Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        result.put("hospital", hospital);
        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;

    }

    @Override
    public String getHospitalName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospital != null){
            return hospital.getHosname();
        }

        return null;
    }

    private Hospital setHospitalHosType(Hospital hospital) {

        String hosTypeString = dictFeginClient.getNameLevel("Hostype", hospital.getHostype());
        String cityString = dictFeginClient.getNameLevel(hospital.getCityCode());
        String districtString = dictFeginClient.getNameLevel(hospital.getDistrictCode());

        hospital.getParam().put("hosTypeString",hosTypeString);
        hospital.getParam().put("fullAddress",hosTypeString + cityString + districtString);

        return hospital;
    }
}
