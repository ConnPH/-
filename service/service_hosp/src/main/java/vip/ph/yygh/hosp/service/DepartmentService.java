package vip.ph.yygh.hosp.service;

import org.springframework.data.domain.Page;
import vip.ph.yygh.model.hosp.Department;
import vip.ph.yygh.model.vo.hosp.DepartmentQueryVo;

import java.util.Map;

public interface DepartmentService {
    void saveDepartment(Map<String, Object> paramMap);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void departmentRemove(String hoscode, String depcode);
}
