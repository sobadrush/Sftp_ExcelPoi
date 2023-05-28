package com.nanshan.sftp_excelpoi.utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author RogerLo
 * @date 2023/5/28
 */
public abstract class CustomizeExceler {
    private XSSFWorkbook workbook;
    private XSSFSheet[] sheets;

    private static final int CHAR_SIZE = 256;
    private static final String DEFAULT_FILE_PATH = "/MyExcels/MyFirstExcel.xlsx"; /* "/" 表示當前workspace所在的磁碟 */

    public CustomizeExceler() {
        this.workbook = new XSSFWorkbook(); //建立 excel 檔案
    }

    protected void createPOI() {
        int defaultSheetNum = 3;
        sheets = new XSSFSheet[defaultSheetNum];// for 物件陣列
        for (int i = 0 ; i < defaultSheetNum ; i++) {
            this.sheets[i] = workbook.createSheet("工作表" + (i + 1));// 建立 Excel 下方的 sheet
        }
    }

    protected void createPOI(String[] sheetsNames) {
        if (sheetsNames == null) {
            this.createPOI();
        } else {
            sheets = new XSSFSheet[sheetsNames.length];// for 物件陣列
            for (int i = 0 ; i < sheetsNames.length ; i++) {
                this.sheets[i] = workbook.createSheet(sheetsNames[i]);// 建立 Excel 下方的 sheet
            }
        }
    }

    protected void outputExcel(String file_path, boolean isOverride) {

        if ("".equals(file_path) || file_path == null ||
                file_path.length() == 0 || file_path.equals("dafaultPath")) {
            file_path = DEFAULT_FILE_PATH;/* "/" 表示當前workspace所在的磁碟 */
        }

        // 建立目錄及目標檔案
        boolean createResult = ExcelUtil.createFolderAndFile(file_path, isOverride);
        if (createResult) {
            System.out.println("檔案建立成功！" + " ( " + file_path + " )");
        } else  {
            System.out.println("檔案建立失敗！" + " ( " + file_path + " )");
            return;
        }

        /* 將excel檔藉由 io 輸出 */
        try (FileOutputStream outputStream = new FileOutputStream(file_path);) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            workbook = null; // 加速GC
        }
        System.out.println(" >>> Excel 產製完成！" + " ( " + file_path + " )" );
    }

    protected void doExcel(String filePath, String[] sheetsNames, Class<?> voClazz , boolean isOverride) {
        this.createPOI(sheetsNames);//---給sheetsNames陣列
        this.customerExcel(voClazz);
        this.outputExcel(filePath, isOverride);
    }

    protected void doExcel(String filePath, Class<?> voClazz , boolean isOverride) {
        this.doExcel(filePath, null, voClazz, isOverride);
    }

    /**
     * @param voClazz
     * 取得POI workbook 的 inputStream 供下載使用
     */
    protected InputStream doExcelGetInputStream(Class<?> voClazz) {
        return doExcelGetInputStream(null, voClazz);
    }

    /**
     * @param voClazz
     *
     * 取得POI workbook 的 inputStream 供下載使用
     */
    protected InputStream doExcelGetInputStream(String[] sheetsNames, Class<?> voClazz) {

        // 【Step1】
        if (sheetsNames == null) {
            this.createPOI();
        } else {
            this.createPOI(sheetsNames);//---給sheetsNames陣列
        }

        // 【Step2】
        this.customerExcel(voClazz);

        // 【Step3】
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is = new ByteArrayInputStream(bytes);
        System.out.println(" === 產生Excel的InputStream完成！ === ");

        return is;
    }

    /**
     * @param voClazz
     *
     * user自行實作產生Excel內容邏輯
     */
    protected abstract void customerExcel(Class<?> voClazz);

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public XSSFSheet[] getSheets() {
        return sheets;
    }

    public void setSheets(XSSFSheet[] sheets) {
        this.sheets = sheets;
    }
}


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>  一些參數   >>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//字體格式
//XSSFFont font = workbook.createFont();
//font.setColor(HSSFColor.BLACK.index); // 顏色
//font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗細體
//font.setFontHeightInPoints((short) 20); //字體高度
//font.setFontName("黑體"); //字體
//font.setItalic(true); //是否使用斜體
//font.setStrikeout(true); //是否使用刪除線

// 設定儲存格格式
//XSSFCellStyle styleRow1 = workbook.createCellStyle();
//styleRow1.setFillForegroundColor(HSSFColor.GREEN.index);//填滿顏色
//styleRow1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//styleRow1.setFont(font); // 設定字體
//styleRow1.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平置中
//styleRow1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直置中

// 設定框線
//styleRow1.setBorderBottom((short) 1);
//styleRow1.setBorderTop((short) 1);
//styleRow1.setBorderLeft((short) 1);
//styleRow1.setBorderRight((short) 1);
//styleRow1.setWrapText(true); // 自動換行

///* Title */
//sheet.autoSizeColumn(0); // 自動調整欄位寬度
//sheet.setColumnWidth(0, CHAR_SIZE * 10);
//sheet.setColumnWidth(1, CHAR_SIZE * 10);
//sheet.setColumnWidth(2, CHAR_SIZE * 50);

// 填滿顏色
//myCellStyle.setFillBackgroundColor(IndexedColors.CORAL.getIndex()); // 填滿顏色
//myCellStyle.setFillPattern(CellStyle.ALIGN_FILL);// 填滿的方式
