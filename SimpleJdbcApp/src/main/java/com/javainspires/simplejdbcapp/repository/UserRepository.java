package com.javainspires.simplejdbcapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.javainspires.simplejdbcapp.form.LoginForm;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<String> getAllUserNames() {
		List<String> usernameList = new ArrayList<>();
		usernameList.addAll(jdbcTemplate.queryForList("select product_gst from producttable;", String.class));
		return usernameList;
	}

	public int createUser(LoginForm data) {
		try {
			String sql = "insert into producttable(product_name,product_price,product_gst) values(?,?,?)";
			int value = jdbcTemplate.update(sql, data.getProductName(), data.getProductPrice(), data.getProductGst());
			return value;
		} catch (Exception e) {
			return 0;
		}
	}
	/*
	 * public void updateUser() { String
	 * sql="update producttable set product_name=? where product_code=?";
	 * jdbcTemplate.update(sql,"trimmer",5);
	 * 
	 * }
	 * 
	 * public void deleteUser() { String
	 * sql="delete from user where product_code=?"; jdbcTemplate.update(sql,6); }
	 */

	public List<LoginForm> getProducts(String key){
		String sql = "select * from producttable ";
		if(!(key==null || key.equals(""))) {
			sql+="where product_code like '%"+key+"%' || product_name like '%"+key+"%'";
		}
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(LoginForm.class));
	}

	public List<LoginForm> getProductList(int[] codes) {
		String sql = "select * from producttable ";
		if(codes.length>0) {
			sql+="where product_code in (";
			for(int code : codes){sql+=code+",";}
			sql+="0) ";
		}
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(LoginForm.class));
	}

}
