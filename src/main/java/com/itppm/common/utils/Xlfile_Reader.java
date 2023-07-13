package com.itppm.common.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.Calendar;

public class Xlfile_Reader {

    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public Xlfile_Reader(String path) {

        this.path = path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    // returns row count from the excel sheet
    public int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return 0;
        else {
            sheet = workbook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }

    }

    // returns cell's data from excel sheet
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {

                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(col_Num);

            if (cell == null)
                return "";
            if (cell.getCellType() ==  CellType.STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

                }

                return cellText;
            } else if (cell.getCellType() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());

        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }

    // returns the data from a cell
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);

            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(colNum);
            if (cell == null)
                return "";

            if (cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

                    // System.out.println(cellText);

                }

                return cellText;
            } else if (cell.getCellType() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
        }
    }

    // returns true if data is set successfully else false
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                // System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            // cell style
            // CellStyle cs = workbook.createCellStyle();
            // cs.setWrapText(true);
            // cell.setCellStyle(cs);
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if Integer data is set successfully else false
    public boolean setIntCellData(String sheetName, String colName, int rowNum, int data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                // System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            // cell style
            // CellStyle cs = workbook.createCellStyle();
            // cs.setWrapText(true);
            // cell.setCellStyle(cs);
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if Integer data is set successfully else false
    public boolean setFloatCellData(String sheetName, String colName, int rowNum, float data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                // System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            // cell style
            // CellStyle cs = workbook.createCellStyle();
            // cs.setWrapText(true);
            // cell.setCellStyle(cs);
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if sheet is created successfully else false
    public boolean addSheet(String sheetname) {

        FileOutputStream fileOut;
        try {
            workbook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if sheet is removed successfully else false if sheet does not exist
    public boolean removeSheet(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return false;

        FileOutputStream fileOut;
        try {
            workbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // find whether sheets exists
    public boolean isSheetExist(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            if (index == -1)
                return false;
            else
                return true;
        } else
            return true;
    }

    // returns number of columns in a sheet
    public int getColumnCount(String sheetName) {
        // check if sheet exists
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null)
            return -1;

        return row.getLastCellNum();

    }

    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;

    }

    // to run this on stand alone
    public static void main(String arg[]) throws IOException {

        // System.out.println(filename);
        Xlfile_Reader datatable = null;

        String abc = "abcd";
        datatable = new Xlfile_Reader("C:\\Selenium2.0\\app\\test\\Framework\\AutomationBvt_Hybrid\\src\\config\\db_data.xlsx");
        for (int col = 0; col < datatable.getColumnCount("db_data"); col++) {
            System.out.println(datatable.getCellData("db_data", col, 1));
        }
        datatable.setCellData("Evaluation", "generated_call_id", 2, abc);
    }

}
