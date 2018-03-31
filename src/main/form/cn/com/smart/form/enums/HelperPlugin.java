package cn.com.smart.form.enums;

/**
 * 帮助插件 
 * @author lmq <br />
 * 2016年10月21日
 * @version 1.0
 * @since 1.0
 */
public class HelperPlugin {

	/**
	 * 数据来源类型
	 * @author lmq <br />
	 * 2016年10月21日
	 * @version 1.0
	 * @since 1.0
	 */
	public enum SourceType {
		/**
		 * helper_library -- 帮助库
		 */
		LIBRARY("helper_library"),
		/**
		 * custom_addr -- 自定义地址
		 */
		CUSTOM_ADDR("custom_addr"),
		
		/**
		 * custom_content -- 自定义内容
		 */
		CUSTOM_CONTENT("custom_content");
		
		private String value;
		
		private SourceType(String value) {
			this.value = value;
		}
		
		/**
		 * 获取数据来源类型
		 * @param value
		 * @return
		 */
		public static SourceType getObj(String value) {
			SourceType sourceType = null;
			for(SourceType sourceTypeTmp : SourceType.values()) {
				if(sourceTypeTmp.getValue().equals(value)) {
					sourceType = sourceTypeTmp;
					break;
				}
			}//for
			return sourceType;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	
	}
	
	/**
	 * 帮助展现方式
	 * @author lmq <br />
	 * 2016年10月21日
	 * @version 1.0
	 * @since 1.0
	 */
	public enum DisplayModel {
		/**
		 * dialog_ -- 弹出窗口方式
		 */
		DISPLAY_DIALOG("dis_dialog"),
		/**
		 * tooltip -- 弹出式提示方式
		 */
		DISPLAY_POPOVER("dis_popover"),
		/**
		 * blank -- 超链接新窗口方式
		 */
		DISPLAY_BLANK("dis_blank");
		
		private String value;
		
		private DisplayModel(String value) {
			this.value = value;
		}
		
		/**
		 * 获取展示方式
		 * @param value
		 * @return
		 */
		public static DisplayModel getObj(String value) {
			DisplayModel disModel = null;
			for(DisplayModel disModelTmp : DisplayModel.values()) {
				if(disModelTmp.getValue().equals(value)) {
					disModel = disModelTmp;
					break;
				}
			}//for
			return disModel;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
}
