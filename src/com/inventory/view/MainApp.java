package com.inventory.view;

import java.awt.*;
import java.awt.Font;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.inventory.Dao.ProductDao;
import com.inventory.model.Product;

public class MainApp extends JPanel implements PropertyChangeListener {

	private static JFrame frame;

	protected String newline = "\n";
	protected Action englishUSLocaleAction, arabicSaudiArabiaLocaleAction;
	private static URL logoPath;
	private static ResourceBundle labels;
	protected String ImagePath = "";
	static Locale currentLocale;
	// Labels to identify the fields
	private JLabel name;
	private JLabel type;
	private JLabel totalPrice;
	private JLabel paidPrice;
	private JLabel remainPrice;
	private JLabel ownerName;
	private JLabel workerName;
	private JLabel productionYear;
	private JLabel phoneNum;
	private JLabel carNum;
	private JLabel notes;
	private JLabel carPic;
	private JLabel clock;
	private JLabel cartype;

	private JButton insert;
	private JButton search;

	private JButton upload;

	// Fields for data entry
	private JFormattedTextField nameField;
	private JFormattedTextField typeField;
	private JFormattedTextField totalPriceField;
	private JFormattedTextField paidPriceField;
	private JFormattedTextField remainPriceField;
	private JFormattedTextField ownerNameField;
	private JFormattedTextField workerNameField;
	private JFormattedTextField productionYearField;
	private JFormattedTextField phoneNumField;
	private JFormattedTextField carNumField;
	private JFormattedTextField carTypeField;

	private JTextArea note;
	private JScrollPane notesArea;
	private ProductDao pdao = new ProductDao();
	// Formats to format and parse numbers
	private static Currency currencyInstance;
	private NumberFormat numFormat;

	public MainApp() {
		showWindow();
	}

	public MainApp(Locale currentLocale) {
		logoPath = this.getClass().getResource("../../../logo.png");
		currencyInstance = Currency.getInstance(currentLocale);
		// System.out.println(pdao.getProduct("admin"));

		englishUSLocaleAction = new ChangeLocaleAction("English, United States locale, en-US",
				"This is the English locale", new Integer(KeyEvent.VK_U),
				new Locale.Builder().setLanguage("en").setRegion("US").build());

		arabicSaudiArabiaLocaleAction = new ChangeLocaleAction("Arabic, Saudi Arabia locale, ar-SA",
				"This is the Arabic locale", new Integer(KeyEvent.VK_S),
				new Locale.Builder().setLanguage("ar").setRegion("SA").build());

		 setUpFormats();

		// double payment = computePayment(amount, rate, numPeriods);

		// Create the labels.

		labels = ResourceBundle.getBundle("resources.Resources", currentLocale);

		name = new JLabel(labels.getString("NAME_STRING"));
		type = new JLabel(labels.getString("TYPE_STRING"));
		totalPrice = new JLabel(MessageFormat.format(labels.getString("TOTAL_PRICE_STRING"),
				currencyInstance.getDisplayName(currentLocale), currencyInstance.getSymbol(currentLocale)));
		paidPrice = new JLabel(labels.getString("PAID_PRICE_STRING"));
		remainPrice = new JLabel(labels.getString("Remain_PRICE_STRING"));
		ownerName = new JLabel(labels.getString("OWNER_STRING"));
		workerName = new JLabel(labels.getString("WORKER_STRING"));

		productionYear = new JLabel(labels.getString("YEAR_STRING"));
		phoneNum = new JLabel(labels.getString("phoneNum"));
		carNum = new JLabel(labels.getString("carNum"));
		notes = new JLabel(labels.getString("notes"));
		carPic = new JLabel(labels.getString("carPic"));
		cartype = new JLabel(labels.getString("CAR_TYPE"));

		insert = new JButton(labels.getString("insert"));
		insert.addActionListener(new ActionButtons());

		search = new JButton(labels.getString("search"));
		search.addActionListener(new ActionButtons());

		upload = new JButton(labels.getString("upload"));
		upload.addActionListener(new ActionButtons());

		// Create the text fields and set them up.

		nameField = new JFormattedTextField();
		nameField.setColumns(10);
		nameField.addPropertyChangeListener("value", this);

		typeField = new JFormattedTextField();
		typeField.setColumns(10);
		typeField.addPropertyChangeListener("value", this);

		totalPriceField = new JFormattedTextField(numFormat);
		totalPriceField.addPropertyChangeListener("value", this);

		paidPriceField = new JFormattedTextField(numFormat);
		paidPriceField.setColumns(10);
		paidPriceField.addPropertyChangeListener("value", this);

		remainPriceField = new JFormattedTextField(numFormat);
		remainPriceField.setColumns(10);
		remainPriceField.addPropertyChangeListener("value", this);

		ownerNameField = new JFormattedTextField();
		ownerNameField.setColumns(10);
		ownerNameField.addPropertyChangeListener("value", this);

		workerNameField = new JFormattedTextField();
		workerNameField.setColumns(10);
		workerNameField.addPropertyChangeListener("value", this);

		carTypeField = new JFormattedTextField();
		carTypeField.setColumns(10);
		carTypeField.addPropertyChangeListener("value", this);

		productionYearField = new JFormattedTextField(numFormat);
		productionYearField.setColumns(4);

		phoneNumField = new JFormattedTextField(numFormat);
		phoneNumField.setColumns(10);

		carNumField = new JFormattedTextField(numFormat);
		carNumField.setColumns(10);

		note = new JTextArea(5, 5);
		note.setLineWrap(true);
		note.setWrapStyleWord(true);
		note.setFont(new Font("Serif", Font.PLAIN, 12));
		notesArea = new JScrollPane(note);
//		notesArea.setMaximumSize(new Dimension(5,5));
		// Tell accessibility tools about label/textfield pairs.

		name.setLabelFor(nameField);
		type.setLabelFor(typeField);
		totalPrice.setLabelFor(totalPriceField);
		paidPrice.setLabelFor(paidPriceField);
		remainPrice.setLabelFor(remainPriceField);
		ownerName.setLabelFor(ownerNameField);
		workerName.setLabelFor(workerNameField);
		productionYear.setLabelFor(productionYearField);
		phoneNum.setLabelFor(phoneNumField);
		carNum.setLabelFor(carNumField);
		notes.setLabelFor(notesArea);
		cartype.setLabelFor(carTypeField);
		setLayout(new BorderLayout());

		JPanel logoPane = new JPanel(new BorderLayout());
		logoPane.setSize(100, 100);
		BufferedImage wPic;
		try {
			wPic = ImageIO.read(logoPath);
			Image scaledImage = wPic.getScaledInstance(logoPane.getWidth(), logoPane.getHeight(), Image.SCALE_SMOOTH);
			JLabel wIcon = new JLabel(new ImageIcon(scaledImage));
			JLabel windowName = new JLabel(labels.getString("WindowString"));
			windowName.setFont(new Font("Serif", Font.PLAIN, 22));
			windowName.setHorizontalAlignment(JLabel.CENTER);
			windowName.setVerticalAlignment(JLabel.CENTER);
			windowName.setVerticalTextPosition(JLabel.CENTER);
			this.setBorder(new EtchedBorder());
			logoPane.add(wIcon, BorderLayout.CENTER);
			logoPane.add(windowName, BorderLayout.SOUTH);

		} catch (IOException e) {
			e.printStackTrace();
		}
		add(logoPane, BorderLayout.NORTH);

		// Layout the labels and text fieds in a GridLayout
		JPanel mainFiledsAndLabel = new JPanel(new BorderLayout());
		JPanel labelsAndFieldsPane = new JPanel(new GridLayout(12, 2, 10, 10));
		labelsAndFieldsPane.add(name);
		labelsAndFieldsPane.add(nameField);
		labelsAndFieldsPane.add(type);
		labelsAndFieldsPane.add(typeField);
		labelsAndFieldsPane.add(totalPrice);
		labelsAndFieldsPane.add(totalPriceField);
		labelsAndFieldsPane.add(paidPrice);
		labelsAndFieldsPane.add(paidPriceField);
		labelsAndFieldsPane.add(remainPrice);
		labelsAndFieldsPane.add(remainPriceField);
		labelsAndFieldsPane.add(ownerName);
		labelsAndFieldsPane.add(ownerNameField);
		labelsAndFieldsPane.add(workerName);
		labelsAndFieldsPane.add(workerNameField);
		labelsAndFieldsPane.add(cartype);
		labelsAndFieldsPane.add(carTypeField);
		labelsAndFieldsPane.add(productionYear);
		labelsAndFieldsPane.add(productionYearField);
		labelsAndFieldsPane.add(phoneNum);
		labelsAndFieldsPane.add(phoneNumField);
		labelsAndFieldsPane.add(carNum);
		labelsAndFieldsPane.add(carNumField);
		labelsAndFieldsPane.add(notes);
		// labelsAndFieldsPane.add(notesArea);
		labelsAndFieldsPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		mainFiledsAndLabel.add(labelsAndFieldsPane, BorderLayout.CENTER);
		mainFiledsAndLabel.add(notesArea, BorderLayout.SOUTH);

		add(mainFiledsAndLabel, BorderLayout.CENTER);
		JPanel mainButtons = new JPanel(new BorderLayout());

		JPanel buttonsPane = new JPanel(new GridLayout(2, 3, 10, 10));
		buttonsPane.add(new JLabel(""));
		buttonsPane.add(upload);

		buttonsPane.add(insert);
		buttonsPane.add(search);
		mainButtons.add(buttonsPane, BorderLayout.CENTER);
		Calendar now = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a", currentLocale);

		clock = new JLabel(dateFormat.format(now.getTime()));
		clock.setBounds(100, 100, 125, 125);
		mainButtons.add(clock, BorderLayout.SOUTH);

		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar now = Calendar.getInstance();
				clock.setText(dateFormat.format(now.getTime()));
			}
		}).start();

		add(mainButtons, BorderLayout.SOUTH);

	}

	public JMenuBar createMenuBar() {
		JMenuItem menuItem = null;
		JMenuBar menuBar;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Create the first menu.
		JMenu mainMenu = new JMenu(labels.getString("LOCALE"));

		Action[] actions = { englishUSLocaleAction, arabicSaudiArabiaLocaleAction };

		for (int i = 0; i < actions.length; i++) {
			menuItem = new JMenuItem(actions[i]);
			// menuItem.setIcon(null); // arbitrarily chose not to use icon
			mainMenu.add(menuItem);
		}

		// Set up the menu bar.
		menuBar.add(mainMenu);
		return menuBar;
	}

	class ChangeLocaleAction extends AbstractAction {
		private String actionDesc;
		private Locale currentLocale;

		public ChangeLocaleAction(String textArg, String descArg, Integer mnemonicArg, Locale localeArg) {
			super(textArg);
			actionDesc = descArg;
			currentLocale = localeArg;
			putValue(SHORT_DESCRIPTION, descArg);
			putValue(MNEMONIC_KEY, mnemonicArg);
		}

		public void actionPerformed(ActionEvent e) {
			createAndShowGUI(currentLocale);
		}
	}

	class ActionButtons implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == insert)
				ValidateAndInsert();
			else if (e.getSource() == upload) {
				try {
					openFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (e.getSource() == search)
				searchWindow();

		}

	}

	/** Called when a field's "value" property changes. */
	public void propertyChange(PropertyChangeEvent e) {

		Object source = e.getSource();
		if (source == paidPriceField && !(totalPriceField.getText().equals("")) && totalPriceField.getText() != null
				&& !(paidPriceField.getText().equals("")) && paidPriceField.getText() != null) {
			double total = Double.parseDouble(totalPriceField.getText().replace(",", ""));
			double paid = Double.parseDouble(paidPriceField.getText().replace(",", ""));
			double remain = total - paid;
			remainPriceField.setValue(remain);
		}

	}

	void ValidateAndInsert() {

		if (nameField.getText().equals("") || nameField.getText() == null || totalPriceField.getText().equals("")
				|| totalPriceField.getText() == null || paidPriceField.getText().equals("")
				|| paidPriceField.getText() == null || remainPriceField.getText().equals("")
				|| remainPriceField.getText() == null || ownerNameField.getText().equals("")
				|| ownerNameField.getText() == null || workerNameField.getText().equals("")
				|| workerNameField.getText() == null || productionYearField.getText().equals("")
				|| productionYearField.getText() == null || phoneNumField.getText().equals("")
				|| phoneNumField.getText() == null || carNumField.getText().equals("") || carNumField.getText() == null
				|| typeField.getText().equals("") || typeField.getText() == null || carTypeField.getText().equals("")
				|| carTypeField.getText() == null) {
			JOptionPane.showMessageDialog(this, "Please Validate All fields to proceed");
			return;
		}
		if (ImagePath.equals("") || ImagePath == null) {
			int proceed = JOptionPane.showConfirmDialog(this,
					"kindly note you didn't upload a Picture!!\n would you to continue?");
			if (proceed == JOptionPane.NO_OPTION || proceed == JOptionPane.CANCEL_OPTION)
				return;

		}

		Product product = new Product(nameField.getText(), typeField.getText(), totalPriceField.getText(),
				paidPriceField.getText(), remainPriceField.getText(), ownerNameField.getText(),
				workerNameField.getText(), productionYearField.getText(), phoneNumField.getText(), typeField.getText(),
				note.getText(), ImagePath.replace("\\","\\\\"),carTypeField.getText());
		pdao.add(product);
		nameField.setText("");
		typeField.setText("");
		totalPrice.setText("");
		paidPriceField.setText("");
		remainPriceField.setText("");
		ownerNameField.setText("");
		workerNameField.setText("");
		productionYearField.setText("");
		phoneNumField.setText("");
		typeField.setText("");
		note.setText("");
		ImagePath="";
		carTypeField.setText("");
	}

	void searchWindow() {
		frame.setVisible(false);
		SearchWindow sw = new SearchWindow(currentLocale, pdao,labels);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	private static void createAndShowGUI(Locale currentLocale) {
		// Create and set up the window.

		MainApp demo = new MainApp(currentLocale);

		if (frame == null) {
			frame = new JFrame(labels.getString("WINDOW_TITLE"));
		} else {
			frame.getContentPane().removeAll();
			frame.setTitle(labels.getString("WINDOW_TITLE"));
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imgicon = new ImageIcon(logoPath);
		frame.setIconImage(imgicon.getImage());

		frame.add(demo);
		frame.setJMenuBar(demo.createMenuBar());
		frame.applyComponentOrientation(ComponentOrientation.getOrientation(currentLocale));
		// Display the window.

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		showWindow();
	}

	public static void showWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
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

	// Create and set up number formats. These objects also
	// parse numbers input by user.
	private void setUpFormats() {
		numFormat = NumberFormat.getNumberInstance();

	}

	void openFile() throws IOException {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Select an image");
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "png", "gif", "jpeg");
		jfc.addChoosableFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			BufferedImage bi = ImageIO.read(new File(jfc.getSelectedFile().getPath()));
			File outputfile = new File(jfc.getSelectedFile().getName());
			ImageIO.write(bi, "png", outputfile);
			ImagePath = outputfile.getAbsolutePath();
		}

	}

}
