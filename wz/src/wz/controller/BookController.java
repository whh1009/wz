package wz.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import wz.config.Constrant;
import wz.model.BookModel;

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
        this.setAttr("showColumn", Constrant.SHOW_COLUMN);
        render("BookListMan.jsp");
    }

    public void getBookListByPara() throws Exception {
        int pageNumber = getParaToInt("pageNumber", 1);
        renderJson(JsonKit.toJson(BookModel.dao.getBookList(pageNumber)));
    }
}
