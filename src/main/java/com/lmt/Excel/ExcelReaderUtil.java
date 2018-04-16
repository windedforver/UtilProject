package com.lmt.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

   
    private static List<List<String>> readExcel(String path,int sheetNum,int writeNumber) {
        List<List<String>> lists = new ArrayList<List<String>>();
        try {
            //create file object
            File finalXlsxFile = new File(path);
            //judge file type is xls or xlsx
            Workbook workBook = getWorkbok(finalXlsxFile);  
            //get whitch Sheet of file
            Sheet sheetFirst = workBook.getSheetAt(sheetNum);
            //get the last RowNumber
            int rowNumber = sheetFirst.getLastRowNum();
            //read should for first data row, write should behind for last data row
            Row row = sheetFirst.getRow(4);
               //loop writeNumber times for create data ready for write
                for(int i=1 ;i<writeNumber+1;i++){
                    ArrayList<String> list = new ArrayList<String>();
                     int count =0;
                     Integer temp = -1;
                     for (Cell cell : row) {
                         //suppose all data are String Type
                         cell.setCellType(Cell.CELL_TYPE_STRING);
                         if(count==0){
                         //only auto-increase the first int cloumn
                         temp = Integer.valueOf(cell.getStringCellValue()).intValue() +i+rowNumber-13;
                         list.add(temp.toString());
                         count++;
                         continue;
                         }
                         //other cloumn nothing to change 
                         list.add(cell.getStringCellValue());
                     }
                     lists.add(list);
                }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          
        }
        return lists;
    }

     private static void writeExcel(List<List<String>> dataList,String finalXlsxPath,int sheetNum){  
        OutputStream out = null;  
        try {  
            File finalXlsxFile = new File(finalXlsxPath);  
            Workbook workBook = getWorkbok(finalXlsxFile);  
            Sheet sheet = workBook.getSheetAt(sheetNum);  
            int rowNumber = sheet.getLastRowNum();  
            for (List<String> dataMap :dataList) {  
                Row row = sheet.createRow(rowNumber + 1);  
                int clounmNum =0;
                    for (String strs : dataMap) {
                         Cell cell = row.createCell(clounmNum);  
                        cell.setCellValue(strs);
                        clounmNum++;
                    }
                    rowNumber++;
            }  
            out =  new FileOutputStream(finalXlsxPath);  
            workBook.write(out);  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("write data unsuccess!");  
        } finally{  
            try {  
                if(out != null){  
                    out.flush();  
                    out.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    private static Workbook getWorkbok(File file) throws IOException{  
        Workbook wb = null;  
        FileInputStream in = new FileInputStream(file);  
        if(file.getName().endsWith("xls")){  //Excel 2003  
            wb = new HSSFWorkbook(in);  
        }else if(file.getName().endsWith("xlsx")){  // Excel 2007/2010  
            wb = new XSSFWorkbook(in);  
        }  
        return wb;  
    }
    public static void main(String[] args) {
    	//your file path here: 
        String path = "C:\\Users\\i346914\\Desktop\\aw.xls";
        //insert data number here:
        int writeNumber = 10;
        String fileType = path.substring(path.indexOf("."));
        if(".xls".equals(fileType)){
        	List<List<String>> listSheetOne = readExcel(path,4,writeNumber);
            List<List<String>> listSheetTwo = readExcel(path,6,writeNumber);
            writeExcel(listSheetOne,path,4);
            writeExcel(listSheetTwo,path,6);
        }else{
        	List<List<String>> listSheetOne = readExcel(path,0,writeNumber);
            List<List<String>> listSheetTwo = readExcel(path,1,writeNumber);
            writeExcel(listSheetOne,path,0);
            writeExcel(listSheetTwo,path,1);
        }
        
    }


}