package vip.ph.yygh.hosp.service;

import vip.ph.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    void saveHospital(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);
}
