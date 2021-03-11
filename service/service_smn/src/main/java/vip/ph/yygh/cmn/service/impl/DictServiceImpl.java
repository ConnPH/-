package vip.ph.yygh.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.ph.yygh.cmn.mapper.DictMapper;
import vip.ph.yygh.cmn.service.DictService;
import vip.ph.yygh.model.cmn.Dict;

import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    /**
     * 查询子数据
     * @param id
     * @return
     */
    @Override
    public List<Dict> findChildData(Long id) {

        QueryWrapper<Dict> wrapper = new QueryWrapper();
        wrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(wrapper);

        // 向list集合中每个dict对象中设置haschdild

        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChild(dictId);
            dict.setHasChildren(isChild);
        }


        return dictList;
    }




    //判断id下面是否还有子节点
    private boolean isChild(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }
}
