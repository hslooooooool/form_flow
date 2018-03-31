package cn.com.smart.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.helper.ObjectHelper;
import cn.com.smart.report.bean.entity.TReportSqlResource;
import cn.com.smart.report.service.IReportSqlResourceService;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.helper.PageHelper;

@Service
public class ReportSqlResourceService extends MgrServiceImpl<TReportSqlResource> implements IReportSqlResourceService {

    @Override
    public SmartResponse<Object> getDatas(TReportSqlResource sqlResource, Map<String,Object> params, int start, int rows) {
        if(null == sqlResource) {
            throw new NullArgumentException("sqlResource对象为空");
        }
        if(StringUtils.isEmpty(sqlResource.getSql())) {
            throw new NullArgumentException("SQL语句为空");
        }
        SmartResponse<Object> smartResp = new SmartResponse<Object>();
        smartResp.setResult(OP_NOT_DATA_SUCCESS);
        smartResp.setMsg(OP_NOT_DATA_SUCCESS_MSG);
        long totalNum = getDao().countSql(sqlResource.getSql(), params);
        if(start <= totalNum) {
            List<Object> objs = getDao().queryObjSql(sqlResource.getSql(), params, start, rows);
            if(null != objs && objs.size()>0) {
                objs = ObjectHelper.handleObjDate(objs);
                smartResp.setResult(OP_SUCCESS);
                smartResp.setMsg(OP_SUCCESS_MSG);
                smartResp.setDatas(objs);
                smartResp.setPerPageSize(rows);
                smartResp.setTotalNum(totalNum);
                smartResp.setTotalPage(PageHelper.getTotalPage(totalNum, rows));
                smartResp.setSize(objs.size());
            }
            objs = null;
        }
        return smartResp;
    }

    @Override
    public BaseDaoImpl<TReportSqlResource> getDao() {
        return super.getDao();
    }
    
}
