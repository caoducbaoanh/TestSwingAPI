package demo;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import api.APIClient;
import api.UserAPI;
import entities.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String username="root";
    private static final String password="root";
    private static final String dataConn="jdbc:mysql://localhost:3306/mid_term";
	
	private JPanel contentPane;
	private JTable tableUser;
	//private JComboBox comboBoxTypeOfHouse;
	//private JDateChooser dateChooser;
	private JTextField textFieldIdentityID;
	private JTextField textFieldFee;
	private JTextField textFieldID;
	private JTextField textFieldAmountToPay;
	
	Connection sqlConn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
   
    
    int q,i,id,deteleItem;
    private JTextField textFieldCitizenName;
    private JTextField textFieldPhoneNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 688, 632);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 65, 626, 155);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 230, 626, 329);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JDateChooser dateChooser = new JDateChooser();
		Calendar calendar = Calendar.getInstance();
        calendar.set(2023,Calendar.JUNE,30); // Set the desired default date
        Date defaultDate = calendar.getTime();
        dateChooser.setDate(defaultDate);
        
    	dateChooser.setDateFormatString("yyyy-MM-dd");
    	dateChooser.setBounds(133,102,255,19);
    	dateChooser.getCalendarButton();
    	
    	panel.add(dateChooser);
    	panel.setVisible(true);
		
		
		JComboBox comboBoxTypeOfHouse = new JComboBox();
		comboBoxTypeOfHouse.setModel(new DefaultComboBoxModel(new String[] {"Level 1", "Level 2", "Level 3", "Level 4"}));
		comboBoxTypeOfHouse.setBounds(133, 135, 255, 21);
		panel.add(comboBoxTypeOfHouse);
    	
		
		loadData();
		
		
		tableUser = new JTable();
		tableUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow =tableUser.getSelectedRow();
				String id=tableUser.getValueAt(selectedRow, 0).toString();
				UserAPI userAPI =APIClient.getClient().create(UserAPI.class);
				userAPI.find(id).enqueue(new Callback<User>() {
					@Override
					public void onResponse(Call<User> call, Response<User> response) {
						if(response.isSuccessful()) {
							User user =response.body();
							textFieldID.setText(user.getId());
							textFieldIdentityID.setText(user.getIdentityID());
							/*String dateString= user.getDateOfPayment().toString();
							
							SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

					        // Create a SimpleDateFormat instance for desired output format
					        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
					        
					        
					        try {
					            // Parse the date string into a Date object
					            Date date = inputFormat.parse(dateString);

					            // Format the Date object into the desired output format
					            String formattedDate = outputFormat.format(date);

					            // Print the formatted date
					            dateChooser.setDateFormatString(formattedDate);
					        } catch (ParseException e) {
					            e.printStackTrace();
					        }
							*/
							dateChooser.setDateFormatString(user.getDateOfPayment());
							comboBoxTypeOfHouse.setSelectedItem(user.getTypeOfHouse());
							textFieldFee.setText(String.valueOf(user.getFee()));
							textFieldAmountToPay.setText(user.getAmountToPay());
						}
						
					}

					@Override
					public void onFailure(Call<User> call, Throwable t) {
						JOptionPane.showConfirmDialog(null, t.getMessage());
						
					}

					
				});
			}
		});
		scrollPane.setColumnHeaderView(tableUser);
		
		
		
		JLabel lblDateOfPayment = new JLabel("Date Of Payment");
		lblDateOfPayment.setBounds(55, 102, 68, 19);
		panel.add(lblDateOfPayment);
		
		
    	
    	
		
		JLabel lblIdentityID = new JLabel("Identity ID");
		lblIdentityID.setBounds(55, 44, 68, 19);
		panel.add(lblIdentityID);
		
		textFieldIdentityID = new JTextField();
		textFieldIdentityID.setColumns(10);
		textFieldIdentityID.setBounds(133, 44, 255, 19);
		panel.add(textFieldIdentityID);
		
		textFieldFee = new JTextField();
		textFieldFee.setColumns(10);
		textFieldFee.setBounds(133, 199, 255, 19);
		panel.add(textFieldFee);
		
		JLabel lblFee = new JLabel("Fee");
		lblFee.setBounds(55, 199, 68, 19);
		panel.add(lblFee);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(133, 15, 255, 19);
		panel.add(textFieldID);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(55, 15, 68, 19);
		panel.add(lblID);
		
		textFieldAmountToPay = new JTextField();
		textFieldAmountToPay.setColumns(10);
		textFieldAmountToPay.setBounds(133, 228, 255, 19);
		panel.add(textFieldAmountToPay);
		
		JLabel lblAmountToPay = new JLabel("Amount To Pay");
		lblAmountToPay.setBounds(55, 228, 68, 19);
		panel.add(lblAmountToPay);
		
		JLabel lblTypeOfHouse = new JLabel("Type Of House");
		lblTypeOfHouse.setBounds(55, 135, 68, 19);
		panel.add(lblTypeOfHouse);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(166, 280, 85, 21);
		panel.add(btnUpdate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame; 
        		frame = new JFrame("Exit");
        		if(JOptionPane.showConfirmDialog(frame, "Do you want to exit ?","MYSQL Connector", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
        			System.exit(0);
        		}
			}
		});
		btnCancel.setBounds(278, 280, 85, 21);
		panel.add(btnCancel);
		
		JLabel lblCitizenName = new JLabel("Citizen Name");
		lblCitizenName.setBounds(55, 73, 68, 19);
		panel.add(lblCitizenName);
		
		textFieldCitizenName = new JTextField();
		textFieldCitizenName.setColumns(10);
		textFieldCitizenName.setBounds(133, 73, 255, 19);
		panel.add(textFieldCitizenName);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(55, 167, 68, 19);
		panel.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setColumns(10);
		textFieldPhoneNumber.setBounds(133, 167, 255, 19);
		panel.add(textFieldPhoneNumber);
		
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				User user = new User();
				user.setId(textFieldID.getText());
				user.setAmountToPay(textFieldAmountToPay.getText());
				user.setTypeOfHouse(comboBoxTypeOfHouse.getSelectedItem());
				user.setFee(Double.parseDouble(textFieldFee.getText()));
				user.setIdentityID(textFieldIdentityID.getText());
				user.setDateOfPayment(dateChooser.getDateFormatString());
				
				
				UserAPI userAPI = APIClient.getClient().create(UserAPI.class);
				userAPI.update(user).enqueue(new Callback<Void>() {
					
					@Override
					public void onResponse(Call<Void> call, Response<Void> response) {
						if (response.isSuccessful()) {
							loadData();
						}
						
					}

					@Override
					public void onFailure(Call<Void> call, Throwable t) {
						JOptionPane.showMessageDialog(null, t.getMessage());
						
					}

					
				});
				
			}
		});
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(108, 23, 85, 21);
		contentPane.add(btnCreate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(322, 23, 85, 21);
		contentPane.add(btnDelete);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String var=	JOptionPane.showInputDialog("Enter person name to search for: ");
	    		if(var==null) {
	    			JOptionPane.showMessageDialog(null, "Please input the person name");
	    		}
	    		else {
	    			DefaultTableModel defaultTableModel=(DefaultTableModel)tableUser.getModel();
	        		TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(defaultTableModel);
	        		tableUser.setRowSorter(obj);
	        		obj.setRowFilter(RowFilter.regexFilter(var.toString()));
	        		}
			}
		});
		btnSearch.setBounds(219, 23, 85, 21);
		contentPane.add(btnSearch);
		
		JToggleButton tglbtnSort = new JToggleButton("Sort");
		tglbtnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnSort.isSelected()) {
    	            // Toggle button is selected
    				 try {
     					Class.forName("com.mysql.cj.jdbc.Driver");
     					sqlConn=DriverManager.getConnection(dataConn,username,password);
     					pst=sqlConn.prepareStatement("select * from thue ORDER BY citizen_name ASC");
     					
     					rs=pst.executeQuery();
     					java.sql.ResultSetMetaData stData=rs.getMetaData();
     					
     					q=stData.getColumnCount();
     					DefaultTableModel RecordTable=(DefaultTableModel)tableUser.getModel();
     					RecordTable.setRowCount(0);
     					
     					
     					while(rs.next()) {
     						Vector ColumnData=new Vector();
     						for (int i = 1; i < q; i++) {
     							ColumnData.add(rs.getString("id"));
     							ColumnData.add(rs.getString("identityid"));	
     							ColumnData.add(rs.getString("citizen_name"));
     							ColumnData.add(rs.getString("date_of_payment"));
     							ColumnData.add(rs.getString("amount_to_pay"));
     							ColumnData.add(rs.getString("fee"));
     							ColumnData.add(rs.getString("phone_number"));
     							ColumnData.add(rs.getString("type_of_house"));
     						}
     						RecordTable.addRow(ColumnData);
     					}
     					
     				} catch (Exception ex) {
     					JOptionPane.showMessageDialog(null, ex);
     				}
    	        } else {
    	            // Toggle button is deselected
    	        	 try {
     					Class.forName("com.mysql.cj.jdbc.Driver");
     					sqlConn=DriverManager.getConnection(dataConn,username,password);
     					pst=sqlConn.prepareStatement("select * from thue ORDER BY citizen_name DESC");
     					
     					rs=pst.executeQuery();
     					java.sql.ResultSetMetaData stData=rs.getMetaData();
     					
     					q=stData.getColumnCount();
     					DefaultTableModel RecordTable=(DefaultTableModel)tableUser.getModel();
     					RecordTable.setRowCount(0);
     					
     					
     					while(rs.next()) {
     						Vector ColumnData=new Vector();
     						for (int i = 1; i < q; i++) {
     							ColumnData.add(rs.getString("id"));
     							ColumnData.add(rs.getString("identityid"));	
     							ColumnData.add(rs.getString("citizen_name"));
     							ColumnData.add(rs.getString("date_of_payment"));
     							ColumnData.add(rs.getString("amount_to_pay"));
     							ColumnData.add(rs.getString("fee"));
     							ColumnData.add(rs.getString("phone_number"));
     							ColumnData.add(rs.getString("type_of_house"));
     						}
     						RecordTable.addRow(ColumnData);
     					}
     					
     				} catch (Exception ex) {
     					JOptionPane.showMessageDialog(null, ex);
     				}
    	        }
			}
		});
		tglbtnSort.setBounds(430, 23, 85, 21);
		contentPane.add(tglbtnSort);
		
		
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Confirm","Are you sure", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					UserAPI userAPI = APIClient.getClient().create(UserAPI.class);
					userAPI.delete(textFieldID.getText()).enqueue(new Callback<Void>() {
						
						@Override
						public void onResponse(Call<Void> call, Response<Void> response) {
							if(response.isSuccessful()) {
								loadData();
							}
							
						}
						
						@Override
						public void onFailure(Call<Void> call, Throwable t) {
							JOptionPane.showMessageDialog(null, t.getMessage());
							
						}

						
					});
				}
			}
		});
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					User user = new User();
					user.setId(textFieldID.getText());
					user.setCitizenName(textFieldCitizenName.getText());
					user.setAmountToPay(textFieldAmountToPay.getText());
					user.setTypeOfHouse(comboBoxTypeOfHouse.getSelectedItem());
					user.setFee(Double.parseDouble(textFieldFee.getText()));
					user.setIdentityID(textFieldIdentityID.getText());
					user.setDateOfPayment(dateChooser.getDateFormatString());
					user.setPhoneNumber(textFieldPhoneNumber.getText());
					
					
					UserAPI userAPI = APIClient.getClient().create(UserAPI.class);
					userAPI.create(user).enqueue(new Callback<Void>() {
						
						@Override
						public void onResponse(Call<Void> call, Response<Void> response) {
							if(response.isSuccessful()) {
								loadData();
							}
							
						}

						@Override
						public void onFailure(Call<Void> call, Throwable t) {
							JOptionPane.showMessageDialog(null, t.getMessage());
							
						}

						
						
					});
					
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				
				
				
			}
		});
		
	}
	
	
	private void loadData() {
		try {
			UserAPI userAPI = APIClient.getClient().create(UserAPI.class);
			userAPI.findAll().enqueue(new Callback<List<User>>() {
				
				@Override
				public void onResponse(Call<List<User>> call, Response<List<User>> response) {
					if(response.isSuccessful()) {
			                // Display the data in the JTable
							DefaultTableModel defaultTableModel = new DefaultTableModel();
							defaultTableModel.addColumn("id");
							defaultTableModel.addColumn("identityID");
							defaultTableModel.addColumn("citizenName");
							defaultTableModel.addColumn("dateOfPayment");
							defaultTableModel.addColumn("amountToPay");
							
							
							defaultTableModel.addColumn("fee");
							
							
							defaultTableModel.addColumn("phoneNumber");
							defaultTableModel.addColumn("typeOfHouse");
							
						

							
							for(User user : response.body()) {
								
								defaultTableModel.addRow(new Object[] {
										user.getId()
										,user.getIdentityID()
										,user.getCitizenName()
										,user.getDateOfPayment()
										,user.getAmountToPay()
										,user.getFee()
										
										,user.getPhoneNumber()
										,user.getTypeOfHouse()
										
								});
							}
							tableUser.setModel(defaultTableModel);
			          
					}
					
				}
				
				@Override
				public void onFailure(Call<List<User>> call, Throwable t) {
					JOptionPane.showConfirmDialog(null, t.getMessage());
					
				}
			});
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
	}
}
