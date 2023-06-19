package demo;

import java.awt.EventQueue;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JToggleButton;
import javax.swing.RowFilter;

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
	private JTextField textFieldDate;
	private JTextField textFieldIdentityID;
	private JTextField textFieldPhoneNumber;
	private JTextField textFieldFee;
	private JTextField textFieldPassword;
	private JTextField textFieldID;
	private JTextField textFieldAmountToPay;
	private JTextField textFieldCitizenName;
	private JTextField textFieldTypeOfHouse;
	
	Connection sqlConn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    int q,i,id,deteleItem;

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
		loadData();
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 65, 626, 155);
		contentPane.add(scrollPane);
		
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
							textFieldAmountToPay.setText(user.getAmountToPay());
							textFieldCitizenName.setText(user.getCitizenName());
							textFieldDate.setText(user.getDateOfPayment());
							textFieldFee.setText(String.valueOf(user.getFee()));
							textFieldIdentityID.setText(user.getIdentityID());
							textFieldPassword.setText(user.getPassword());
							textFieldPhoneNumber.setText(user.getPhoneNumber());
							textFieldTypeOfHouse.setText(user.getTypeOfHouse());
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
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 230, 626, 329);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel("Date Of Payment");
		lblName.setBounds(55, 96, 68, 19);
		panel.add(lblName);
		
		textFieldDate = new JTextField();
		textFieldDate.setColumns(10);
		textFieldDate.setBounds(133, 96, 255, 19);
		panel.add(textFieldDate);
		
		JLabel lblPrice = new JLabel("Identity ID");
		lblPrice.setBounds(55, 147, 68, 19);
		panel.add(lblPrice);
		
		textFieldIdentityID = new JTextField();
		textFieldIdentityID.setColumns(10);
		textFieldIdentityID.setBounds(133, 147, 255, 19);
		panel.add(textFieldIdentityID);
		
		JLabel lblDecription = new JLabel("Phone Number");
		lblDecription.setBounds(55, 199, 68, 19);
		panel.add(lblDecription);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setColumns(10);
		textFieldPhoneNumber.setBounds(133, 199, 255, 19);
		panel.add(textFieldPhoneNumber);
		
		textFieldFee = new JTextField();
		textFieldFee.setColumns(10);
		textFieldFee.setBounds(133, 118, 255, 19);
		panel.add(textFieldFee);
		
		JLabel lblFee = new JLabel("Fee");
		lblFee.setBounds(55, 118, 68, 19);
		panel.add(lblFee);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(133, 176, 255, 19);
		panel.add(textFieldPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(55, 176, 68, 19);
		panel.add(lblPassword);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(133, 15, 255, 19);
		panel.add(textFieldID);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(55, 15, 68, 19);
		panel.add(lblID);
		
		textFieldAmountToPay = new JTextField();
		textFieldAmountToPay.setColumns(10);
		textFieldAmountToPay.setBounds(133, 38, 255, 19);
		panel.add(textFieldAmountToPay);
		
		JLabel lblAmoutToPay = new JLabel("Amout To Pay");
		lblAmoutToPay.setBounds(55, 38, 68, 19);
		panel.add(lblAmoutToPay);
		
		textFieldCitizenName = new JTextField();
		textFieldCitizenName.setColumns(10);
		textFieldCitizenName.setBounds(133, 67, 255, 19);
		panel.add(textFieldCitizenName);
		
		JLabel lblCitizenName = new JLabel("Citizen Name");
		lblCitizenName.setBounds(55, 67, 68, 19);
		panel.add(lblCitizenName);
		
		textFieldTypeOfHouse = new JTextField();
		textFieldTypeOfHouse.setColumns(10);
		textFieldTypeOfHouse.setBounds(133, 228, 255, 19);
		panel.add(textFieldTypeOfHouse);
		
		JLabel lblTypeOfHouse = new JLabel("Type Of House");
		lblTypeOfHouse.setBounds(55, 228, 68, 19);
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
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				User user = new User();
				user.setId(textFieldID.getText());
				user.setAmountToPay(textFieldAmountToPay.getText());
				user.setCitizenName(textFieldCitizenName.getText());
				user.setDateOfPayment(textFieldDate.getText());
				user.setFee(Double.parseDouble(textFieldFee.getText()));
				user.setIdentityID(textFieldIdentityID.getText());
				user.setPassword(textFieldPassword.getText());
				user.setPhoneNumber(textFieldPhoneNumber.getText());
				user.setTypeOfHouse(textFieldTypeOfHouse.getText());
				
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
     							ColumnData.add(rs.getString("amount_to_pay"));	
     							ColumnData.add(rs.getString("citizen_name"));
     							ColumnData.add(rs.getString("date_of_payment"));
     							ColumnData.add(rs.getString("fee"));
     							ColumnData.add(rs.getString("identityid"));
     							ColumnData.add(rs.getString("password"));
     							ColumnData.add(rs.getString("phone_number"));
     							ColumnData.add(rs.getString("type_of_house"));
     						}
     						RecordTable.addRow(ColumnData);
     					}
     					
     				} catch (Exception ex) {
     					// TODO: handle exception
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
     							ColumnData.add(rs.getString("amount_to_pay"));	
     							ColumnData.add(rs.getString("citizen_name"));
     							ColumnData.add(rs.getString("date_of_payment"));
     							ColumnData.add(rs.getString("fee"));
     							ColumnData.add(rs.getString("identityid"));
     							ColumnData.add(rs.getString("password"));
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
					user.setAmountToPay(textFieldAmountToPay.getText());
					user.setCitizenName(textFieldCitizenName.getText());
					user.setDateOfPayment(textFieldDate.getText());
					user.setFee(Double.parseDouble(textFieldFee.getText()));
					user.setIdentityID(textFieldIdentityID.getText());
					user.setPassword(textFieldPassword.getText());
					user.setPhoneNumber(textFieldPhoneNumber.getText());
					user.setTypeOfHouse(textFieldTypeOfHouse.getText());
					
					
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
							defaultTableModel.addColumn("amountToPay");
							defaultTableModel.addColumn("citizenName");
							defaultTableModel.addColumn("dateOfPayment");
							defaultTableModel.addColumn("fee");
							defaultTableModel.addColumn("identityID");
							defaultTableModel.addColumn("password");
							defaultTableModel.addColumn("phoneNumber");
							defaultTableModel.addColumn("typeOfHouse");
							
							for(User user : response.body()) {
								defaultTableModel.addRow(new Object[] {
										user.getId()
										,user.getAmountToPay()
										,user.getCitizenName()
										,user.getDateOfPayment()
										,user.getFee()
										,user.getIdentityID()
										,user.getPassword()
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
