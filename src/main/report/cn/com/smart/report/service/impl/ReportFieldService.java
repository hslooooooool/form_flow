package cn.com.smart.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.CollectionUtils;

import cn.com.smart.report.bean.entity.TReportField;
import cn.com.smart.service.impl.MgrServiceImpl;

@Service
public class ReportFieldService extends MgrServiceImpl<TReportField> {

    /**
     * 更新字段
     * @param reportId 报表ID
     * @param reportFields 字段
     */
    public void updateField(String reportId, List<TReportField> reportFields) {
        if (StringUtils.isEmpty(reportId) || CollectionUtils.isEmpty(reportFields)) {
            return;
        }
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("reportId", reportId);
        List<TReportField> fields = super.findByParam(param).getDatas();
        if (CollectionUtils.isEmpty(fields)) {
            if (CollectionUtils.isNotEmpty(reportFields))
                super.save(reportFields);
        } else {
            List<TReportField> saveFieldList = new ArrayList<TReportField>();
            List<TReportField> deleteFieldList = new ArrayList<TReportField>();
            List<TReportField> updateFieldList = new ArrayList<TReportField>();

            for (TReportField reportField : reportFields) {
                reportField.setReportId(reportId);
                if (StringUtils.isEmpty(reportField.getId()) && StringUtils.isNotEmpty(reportField.getTitle())) {
                    saveFieldList.add(reportField);
                }
            }
            //注意标题为空的字段会删除
            boolean isDelete = true;
            for (TReportField field : fields) {
                isDelete = true;
                for (TReportField reportField : reportFields) {
                    if (StringUtils.isNotEmpty(reportField.getId())) {
                        if (field.getId().equals(reportField.getId()) && StringUtils.isNotEmpty(reportField.getTitle())) {
                            field.setCustomClass(reportField.getCustomClass());
                            field.setOpenUrlType(reportField.getOpenUrlType());
                            field.setParamName(reportField.getParamName());
                            field.setParamValue(reportField.getParamValue());
                            field.setSearchName(reportField.getSearchName());
                            field.setSortOrder(reportField.getSortOrder());
                            field.setTitle(reportField.getTitle());
                            field.setUrl(reportField.getUrl());
                            field.setWidth(reportField.getWidth());
                            updateFieldList.add(field);
                            isDelete = false;
                            break;
                        }
                    } else {
                        isDelete = false;
                    }
                }
                if (isDelete) {
                    deleteFieldList.add(field);
                }
            } // for
            reportFields = null;
            fields = null;
            if(CollectionUtils.isNotEmpty(saveFieldList)) {
                super.save(saveFieldList);
            }
            if(CollectionUtils.isNotEmpty(updateFieldList)) {
                super.update(updateFieldList);
            }
            if(CollectionUtils.isNotEmpty(deleteFieldList)) {
                super.getDao().delete(deleteFieldList);
            }
        }
    }

}
