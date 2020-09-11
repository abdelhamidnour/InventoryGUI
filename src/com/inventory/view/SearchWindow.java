package com.inventory.view;

import java.awt.*;
import java.awt.ComponentOrientation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import com.inventory.Dao.ProductDao;
import com.inventory.contorl.TableHelper;

public class SearchWindow extends JFrame {
	
	private static ResourceBundle labels;
	JTextField search = new JTextField(20);
	private ProductDao pdao;

	public SearchWindow(Locale currentLocale, ProductDao pdao, ResourceBundle labels) {
		this.pdao = pdao;
		this.labels = labels;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				createAndShowGUI(currentLocale);
			}
		});
	}

	protected void createAndShowGUI(Locale currentLocale) {

		// create a JTable
		JTable table = TableHelper.createTable(pdao, labels);
		search.addKeyListener(new KeyAdapter() {

		    @Override
		    public void keyReleased(KeyEvent event) {
				String searchText = (String) search.getText();
				searchText = searchText.trim().toLowerCase();
				// if there are matches then add the search text in combo for later use
				TableHelper.searchInTable(table, searchText);
		    }
		});
		
		// wrap comboBox in a panel
		JPanel panel = new JPanel();
		panel.add(new JLabel(labels.getString("search")));
		panel.add(search);

		setSize(1800, 1000);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				setVisible(false);
				MainApp.showWindow();
			}
		});
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		add(new JScrollPane(table));
		applyComponentOrientation(ComponentOrientation.getOrientation(currentLocale));
		setVisible(true);

	}

	

}
