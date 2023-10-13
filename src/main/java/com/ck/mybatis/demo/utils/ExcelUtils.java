package com.ck.mybatis.demo.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelUtils {

    public static String getCellStringValue(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return null;
    }

    public static Integer getCellNumericValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return (int) cell.getNumericCellValue();
    }

    public static Boolean getCellBooleanValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return cell.getNumericCellValue() == 1.0;
    }

    public static void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue(Double.valueOf((Integer) value));
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value ? 1 : 0);
        } else {
            throw new IllegalArgumentException("未处理的数据类型: " + value.getClass());
        }
    }
}



