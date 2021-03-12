package vip.ph.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.ph.yygh.hosp.repository.HospitalRepository;
import vip.ph.yygh.hosp.service.HospitalService;
import vip.ph.yygh.model.hosp.Hospital;

import java.util.Date;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {


    @Autowired
    HospitalRepository hospitalRepository;

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
}
