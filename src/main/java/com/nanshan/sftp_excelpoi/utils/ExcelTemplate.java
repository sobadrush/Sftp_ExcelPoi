package com.nanshan.sftp_excelpoi.utils;

/**
 * @author RogerLo
 * @date 2023/5/28
 */

import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Roger Lo
 *
 * 【Step1】. 覆寫 getExcelInputSream()[ getExcelData() 可不寫 ]
 * 【※※※】 調整 VO 中 private 屬性的宣告順序可改變Excel輸出時的column順序
 */
@Component
public class ExcelTemplate {

    public ExcelTemplate() {
        super();
    }

    /**
     * 建立實體xlsx檔 (預設sheets)
     *
     * @param exceler
     * @param voClazz
     * @param filePath
     */
    public void doCustomerExcel(CustomizeExceler exceler, Class<?> voClazz, String filePath , boolean isOverride) {
        exceler.doExcel(filePath, voClazz, isOverride);
    }

    /**
     * 建立實體xlsx檔 (自訂sheets)
     *
     * @param exceler
     * @param voClazz
     * @param filePath
     * @param isOverride : 是否要覆蓋既有檔案
     * @param sheetsnames
     */
    public void doCustomerExcel(CustomizeExceler exceler, Class<?> voClazz, String filePath, String[] sheetsnames , boolean isOverride) {
        exceler.doExcel(filePath, sheetsnames, voClazz, isOverride);
    }

    /**
     * 取得POI workbook 的 inputStream (自訂sheets)
     *
     * @param exceler
     * @param voClazz
     * @param sheetsnames
     */
    public InputStream doCustomerExcelGetInputStream(CustomizeExceler exceler, Class<?> voClazz, String[] sheetsnames) {
        return exceler.doExcelGetInputStream(sheetsnames, voClazz);// filePath 為傳入時，直接取得 POI workbook 的 inputStream
    }
}
