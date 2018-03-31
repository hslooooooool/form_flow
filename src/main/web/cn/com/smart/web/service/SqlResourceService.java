package cn.com.smart.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNSqlResource;
import cn.com.smart.web.cache.impl.SqlResourceMemoryCache;

@Service
public class SqlResourceService extends MgrServiceImpl<TNSqlResource> {
    
    @Autowired
    private SqlResourceMemoryCache sqlResCache;
    
    /**
     * 关联用户表查询
     * @param id SQL资源的ID（主键）
     * @return 返回TNSqlResource实体对象
     */
    public TNSqlResource findAssocUser(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new NullArgumentException();
        }
        TNSqlResource sqlResource = null;
        String sql = SQLResUtil.getOpSqlMap().getSQL("query_assoc_user_sql_resource");
        if(StringUtils.isNotEmpty(sql)) {
            Map<String, Object> param = new HashMap<String, Object>(1);
            param.put("id", id);
            List<TNSqlResource> list = super.getDao().querySqlToEntity(sql, param, TNSqlResource.class);
            if(CollectionUtils.isNotEmpty(list)) {
                sqlResource = list.get(0);
            }
        }
        return sqlResource;
    }

    @Override
    public SmartResponse<String> save(TNSqlResource t) throws ServiceException {
        SmartResponse<String> smartResp = super.save(t);
        if(OP_SUCCESS.equals(smartResp.getResult())) {
            sqlResCache.addOrUpdate(t.getResName(), t.getSql());
        }
        return smartResp;
    }

    @Override
    public SmartResponse<String> update(TNSqlResource t) throws ServiceException {
        SmartResponse<String> smartResp = super.update(t);
        if(OP_SUCCESS.equals(smartResp.getResult())) {
            sqlResCache.addOrUpdate(t.getResName(), t.getSql());
        }
        return smartResp;
    }

    @Override
    public SmartResponse<String> delete(String id) throws ServiceException {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("删除失败");
        if(StringUtils.isNotEmpty(id)) {
            String[] ids = id.split(MULTI_VALUE_SPLIT);
            List<TNSqlResource> sqlResList = super.finds(ids).getDatas();
            if(CollectionUtils.isNotEmpty(sqlResList)) {
                for (TNSqlResource sqlRes : sqlResList) {
                    sqlResCache.remove(sqlRes.getResName());
                }
                if(getDao().delete(sqlResList)) {
                    smartResp.setResult(OP_SUCCESS);
                    smartResp.setMsg("删除成功");
                }
            }
        }
        return super.delete(id);
    }
    
}
