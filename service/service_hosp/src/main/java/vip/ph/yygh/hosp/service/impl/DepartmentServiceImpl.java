package vip.ph.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vip.ph.yygh.hosp.repository.DepartmentRepository;
import vip.ph.yygh.hosp.service.DepartmentService;
import vip.ph.yygh.model.hosp.Department;
import vip.ph.yygh.model.vo.hosp.DepartmentQueryVo;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    @Resource
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Map<String, Object> paramMap) {
        Department department = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Department.class);
        Department targetDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());


        if(null != targetDepartment) {
            targetDepartment.setUpdateTime(new Date());
            targetDepartment.setIsDeleted(0);
            departmentRepository.save(targetDepartment);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }







    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        Pageable pageable = PageRequest.of(page-1,limit);
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);
        Example<Department> example = Example.of(department,matcher);
        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void departmentRemove(String hoscode, String depcode) {

        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(null != department) {
//departmentRepository.delete(department);
            departmentRepository.deleteById(department.getId());
        }


    }
}
