package vip.ph.yygh.hosp.service;

import org.springframework.data.domain.Page;
import vip.ph.yygh.model.hosp.Schedule;
import vip.ph.yygh.model.vo.hosp.ScheduleQueryVo;

import java.util.Map;

public interface ScheduleService {
    //上传排班接口
    void save(Map<String, Object> paramMap);

    //查询排班接口
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班
    void remove(String hoscode, String hosScheduleId);
}
