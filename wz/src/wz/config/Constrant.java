package wz.config;

/**
 * 常量定义
 * @author wanghonghui
 *
 */
public class Constrant {
	/**
	 * 分页指定每页显示条数
	 */
	public final static int PAGE_SIZE = 15;

	/**
	 * 默认显示字段
	 */
	public final static String SHOW_COLUMN = "book_serial_number, book_name_cn, book_editor, book_language, book_paper_price";

	/**
	 * 默认导出字段
	 */
	public final static String EXPORT_COLUMN = "book_serial_number, book_name_cn, book_editor, book_language, book_paper_price";

	/**
	 * 默认检索字段
	 */
	public final static String SEARCH_COLOMN = "book_serial_number, book_name_cn, book_editor, book_language, book_paper_price";
}
