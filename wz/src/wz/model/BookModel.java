package wz.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import wz.config.Constrant;

import java.util.List;

/**
 * Created by Administrator on 2015/4/18.
 */
public class BookModel extends Model<BookModel> {
    public static final BookModel dao = new BookModel();

    /**
     * 根据条件检索图书列表
     * @param pageNumber
     * @param showColumn
     * @param queryStr
     * @return
     * @throws Exception
     */
    public Page<BookModel> getBookList(int pageNumber, String showColumn, String queryStr) throws Exception{
        if("".equals(showColumn)) {
            showColumn = Constrant.SHOW_COLUMN;
        }
        return this.paginate(pageNumber, Constrant.PAGE_SIZE, "select " + showColumn, " from wz_book where book_del_flag = 0 and " + queryStr);
    }

    /**
     * 根据条件查询导出的图书
     * @param exportColumn
     * @param queryStr
     * @return
     * @throws Exception
     */
    public List<BookModel> getBookList(String exportColumn, String queryStr) throws Exception {
        if("".equals(exportColumn)) {
            exportColumn = Constrant.EXPORT_COLUMN;
        }
        return find("select " + exportColumn + " from wz_book where book_del_flag=0 and " + queryStr);
    }


}
