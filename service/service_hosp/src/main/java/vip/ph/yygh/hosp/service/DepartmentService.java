package vip.ph.yygh.hosp.service;

import org.springframework.data.domain.Page;
import vip.ph.yygh.model.hosp.Department;
import vip.ph.yygh.model.vo.hosp.DepartmentQueryVo;
import vip.ph.yygh.model.vo.hosp.DepartmentVo;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    void saveDepartment(Map<String, Object> paramMap);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void departmentRemove(String hoscode, String depcode);

    //根据医院编号，查询医院所有科室列表
    List<DepartmentVo> findDeptTree(String hoscode);

    Object getDepName(String hoscode, String depcode);
}
