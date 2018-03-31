package cn.com.smart.web.constant.enums;

import com.mixsmart.utils.StringUtils;

/**
 * 图标类型
 * @author lmq  2017年10月25日
 * @version 1.0
 * @since 1.0
 */
public enum IconType {

    /**
     * glyphicon- Bootstrap 图标类型
     */
    BOOTSTRAP("glyphicon-"),
    
    /**
     *  fa- Font Awesome 图标类型
     */
    FONT_AWESOME("fa-");
    
    private String value;
    
    IconType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    /**
     * 获取图标类型
     * @param value 图标类型值
     * @return 返回图标类型对象；如：没有返回默认图标类型（Bootstrap图标类型）
     */
    public static IconType getIconType(String value) {
        IconType tmp = null;
        if(StringUtils.isEmpty(value)) {
            tmp = BOOTSTRAP;
        } else {
            for (IconType iconType : IconType.values()) {
                if(iconType.getValue().equals(value)) {
                    tmp = iconType;
                    break;
                }
            }
            if(null == tmp) {
                tmp = BOOTSTRAP;
            }
        }
        return tmp;
    }
}
