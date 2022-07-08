package com.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.book.model.Book;
import com.book.util.StringUtil;

/**
 * 图书Dao类
 * @author sdssy
 *
 */
public class BookDao {

	/**
	 * 图书添加
	 * @param con
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public int add(Connection con, Book book)throws Exception{
		String sql= "insert into t_book values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setString(2, book.getAuthor());
		pstmt.setString(3, book.getSex());
		pstmt.setFloat(4, book.getPrice());
		pstmt.setInt(5, book.getBookTypeId());
		pstmt.setString(6, book.getBookDesc());
		pstmt.setInt(7, book.getInStock());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 图书信息查询
	 * @param con
	 * @return
	 */
	public ResultSet list(Connection con, Book book)throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_book b, t_bookType bt where b.bookTypeId=bt.id");
		if(StringUtil.isNotEmpty(book.getBookName())) {
			sb.append(" and b.bookName like '%"+book.getBookName()+"%'");
		}
		if(StringUtil.isNotEmpty(book.getAuthor())) {
			sb.append(" and b.author like '%"+book.getAuthor()+"%'");
		}
		if(book.getBookTypeId() != null && book.getBookTypeId() != -1) {
			sb.append(" and b.bookTypeId="+book.getBookTypeId());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	/**
	 * 图书信息删除
	 * @param con
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Connection con, String id)throws Exception{
		String sql = "delete from t_book where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 图书信息修改
	 * @param con
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public int update(Connection con, Book book)throws Exception{
		String sql = "update t_book set bookName=?,author=?,sex=?,price=?,bookDesc=?,bookTypeId=?,inStock=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setString(2, book.getAuthor());
		pstmt.setString(3, book.getSex());
		pstmt.setFloat(4, book.getPrice());
		pstmt.setString(5, book.getBookDesc());
		pstmt.setInt(6, book.getBookTypeId());
		pstmt.setInt(7, book.getInStock());
		pstmt.setInt(8, book.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 图书库存更新
	 * @param con
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public int inStockUpdate(Connection con, Book book, int inStock, boolean add)throws Exception{
		String sql = "update t_book set inStock=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		if(add) {
			pstmt.setInt(1, inStock + 1);
		}else {
			pstmt.setInt(1, inStock - 1);
		}
		pstmt.setInt(2, book.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 查询当前库存
	 * @param con
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public ResultSet inStocklist(Connection con, Book book)throws Exception{
		String sql = "select * from t_book where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, book.getId());
		return pstmt.executeQuery();
	}
	
	/**
	 * 指定图书类别下是否存在图书
	 * @param con
	 * @param bookTypeId
	 * @return
	 * @throws Exception
	 */
	public boolean existBookByBookTypeId(Connection con, String bookTypeId)throws Exception{
		String sql = "select * from t_book where bookTypeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, bookTypeId);
		ResultSet rs =  pstmt.executeQuery();
		return rs.next();
	}
}
