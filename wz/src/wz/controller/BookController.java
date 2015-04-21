package wz.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import wz.config.Constrant;
import wz.model.BookModel;
import wz.service.ColumnXml2Sql;
import wz.service.CreateExcel;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2015/4/18.
 */
public class BookController extends Controller{
    Logger log = Logger.getLogger("");


    public void addBookPage() throws Exception {
        render("AddBook.jsp");
    }

    public void bookListControl() throws Exception {
        setAttr("showColumn", Constrant.SHOW_COLUMN);
        render("BookListMan.jsp");
    }

    public void getBookListByPara() throws Exception {
        int pageNumber = getParaToInt("pageNumber", 1);
        String queryStr = ColumnXml2Sql.getSqlByXml(getPara("queryStr"));
        renderJson(JsonKit.toJson(BookModel.dao.getBookList(pageNumber, (String)getSessionAttr("showColumn"), queryStr)));
    }

    public void bookInfoExportExcel() throws Exception{
        String excelPath = PathKit.getWebRootPath()+"\\excel";
        new File(excelPath).deleteOnExit();
        new File(excelPath).mkdirs();
        String queryStr = ColumnXml2Sql.getSqlByXml(getPara("queryStr"));
        String explortColumn = (String)getSessionAttr("exportColumn");
        CreateExcel.BookListToExcel(BookModel.dao.getBookList(explortColumn, queryStr), explortColumn, excelPath+"\\excel.xlsx");
        System.out.print("路径3：" + getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/");
        //renderJson("1");
        excelPath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/excel/excel.xlsx";
        renderFile(excelPath);
    }

    public void downExportExcel()  throws Exception {

    }
}
