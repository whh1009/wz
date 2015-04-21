package wz.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import wz.util.StringUtil;

import java.io.File;
import java.util.List;

/**
 * Created by wanghonghui on 2015/4/21.
 */
public class ColumnXml2Sql {

    private static Document getDocByText(String xmlStr) throws DocumentException {
        return DocumentHelper.parseText(xmlStr);
    }

    private static Document getDocByPath(String xmlPath) throws DocumentException{
        return new SAXReader().read(new File(xmlPath));
    }

    public static String getSqlByXml(String xmlStr) throws Exception{
        if("".equals(StringUtil.ObjectToString(xmlStr))) return "1=1";
        String sql = "";
        Document doc = getDocByText(xmlStr);
        List<Element> list = doc.selectNodes("//item");
        if(list==null||list.isEmpty()) return "1=1";
        for(Element ele : list) {
            String columnName = ele.attributeValue("columnName");
            String columnVal = StringUtil.ObjectToString(ele.attributeValue("columnVal"));
            if(!"".equals(columnVal)) {
                if("book_publish_count".equals(columnName)||
                        "book_print_count".equals(columnName)||
                        "book_copyright_word_count".equals(columnName)||
                        "book_image_count".equals(columnName)||
                        "book_table_count".equals(columnName)||
                        "book_paper_price".equals(columnName)||
                        "book_paper_dollar_price".equals(columnName)||
                        "book_ebook_price".equals(columnName)||
                        "book_ebook_dollar_price".equals(columnName)||
                        "book_neiwen_page_count".equals(columnName)||
                        "book_image_page_count".equals(columnName)||
                        "book_publish_page_count".equals(columnName)||
                        "book_weight".equals(columnName)||
                        "book_news_publish_count".equals(columnName)||
                        "book_news_distribute_count".equals(columnName)||
                        "book_news_count".equals(columnName)||
                        "book_news_shangjiao_count".equals(columnName)||
                        "book_evaluate_weight".equals(columnName)) {
                    sql+= columnName + " = " + columnVal + " and ";
                } else {
                    sql+= columnName + " like '%"+mysqlRep(columnVal)+"%' and ";
                }
            }
        }
        if(sql.endsWith(" and ")) {
            sql = sql.substring(0, sql.length()-4);
        }
        return sql;
    }

    /**
     * mysql 转义替换
     * @param str
     * @return
     */
    private static String mysqlRep(String str) {
        return str
                .replace("_", "\\_")
                .replace("%", "\\%")
                .replace("\"", "\\\"")
                .replace("'", "\\'")

                ;
    }
}
