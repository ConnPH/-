package vip.ph.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.ph.yygh.hosp.mapper.HospSetMapper;
import vip.ph.yygh.hosp.service.HospSetService;
import vip.ph.yygh.model.hosp.HospitalSet;

@Service
public class HospSetServiceImpl extends ServiceImpl<HospSetMapper, HospitalSet> implements HospSetService {


    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }
}
