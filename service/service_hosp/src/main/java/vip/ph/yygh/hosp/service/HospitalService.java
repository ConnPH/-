package vip.ph.yygh.hosp.service;

import org.springframework.data.domain.Page;
import vip.ph.yygh.model.hosp.Hospital;
import vip.ph.yygh.model.vo.hosp.HospitalQueryVo;

import java.util.Map;

public interface HospitalService {
    void saveHospital(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);

    Page<Hospital> selectHospitalPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String, Object> getHospitalById(String id);

    String getHospitalName(String hoscode);
}
