import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class excel {

    public static void excel_save_xlsx(ArrayList<ani_object> database,String path) throws FileNotFoundException, IOException
    {
        XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Sheet1");
		XSSFRow row=sheet.createRow(0); 
        
		XSSFCell cell = row.createCell(0);
        cell.setCellValue("Title");
        
        cell = row.createCell(1);
        cell.setCellValue("Episodes");
        
        cell = row.createCell(2);
        cell.setCellValue("Air-Day");
        
        for (int r=0;r<database.size();r++)
		{
            row = sheet.createRow(r+1);
                cell = row.createCell(0);
				cell.setCellValue(database.get(r).show_title);
                cell = row.createCell(1);
				cell.setCellValue(database.get(r).count_episodes);
                cell = row.createCell(2);
				cell.setCellValue(database.get(r).day_string()); 
		}
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
   
        File file = new File(path);
        
        if(file.exists()&&file.isDirectory())
        {
            FileOutputStream fileOut = new FileOutputStream(path+"new_export.xlsx"); 
            wb.write(fileOut);
            drawWindow.saveLabel(true);
        }
        else
        { 
            FileOutputStream fileOut = new FileOutputStream("D://new_export.xlsx");
            wb.write(fileOut);
            drawWindow.saveLabel(false);
        }
       
        databaseManage.clear_db();
       }
}
