package cn.com.smart.report.poi;

import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 抽象类
 * @author lmq  2017年10月14日
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractWriteExcel implements IWriteExcel {

    /**
     * 默认单元格宽度
     */
    protected static final int DEFAULT_CELL_WIDTH = 6000;
    protected static final int MIN_CELL_WIDTH = 1500;
    
    protected Logger logger;
    
    protected Workbook wb;
    
    public AbstractWriteExcel() {
        logger = LoggerFactory.getLogger(getClass());
        wb = new XSSFWorkbook();
    }

    public AbstractWriteExcel(Workbook wb) {
        logger = LoggerFactory.getLogger(getClass());
        this.wb = wb;
    }

    @Override
    public Workbook write() {
        if(null == this.wb) {
            throw new NullPointerException("Workbook的实例为空");
        }
        String[] headerTitle = getHeaderTitle();
        if(null == headerTitle || headerTitle.length == 0) {
            throw new NullPointerException("列表列标题为空");
        }
        int titleLength = headerTitle.length;
        Integer[] cellWidth = handleCellWidth(headerTitle);
        Sheet sheet = wb.createSheet("sheet1");
        
        createHeader(headerTitle, sheet);
        createDataRow(0, getDatas(), sheet, titleLength);
        
        for (int i = 0; i < titleLength; i++) {
            sheet.setColumnWidth(i, cellWidth[i]);
        }
        return this.wb;
    }
    
    /**
     * 处理单元格宽度
     * @param headerTitle 头标题
     * @return 返回单元格宽度数组
     */
    private Integer[] handleCellWidth(String[] headerTitle) {
        int headerLen = headerTitle.length;
        Integer[] cellWidth = new Integer[headerLen];
        int totalWidth = DEFAULT_CELL_WIDTH * headerLen;
        String[] widthArray = getCellWidth();
        for (int i = 0; i < headerTitle.length; i++) {
            if(null == widthArray) {
                cellWidth[i] = DEFAULT_CELL_WIDTH;
            } else if(StringUtils.isEmpty(widthArray[i])) {
                cellWidth[i] = DEFAULT_CELL_WIDTH;
            } else if(widthArray[i].contains("%")) {
                String numStr = widthArray[i].replace("%", "").trim();
                if(StringUtils.isInteger(numStr)) {
                    cellWidth[i] = (int)(totalWidth * (Float.parseFloat(numStr)/100));
                } else {
                    cellWidth[i] = DEFAULT_CELL_WIDTH;
                }
            } else if(StringUtils.isInteger(widthArray[i])) {
                int width = Integer.parseInt(widthArray[i]);
                if(width > MIN_CELL_WIDTH) {
                    cellWidth[i] = width;
                } else {
                    cellWidth[i] = DEFAULT_CELL_WIDTH;
                }
            }
        }
        return cellWidth;
    }
    
    /**
     * 创建单元格标题
     * @param headerTitle 单元格标题
     * @param sheet Sheet对象
     */
    protected void createHeader(String[] headerTitle, Sheet sheet) {
        LoggerUtils.debug(logger, "正在创建单元格标题--有["+headerTitle.length+"]个字段...");
        Font font = wb.createFont();
        font.setFontName("Verdana");
        font.setFontHeight((short) 200);
        font.setBold(true);
        CellStyle cellStyle = createCellStyle(HSSFColorPredefined.RED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);  
        cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
        cellStyle.setFont(font);// 设置字体
        //为sheet1生成第一行，用于放表头信息
        Row row = sheet.createRow(0);
        row.setHeight((short)400);
        //第一行标题头
        for(int i=0; i< headerTitle.length; i++){
            Cell cell = row.createCell((short)i);
            cell.setCellStyle(cellStyle);  
            cell.setCellValue(headerTitle[i]);
        }
        LoggerUtils.debug(logger, "单元格标题创建[结束].");
    }
    
    /**
     * 生成行数据
     * @param startRowNum 行开始位置
     * @param datas 数据列表
     * @param sheet Sheet对象
     * @param titleLength 标题长度
     */
    protected void createDataRow(int startRowNum, List<Object> datas, Sheet sheet, int titleLength) {
        LoggerUtils.debug(logger, "正在创建数据行,开始行为["+startRowNum+"]...");
        CellStyle cellStyle = createCellStyle(HSSFColorPredefined.BLACK);
        int startCellIndex = 1;
        if(isShowId()) {
            startCellIndex = 0;
        }
        for(int i= 0; i < datas.size(); i++){
            Row row = sheet.createRow(startRowNum + i + 1);
            row.setHeight((short)400);
            Object[] objs = (Object[])datas.get(i);
            for(int j = startCellIndex, len = objs.length; (j<len && j <=titleLength); j++){
                Cell cell = row.createCell(j - startCellIndex);
                cell.setCellValue(StringUtils.handleNull(objs[j]));
                cell.setCellStyle(cellStyle);
            }
        }
    }
    
    /**
     * 创建单元格样式
     * @param color 边框颜色
     * @return 返回单元格样式对象
     */
    private CellStyle createCellStyle(HSSFColorPredefined color) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置边框  
        cellStyle.setBottomBorderColor(color.getIndex());  
        cellStyle.setBorderBottom(BorderStyle.THIN);  
        cellStyle.setBorderLeft(BorderStyle.THIN);  
        cellStyle.setBorderRight(BorderStyle.THIN);  
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }
    
    
    /**
     * 获取标题
     * @return 标题数组
     */
    protected abstract String[] getHeaderTitle();
    
    /**
     * 获取单元格宽度
     * @return 返回单元格宽度的数组
     */
    protected abstract String[] getCellWidth();
    
    /**
     * 获取对象数组列表
     * @return 返回对象数组列表
     */
    protected abstract List<Object> getDatas(); 
    
    /**
     * 是否显示ID
     * <p> 注意；如果返回为true；需要是：数据中有ID值，并且ID的值是数组中的第一个字段；负责会报错；</p>
     * <p> 如：方法返回：true；则对应的数据应该是:["12345","张三",20,"男"]；其中："123456"为ID的值 <p> 
     * @return 如果列表中需要显示ID值，则返回: true；否则返回: false
     */
    protected abstract boolean isShowId();
}
