package vip.ph.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import vip.ph.yygh.cmn.mapper.DictMapper;
import vip.ph.yygh.cmn.service.DictService;
import vip.ph.yygh.model.cmn.Dict;
import vip.ph.yygh.model.vo.cmn.DictEeVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    //导出数据字典接口
    @Override
    public void exportData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
        //查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        //Dict -- DictEeVo
        List<DictEeVo> dictVoList = new ArrayList<>();
        for(Dict dict:dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            // dictEeVo.setId(dict.getId());
            BeanUtils.copyProperties(dict,dictEeVo);
            dictVoList.add(dictEeVo);
        }
        //调用方法进行写操作
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //判断id下面是否还有子节点
    private boolean isChild(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }
}
