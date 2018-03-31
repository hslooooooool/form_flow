package cn.com.smart.form.bean;

/**
 *
 * 需要记录日志的字段信息
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public class LogFieldInfo {

    private String tableId;

    private String tableName;

    private String tableFieldId;

    private String tableFieldName;

    private String plugins;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableFieldId() {
        return tableFieldId;
    }

    public void setTableFieldId(String tableFieldId) {
        this.tableFieldId = tableFieldId;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    /**
     * LogFieldInfo有的属性转换为TableFieldMap对应的属性
     * @return 返回TableFieldMap对象
     */
    public TableFieldMap convertFieldMap() {
        TableFieldMap map = new TableFieldMap();
        map.setTableFieldId(this.tableId);
        map.setTableName(this.tableName);
        map.setTableFieldId(this.tableFieldId);
        map.setTableFieldName(this.tableFieldName);
        map.setPlugin(this.plugins);
        return map;
    }
}
