package com.nanshan.sftp_excelpoi.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RogerLo
 * @date 2023/5/28
 */
public class ExcelUtil {

    public static boolean createFolderAndFile(String filePath, boolean isOverride/* 是否需要判斷檔案是否已存在 */) {

        if (isOverride == true) {
            // 覆蓋既有檔案
            java.io.File file = new java.io.File(filePath);
            if (file.exists() == false) {
                file.getParentFile().mkdirs();
            }
            return true;
        } else {
            // 不覆蓋既有檔案
            java.io.File file = new java.io.File(filePath);
            if (file.exists() == false) {
                file.getParentFile().mkdirs(); //這樣才不會把ggg.txt當成folder建立
                try {
                    file.createNewFile();
                    System.out.println("檔案及資料夾建立完成!" + "( " + filePath + " )");
                    return true;
                } catch (IOException e) {
                    System.out.println("檔案及資料夾建立失敗!" + "( " + filePath + " )");
                    return false;
                }
            } else {
                System.out.println("磁碟中已有此目錄及檔案!" + "( " + filePath + " )");
                return false; //已有此檔案
            }

        }

    }

    /**
     * @param clazz
     *            : vo.class
     * @param methodStartWith
     *            : methodStartWith
     * @return List<類別中方法的名稱字串> List內容的順序根據VO中宣告private屬性的順序排序
     */
    public static List<String> getMethods(Class<?> clazz, String methodStartWith) {
        List<String> methodList = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();   // 取得屬性
        Method[] methods = clazz.getDeclaredMethods();// 取得所有方法

        for (Field field : fields) {
            for (Method mm : methods) {
                String methodName = mm.getName();
                String methodReturnType = mm.getReturnType().toString();

                if (methodReturnType.contains("List") || methodReturnType.contains("Set")) {
                    continue;// 回傳型態為Set 或 List 時，通常是用來做關聯的屬性(一方中宣告多方)，跳過
                }

                if (methodName.startsWith(methodStartWith)) {// 取得 methodStartWith字串開頭 的方法的名稱 : 加到 methodList
                    String methodNameWithoutGet /* 對方法名稱作字串切割 =>取得 getter方法名稱不含 "get" 的字串 */
                            = methodName.substring(methodName.indexOf(methodStartWith) + (methodStartWith.length()));
                    if (field.getName().equalsIgnoreCase(methodNameWithoutGet)) {
                        methodList.add(methodName);
                    }
                }
            }
        }
        return methodList;
    }

    /**
     * @param clazz
     *            : vo.class
     * @param String
     *            : methodStartWith，預設值 : "get"
     * @return List<類別中方法的名稱字串> List內容的順序根據VO中宣告private屬性的順序排序
     */
    public static List<String> getMethods(Class<?> clazz /* , String methodStartWith */) {
        return getMethods(clazz, "get");
    }

    /**
     * @param valueObject
     *            : vo 物件
     * @param methodName
     *            : 方法名稱字串
     * @return 呼叫getter的結果字串
     */
    public static String invokeGetter(Object valueObject, String methodName) {
        String getterResultString = null;
        try {
            getterResultString = valueObject.getClass().getMethod(methodName).invoke(valueObject) + "";
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return getterResultString;
        //return invokeGetter(valueObject, methodName, null);
    }

    /**
     * @param valueObject
     *            : vo 物件
     * @param methodName
     *            : 方法名稱字串
     * @param noInvokeFields
     *            : 不需調用方法之字串陣列
     * @return 呼叫getter的結果字串
     */
    public static String invokeGetter(Object valueObject, String methodName, String[] noInvokeFields) {

        if (noInvokeFields == null) {
            return invokeGetter(valueObject, methodName);
        }

        String getterResultString = null;
        try {
            Method getterMethod = valueObject.getClass().getMethod(methodName); // package + classname + methodName
            int dotIndex = getterMethod.toString().lastIndexOf(".") + 1;
            String getterMethodOnly = getterMethod.toString().substring(dotIndex); // 切成只有 methodName
            for (String noInvoke : noInvokeFields) {
                String tmp = "get" + (Character.toUpperCase(noInvoke.charAt(0)) + noInvoke.substring(1) + "()").toString();
                if (getterMethodOnly.equals(tmp)) {
                    return null;
                }
            }

            getterResultString = getterMethod.invoke(valueObject).toString();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return getterResultString;
    }

    /**
     * 建立title列
     *
     * @return title列的下一列數 int
     */
    public static int createTitleRow(Sheet excelSheet, String[] titles, CellStyle cellStyle, int titleRowNum , int titleColNum) {
        Row titleRow = excelSheet.createRow(titleRowNum);
        int titleCol = titleColNum;
        for (String title : titles) {
            Cell titleCell = titleRow.createCell(titleCol++);
            titleCell.setCellStyle(cellStyle); // 設置儲存格樣式
            titleCell.setCellValue(title);
        }
        return titleRowNum + 1;
    }

    public static void main(String[] args) {
//		Field[] fields = EmpVO.class.getDeclaredFields();
//		for (Field field : fields) {
//			System.out.println(field.toString());
//		}

//		List<String> fmethods = ExcelUtil.getMethods(EmpVO.class, "get");
//		for (String str : fmethods) {
//			System.out.println(str.toString());
//		}

//		List<String> fmethods = ExcelUtil.getMethods(CourseVO.class, "get");
//		for (String str : fmethods) {
//			System.out.println(str.toString());
//		}

//		List<String> fmethods = ExcelUtil.getMethods(EmpOrgSupportVO.class, "get");
//		for (String str : fmethods) {
//			System.out.println(str.toString());
//		}
    }

}
