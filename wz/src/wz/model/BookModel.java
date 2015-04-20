package wz.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import wz.config.Constrant;

/**
 * Created by Administrator on 2015/4/18.
 */
public class BookModel extends Model<BookModel> {
    public static final BookModel dao = new BookModel();

    public Page<BookModel> getBookList(int pageNumber, String showColumn) throws Exception{
        if("".equals(showColumn)) {
            showColumn = Constrant.SHOW_COLUMN;
        }
        return this.paginate(pageNumber, Constrant.PAGE_SIZE, "select " + showColumn, " from wz_book where book_del_flag = 0");
    }
}
