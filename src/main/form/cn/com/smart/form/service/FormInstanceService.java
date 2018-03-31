package cn.com.smart.form.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mixsmart.constant.IMixConstant;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.exception.ServiceException;
import cn.com.smart.form.bean.entity.TForm;
import cn.com.smart.form.bean.entity.TFormInstance;
import cn.com.smart.form.helper.FormDataHelper;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.UserInfo;

/**
 * 表单实例 服务类
 * @author lmq 2017年8月28日
 * @version 1.0
 * @since 1.0
 */
@Service
public class FormInstanceService extends MgrServiceImpl<TFormInstance> {

    @Autowired
    private IFormDataService formDataServ;
    @Autowired
    private FormService formServ;
    @Autowired
    private FormAttachmentService formAttServ;

    /**
     * 创建表单实例
     * @param datas 表单数据
     * @param formDataId 表单数据ID
     * @param formId 表单ID
     * @param userInfo 用户信息
     * @return 返回创建结果
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SmartResponse<String> create(Map<String, Object> datas, String formDataId, String formId,
            UserInfo userInfo) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("表单提交失败");
        TForm form = formServ.find(formId).getData();
        if (null == form) {
            return smartResp;
        }
        String insTitle = formServ.getInstanceTitle(datas, formId, userInfo.getId(), form.getName());
        if (StringUtils.isEmpty(insTitle)) {
            insTitle = form.getName() + "(" + userInfo.getFullName() + ")";
        }
        if(formDataId.startsWith(FormDataHelper.APP_NEW_PREFIX)) {
            String tmplFormDataId = formDataId;
            formDataId = formDataServ.saveForm(datas, formId, formDataId, userInfo.getId(), 0);
            if (StringUtils.isNotEmpty(formDataId)) {
                TFormInstance formInstance = new TFormInstance();
                formInstance.setFormDataId(formDataId);
                formInstance.setFormId(formId);
                formInstance.setOrgId(userInfo.getOrgId());
                formInstance.setTitle(insTitle);
                formInstance.setUserId(userInfo.getId());
                super.save(formInstance);
                smartResp.setResult(OP_SUCCESS);
                smartResp.setMsg("表单提交成功");
                //更新表单附件中的formDataId字段值
                formAttServ.updateFormDataId(formDataId, tmplFormDataId);
                //删除过期的表单附件信息
                formAttServ.deleteExpireTmpAtt();
            }
        } else {
            boolean is = formDataServ.updateForm(datas, formId, formDataId, userInfo.getId(), 0);
            if (is) {
               updateTitle(formDataId, insTitle);
               smartResp.setResult(OP_SUCCESS);
               smartResp.setMsg("表单提交成功");
            }
        }
        return smartResp;
    }

    @Override
    public SmartResponse<String> delete(String id) throws ServiceException {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        smartResp.setMsg("表单实例删除失败");
        if (StringUtils.isEmpty(id)) {
            return smartResp;
        }
        String[] ids = id.split(IMixConstant.MULTI_VALUE_SPLIT);
        List<TFormInstance> list = super.finds(ids).getDatas();
        if (null != list && list.size() > 0) {
            if(deleteAssocByObj(list)) {
                smartResp.setResult(OP_SUCCESS);
                smartResp.setMsg("表单实例删除成功");
            }
        }
        return smartResp;
    }

    /**
     * 关联删除数据
     * @param list
     * @return
     */
    private boolean deleteAssocByObj(List<TFormInstance> list) {
        boolean is = false;
        Map<String, Object> param = null;
        try {
            String delSql = SQLResUtil.getOpSqlMap().getSQL("del_form_data");
            param = new HashMap<String, Object>();
            String sql = SQLResUtil.getOpSqlMap().getSQL("get_table_name");
            String[] plugins = new String[] {"file","files"};
            for (TFormInstance formIns : list) {
                //删除表单对应的附件
                List<String> attIds = formDataServ.getFieldInAttIds(formIns.getFormId(), plugins, formIns.getFormDataId()).getDatas();
                if(CollectionUtils.isNotEmpty(attIds)) {
                    formAttServ.deleteByAttIds(attIds);
                }
                formAttServ.deleteByFormDataId(formIns.getFormDataId());
                param.put("formId", formIns.getFormId());
                //获取表名称
                List<Object> objs = super.getDao().queryObjSql(sql, param);
                if (null != objs && objs.size() > 0) {
                    for (Object obj : objs) {
                        if (StringUtils.isNotEmpty(delSql)) {
                            param.clear();
                            param.put("formDataId", formIns.getFormDataId());
                            super.getDao().executeSql(delSql.replace("${table}", obj.toString()), param);
                        }
                    } // for
                } // if
                super.delete(formIns.getId());
            } // for
            is = true;
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return is;
    }
    
    /**
     * 更新标题
     * @param formDataId
     * @param title
     */
    private void updateTitle(String formDataId, String title) {
        Map<String,Object> params = new HashMap<String, Object>(2);
        params.put("title", title);
        params.put("formDataId", formDataId);
        super.execute("update_form_inst_title", params);
    }
}
