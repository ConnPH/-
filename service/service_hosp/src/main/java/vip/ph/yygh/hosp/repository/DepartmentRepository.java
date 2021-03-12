package vip.ph.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vip.ph.yygh.model.hosp.Department;

public interface DepartmentRepository extends MongoRepository<Department,String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
