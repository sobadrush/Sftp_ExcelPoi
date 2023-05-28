package com.nanshan.sftp_excelpoi.utils;

import com.nanshan.sftp_excelpoi.model.EmpVO;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExcelTemplateTest {

    @Autowired
    private ExcelTemplate excelTemplate;

    private List<EmpVO> genFakeEmpData() {
        return Stream.of(
                EmpVO.builder().empNo(1001).empName("Roger").empJob("SA").empHireDate(new Date(new java.util.Date().getTime())).build(),
                EmpVO.builder().empNo(1001).empName("Kelly").empJob("PM").empHireDate(new Date(new java.util.Date().getTime())).build(),
                EmpVO.builder().empNo(1001).empName("Ken").empJob("SD").empHireDate(new Date(new java.util.Date().getTime())).build(),
                EmpVO.builder().empNo(1001).empName("Terry").empJob("PG").empHireDate(new Date(new java.util.Date().getTime())).build(),
                EmpVO.builder().empNo(1001).empName("Billy").empJob("QA").empHireDate(new Date(new java.util.Date().getTime())).build()
        ).collect(Collectors.toList());
    }

    @Test
    @Order(1)
    @DisplayName("測試 excelTemplate doCustomerExcel")
    // @Disabled
    void test_001(TestInfo testInfo) {
        // 建立實體xlsx檔 (預設sheets)
        excelTemplate.doCustomerExcel(new CustomizeExceler() {

            private Font font1 = this.getWorkbook().createFont();
            private XSSFCellStyle baseicCellStyle = this.getWorkbook().createCellStyle();
            private XSSFCellStyle titleStyle = this.getWorkbook().createCellStyle();

            {
                font1.setFontHeightInPoints((short) 14);
                font1.setFontName("Microsoft JhengHei");
            }
            {
                baseicCellStyle.setWrapText(true);
                baseicCellStyle.setAlignment(HorizontalAlignment.LEFT);
                baseicCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                baseicCellStyle.setBorderBottom(BorderStyle.THIN); //下邊框
                baseicCellStyle.setBorderLeft(BorderStyle.THIN);//左邊框
                baseicCellStyle.setBorderTop(BorderStyle.THIN);//上邊框
                baseicCellStyle.setBorderRight(BorderStyle.THIN);//右邊框
                baseicCellStyle.setFont(font1);
            }
            {
                titleStyle.setWrapText(true);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                titleStyle.setBorderBottom(BorderStyle.THIN); //下邊框
                titleStyle.setBorderLeft(BorderStyle.THIN); // 左邊框
                titleStyle.setBorderTop(BorderStyle.THIN); // 上邊框
                titleStyle.setBorderRight(BorderStyle.THIN); // 右邊框
                titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFont(font1);
            }

            @Override
            protected void customerExcel(Class<?> voClazz) {
                int sheetNum = 0; // 【從第個Sheet開始寫】
                int numberOfSheet = this.getWorkbook().getNumberOfSheets();// 【共有多少個Sheet】
                List<String> getterMethods = ExcelUtil.getMethods(voClazz);

                // List<EmpVO> empList = empDAO.getAll();// >>>>>>【查詢要輸出到Excel的資料】
                List<EmpVO> empList = genFakeEmpData();

                String[] titles = getterMethods.stream().map(ss -> ss.substring(3)).toArray(String[]::new);

                while (sheetNum < numberOfSheet) {
                    XSSFSheet sheet = this.getSheets()[sheetNum];
                    ExcelUtil.createTitleRow(sheet, titles, titleStyle, 0, 0);
                    int rownum = 10; // 【從第幾列開始寫】
                    for (EmpVO empVO : empList) {
                        Row excelRow = sheet.createRow(rownum++);
                        int col = 0;
                        for (String methodName : getterMethods) {
                            Cell excelCell = excelRow.createCell(col++);
                            excelCell.setCellStyle(baseicCellStyle); // 設置單元格样式
                            String getterResult = ExcelUtil.invokeGetter(empVO, methodName);// 調用VO的Getter方法
                            excelCell.setCellValue(getterResult);
                            this.getSheets()[sheetNum].autoSizeColumn(col - 1);// 設定每個column自動寬度
                        }
                    }
                    //---------------
                    sheetNum++;
                }

            }
        }, EmpVO.class /* 接資料的VO */, "./outputFiles/EmpData.xlsx", true /*是否覆蓋既有檔案*/);
    }

}