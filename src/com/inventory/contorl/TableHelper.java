package com.inventory.contorl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

import com.inventory.Dao.ProductDao;
import com.inventory.model.Product;

public class TableHelper {

	public static JTable createTable(ProductDao pdao, ResourceBundle labels) {
		// prepare columns
		Vector<String> columns = new Vector<>();
		columns.add(labels.getString("NAME_STRING"));
		columns.add(labels.getString("TYPE_STRING"));
		columns.add(labels.getString("TOTAL_PRICE_STRING"));
		columns.add(labels.getString("PAID_PRICE_STRING"));
		columns.add(labels.getString("Remain_PRICE_STRING"));
		columns.add(labels.getString("OWNER_STRING"));
		columns.add(labels.getString("WORKER_STRING"));
		columns.add(labels.getString("CAR_TYPE"));
		columns.add(labels.getString("YEAR_STRING"));
		columns.add(labels.getString("phoneNum"));
		columns.add(labels.getString("carNum"));
		columns.add(labels.getString("notes"));
		columns.add("photo");

		// prepare rows
		Vector<Vector<Object>> rows = new Vector();
		for (Product product : pdao.getAllProduct()) {
			Vector<Object> row = new Vector<>();

			row.add(product.getName());
			row.add(product.getType());
			row.add(product.getTotal());
			row.add(product.getPaid());
			row.add(product.getRemain());
			row.add(product.getOwner());
			row.add(product.getWorker());
			row.add(product.getCarType());
			row.add(product.getYear());
			row.add(product.getPhoneNumber());
			row.add(product.getCarNum());
			row.add(product.getNote());
			BufferedImage wPic = null;
			try {

				String path = product.getImage();
				
				wPic = ImageIO.read(new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageIcon copyIcon = new ImageIcon(wPic.getScaledInstance(100, 100, 0));
			row.add(copyIcon);
			rows.add(row);
		}
		Object[][] data = new Object[rows.size()][13];

		for (int i = 0; i < rows.size(); i++) {

			data[i] = rows.get(i).toArray();

		}

		DefaultTableModel model = new DefaultTableModel(data, columns.toArray()) {
			// Returning the Class of each column will allow different
			// renderers to be used based on Class
			public Class getColumnClass(int column) {
				return (column == 12) ? Icon.class : Object.class;
			}
		};
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(true);
		table.setRowHeight(150);
		table.setEnabled(false);
		return table;
	}

	public static boolean searchInTable(JTable table, String searchText) {
		if (searchText == null) {
			return false;
		}
		int beforeFilterRowCount = table.getRowCount();
		RowSorter<? extends TableModel> rs = table.getRowSorter();
		if (rs == null) {
			table.setAutoCreateRowSorter(true);
			rs = table.getRowSorter();
		}
		TableRowSorter<? extends TableModel> rowSorter = (TableRowSorter<? extends TableModel>) rs;
		if (searchText.length() == 0) {
			rowSorter.setRowFilter(null);
		} else {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(searchText), 9, 10));
		}
		int afterFilterRowCount = table.getRowCount();
		return afterFilterRowCount != 0 && afterFilterRowCount != beforeFilterRowCount;
	}
}