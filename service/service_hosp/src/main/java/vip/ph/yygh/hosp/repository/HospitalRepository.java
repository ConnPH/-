package vip.ph.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vip.ph.yygh.model.hosp.Hospital;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    Hospital getHospitalByHoscode(String hoscode);
}
