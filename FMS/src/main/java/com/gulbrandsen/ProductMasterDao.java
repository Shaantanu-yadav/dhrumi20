package com.gulbrandsen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMasterDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/product_master";
	private String jdbcUsername = "root";
	private String jdbcPassword = "1234";
	private String jdbcDriver = "com.mysql.jdbc.Driver";

	String listsql = "select* from product_master";
	String insertsql ="insert into product_master" + "(Product Code,Product Name,Product group,Status,Sap) ";
	String updatesql = "update product_master set name =?";
	String deletesql = "delete from product_master where id=?";
	
private Connection conn;
	
	
	public ProductMasterDao(Connection conn) {
		super();
		this.conn = conn;
	}
	//insert
	
	public void insertRecord(Productmaster productMaster) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(insertsql);
		ps.setInt(1, productMaster.getId());
		ps.setString(2, productMaster.getProductGroup());
		ps.setString(3, productMaster.getProductCode());
		ps.setString(4, productMaster.getProductName());
		ps.setString(5, productMaster.getSap());
		ps.setString(6, productMaster.getStatus());

		
	}
	public List<Productmaster> selectAll() throws SQLException {
		List<Productmaster> showRecord = new ArrayList<>();
		Productmaster pro = null;
		String sql ="Select product_id, product_abbr, product_name, ifnull(group_name,'') as group_name , status , ifnull(Sap,'') as Sap from fms.product_master pm left join product_group_mst pg on pm.product_group = pg.id";
	
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();   
		while (rs.next()) {
			pro = new Productmaster();
				pro.setId(rs.getInt("product_id"));
				pro.setProductGroup(rs.getString("group_name"));
				pro.setProductCode(rs.getString("product_abbr"));
				pro.setProductName(rs.getString("product_name"));
				pro.setStatus(rs.getString("status"));
				pro.setSap(rs.getString("Sap"));
			
			showRecord.add(pro);
			
		} 
		return showRecord;
	}
	public Productmaster getDataById(int id) {

		Productmaster pro= null;
		
		try {
			String sql = "Select product_id, product_abbr, ifnull(product_group,'') as product_group , product_name, ifnull(group_name,'') as group_name , status , ifnull(Sap,'') as Sap from fms.product_master pm left join product_group_mst pg on pm.product_group = pg.id where pm.product_id=?";
			System.out.println("Update Query" + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			System.out.println("Update Query PS1:" + ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

			    pro = new Productmaster();
			    pro.setProductGroup(rs.getString("product_group"));
				pro.setProductCode(rs.getString("product_abbr"));
				pro.setProductName(rs.getString("product_name"));
				pro.setStatus(rs.getString("status"));
				pro.setSap(rs.getString("Sap"));
				pro.setId(id);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return pro;}
}