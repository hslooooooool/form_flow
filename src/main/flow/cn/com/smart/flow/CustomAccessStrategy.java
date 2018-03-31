/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.smart.flow;

import java.util.ArrayList;
import java.util.List;

import org.snaker.engine.impl.GeneralAccessStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.web.bean.UserInfo;
import cn.com.smart.web.bean.entity.TNUser;
import cn.com.smart.web.service.UserService;

/**
 * 自定义访问策略，根据操作人获取其所有组集合（部门、角色、权限）
 * @author yuqs
 * @since 0.1
 */
public class CustomAccessStrategy extends GeneralAccessStrategy {
	
    @Autowired
    private UserService userServ;
    
    protected List<String> ensureGroup(String operator) {
        if(StringUtils.isEmpty(operator)) {
            return null;
        }
        TNUser user = userServ.find(operator).getData();
        if(null == user) {
            return null;
        }
        UserInfo userInfo =  userServ.getUserInfo(user);
        List<String> groups = null;
        if(null != userInfo) {
            groups = new ArrayList<String>();
            if(StringUtils.isNotEmpty(userInfo.getDepartmentId())) {
                groups.add(userInfo.getDepartmentId());
            } else if(StringUtils.isNotEmpty(userInfo.getOrgId())) {
                groups.add(userInfo.getOrgId());
            }
            if(StringUtils.isNotEmpty(userInfo.getPositionId())) {
                groups.add(userInfo.getPositionId());
            }
            groups.add(userInfo.getId());
        }
        return groups;
    }
}
