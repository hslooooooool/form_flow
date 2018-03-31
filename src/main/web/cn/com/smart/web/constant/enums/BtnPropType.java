package cn.com.smart.web.constant.enums;

/**
 * 自定义按钮类型，常量定义
 * @author lmq
 * @version 1.0 2015年9月9日
 * @since 1.0
 *
 */
public interface BtnPropType {

	enum SelectType {
		/**
		 * 不选择数据
		 */
		NONE(1,"none-selected"),
		/**
		 * 只能选择一条数据
		 */
		ONE(2,"one-selected"),
		/**
		 * 选择多条数据
		 */
		MULTI(3,"multi-selected");
		private int index;
		private String value;
		private SelectType(int index,String value) {
			this.index = index;
			this.value = value;
		}
		
		public String getValue(int index) {
			String valueTmp = null;
			for (SelectType selectType : SelectType.values()) {
				if(selectType.getIndex() == index) {
					valueTmp = selectType.getValue();
					break;
				}
			}
			return valueTmp;
		}
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	
	enum OpenStyle {
	    
	    /**
	     * 没有打开样式
	     */
	    NONE(0, ""),
	    
		/**
		 * 弹出框
		 */
		OPEN_POP(1,"open-pop"),
		/**
		 * 在当前所在页面打开
		 */
		OPEN_SELF(2,"open-self"),
		/**
		 * 打开一个新的窗口
		 */
		OPEN_BLANK(3,"open-blank"),
		
		/**
		 * 打开一个新的Tab窗口
		 */
		OPEN_NEW_TAB(4,"open-new-tab");
		
		private int index;
		private String value;
		private OpenStyle(int index,String value) {
			this.index = index;
			this.value = value;
		}
		
		public String getValue(int index) {
			String valueTmp = null;
			for (OpenStyle openStyle : OpenStyle.values()) {
				if(openStyle.getIndex() == index) {
					valueTmp = openStyle.getValue();
					break;
				}
			}
			return valueTmp;
		}
		
		/**
		 * 根据值获取OpenStyle对象
		 * @param value 值
		 * @return 返回OpenStyle对象
		 */
		public static OpenStyle getValue(String value) {
		    OpenStyle valueTmp = null;
            for (OpenStyle openStyle : OpenStyle.values()) {
                if(openStyle.getValue().equals(value)) {
                    valueTmp = openStyle;
                    break;
                }
            }
            return valueTmp;
        }
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
}
