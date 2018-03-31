package cn.com.smart.web.controller.impl;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.web.bean.RequestPage;
import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNSqlResource;
import cn.com.smart.web.controller.base.BaseController;
import cn.com.smart.web.service.IOPService;
import cn.com.smart.web.service.SqlResourceService;
import cn.com.smart.web.tag.bean.ALink;
import cn.com.smart.web.tag.bean.DelBtn;
import cn.com.smart.web.tag.bean.EditBtn;
import cn.com.smart.web.tag.bean.PageParam;
import cn.com.smart.web.tag.bean.RefreshBtn;

/**
 * SQL资源管理 控制器类
 * @author lmq  2017年10月25日
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/sql/resource")
public class SqlResourceController extends BaseController {

    private static final String VIEW_DIR = WEB_BASE_VIEW_DIR + "/sql";
    
    @Autowired
    private IOPService opServ;
    @Autowired
    private SqlResourceService sqlResServ;
    
    /**
     * 资源列表 
     * @param session HttpSession对象
     * @param searchParam 搜索对象
     * @param page 分页对象
     * @return 返回列表视图
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpSession session, FilterParam searchParam, RequestPage page) {
        String uri = "sql/resource/list";
        SmartResponse<Object> smartResp = opServ.getDatas("get_sql_resource_mgr_list", searchParam, page.getStartNum(), page.getPageSize());
        addBtn = new EditBtn("add","showPage/base_sql_add", "添加SQL资源", "800");
        editBtn = new EditBtn("edit","sql/resource/edit", "修改SQL资源", "800");
        delBtn = new DelBtn("sql/resource/delete", "确定要删除选中的SQL资源吗？",uri,null, null);
        refreshBtn = new RefreshBtn(uri, null);
        pageParam = new PageParam(uri, null, page.getPage(), page.getPageSize());
        ALink alink = new ALink("sql/resource/view", "800", "查看SQL资源信息");
        alinks = new ArrayList<ALink>(1);
        alinks.add(alink);
        ModelAndView modelView = new ModelAndView();
        ModelMap modelMap = modelView.getModelMap();
        modelMap.put("smartResp", smartResp);
        modelMap.put("addBtn", addBtn);
        modelMap.put("editBtn", editBtn);
        modelMap.put("delBtn", delBtn);
        modelMap.put("refreshBtn", refreshBtn);
        modelMap.put("pageParam", pageParam);
        modelMap.put("searchParam", searchParam);
        modelMap.put("alinks", alinks);
        modelView.setViewName(VIEW_DIR+"/list");
        return modelView;
    }
    
    /**
     * 保存
     * @param session HttpSession对象
     * @param resource SQL资源实体类
     * @return 返回保存结果
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> save(HttpSession session, TNSqlResource resource) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        if(null != resource) {
            UserInfo userInfo = super.getUserInfoFromSession(session);
            resource.setUserId(userInfo.getId());
            smartResp = sqlResServ.save(resource);
        }
        return smartResp;
    }
    
    /**
     * 修改
     * @param id SQL资源ID
     * @return 返回修改视图
     */
    @RequestMapping("/edit")
    public ModelAndView edit(String id) {
        ModelAndView modelView = new ModelAndView();
        if(StringUtils.isNotEmpty(id)) {
            modelView.getModelMap().put("objBean", sqlResServ.find(id).getData());
        }
        modelView.setViewName(VIEW_DIR+"/edit");
        return modelView;
    }
    
    /**
     * 更新
     * @param session HttpSession对象
     * @param resource SQL资源实体类
     * @return 返回更新结果
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> update(HttpSession session, TNSqlResource resource) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        if(null != resource && StringUtils.isNotEmpty(resource.getId())) {
            UserInfo userInfo = super.getUserInfoFromSession(session);
            resource.setLastUserId(userInfo.getId());
            resource.setLastModifyTime(new Date());
            smartResp = sqlResServ.update(resource);
        }
        return smartResp;
    }
    
    
    /**
     * 删除
     * @param id SQL资源ID
     * @return 返回删除结果
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SmartResponse<String> delete(String id) {
        return sqlResServ.delete(id);
    }
    
    /**
     * 查看
     * @param id SQL资源ID
     * @return 返回查看视图
     */
    @RequestMapping("/view")
    public ModelAndView view(String id) {
        ModelAndView modelView = new ModelAndView();
        if(StringUtils.isNotEmpty(id)) {
            modelView.getModelMap().put("objBean", sqlResServ.findAssocUser(id));
        }
        modelView.setViewName(VIEW_DIR+"/view");
        return modelView;
    }
}
