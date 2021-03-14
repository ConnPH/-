package vip.ph.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.ph.yygh.model.cmn.Dict;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportData(HttpServletResponse response);

    String getNameLevel(String dictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
