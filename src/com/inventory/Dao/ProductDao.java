package com.inventory.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.inventory.model.Product;

public class ProductDao {
	private Statement stmt;
	private ResultSet rs;
	private Connection con;
	private List list;

	public ProductDao() {
		ResourceBundle env= ResourceBundle.getBundle("resources.persistence-mysql");

		try {
			Class.forName(env.getString("jdbc.driver"));
			con = DriverManager.getConnection(env.getString("jdbc.url"),env.getString("jdbc.user"), env.getString("jdbc.password"));
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void add(Product p) {
		try {
			stmt.executeUpdate("insert into `PRODUCT` values ('"+p.getName()+"','"+p.getType()+"','"+p.getTotal()+"','"
		+p.getPaid()+"','"+p.getRemain()+"','"+p.getOwner()+"','"+p.getWorker()+"','"+p.getYear()+"','"+p.getPhoneNumber()
		+"','"+p.getCarNum()+"','"+p.getNote()+"','"+p.getImage()+"','"+p.getCarType()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<String> findByPhoneOrCar() {
		list = new ArrayList<String>();
		try {
			rs = stmt.executeQuery("select phoneNumber,carNum from product");
			while (rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<Product> getProduct(String id) {
		list = new ArrayList<Product>();
		try {
			rs = stmt.executeQuery("select * from product where phoneNumber ='" + id + "' or carNum='" + id + "'");
			Product p = null;
			int numberOfColumns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				p = new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12),rs.getString(13));

				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
	public List<Product> getAllProduct() {
		list = new ArrayList<Product>();
		try {
			rs = stmt.executeQuery("select * from product");
			Product p = null;
			int numberOfColumns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				p = new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12),rs.getString(13));
				list.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
}
