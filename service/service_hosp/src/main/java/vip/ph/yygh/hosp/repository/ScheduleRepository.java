package vip.ph.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vip.ph.yygh.model.hosp.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}
