package wz.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import wz.config.ColumnMap;
import wz.model.BookModel;
import wz.util.StringUtil;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by wanghonghui on 2015/4/21.
 */
public class CreateExcel {
    public static void BookListToExcel(List<BookModel> bookList, String exportColumn, String outPath) throws Exception {
        if (bookList == null || bookList.isEmpty()) {
            return;
        } else {
            Workbook wb = new XSSFWorkbook();
            FileOutputStream fos = null;
            fos = new FileOutputStream(outPath);
            sheet(wb, bookList, exportColumn);
            wb.write(fos);
            fos.flush();
            fos.close();
        }
    }

    private static void sheet(Workbook wb, List<BookModel> bookList, String exportColumn) {
        Sheet helloSheet = wb.createSheet("Sheet1");
        Row row1 = helloSheet.createRow(0);
        String[] exportColumnArray = exportColumn.split(",");
        for (int i = 0; i < exportColumnArray.length; i++) {
            row1.createCell(i).setCellValue(ColumnMap.getBookTableCnByColumnName(exportColumnArray[i].trim()));
        }
        for (int i = 0; i < bookList.size(); i++) {
            Row row = helloSheet.createRow(i + 1);
            for (int j = 0; j < exportColumnArray.length; j++) {
                row.createCell(j).setCellValue(StringUtil.ObjectToString(bookList.get(i).get(exportColumnArray[j].trim())));
            }
        }

    }
}
