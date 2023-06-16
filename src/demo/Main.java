package demo;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import api.APIClient;
import api.ProductAPI;
import entities.Product;
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

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableProduct;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldPrice;
	private JTextField textFieldDecription;

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
		scrollPane.setBounds(25, 35, 626, 185);
		contentPane.add(scrollPane);
		
		tableProduct = new JTable();
		tableProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow =tableProduct.getSelectedRow();
				String id=tableProduct.getValueAt(selectedRow, 0).toString();
				ProductAPI productAPI =APIClient.getClient().create(ProductAPI.class);
				productAPI.find(id).enqueue(new Callback<Product>() {
					
					@Override
					public void onResponse(Call<Product> call, Response<Product> response) {
						if(response.isSuccessful()) {
							Product product =response.body();
							textFieldID.setText(product.getId());
							textFieldName.setText(product.getName());
							textFieldPrice.setText(String.valueOf(product.getPrice()));
							textFieldDecription.setText(product.getDecription());
						}
						
					}

					@Override
					public void onFailure(Call<Product> call, Throwable t) {
						JOptionPane.showConfirmDialog(null, t.getMessage());
						
					}

					
				});
			}
		});
		scrollPane.setColumnHeaderView(tableProduct);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 239, 626, 278);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textFieldID = new JTextField();
		textFieldID.setBounds(133, 44, 255, 19);
		panel.add(textFieldID);
		textFieldID.setColumns(10);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(55, 44, 68, 19);
		panel.add(lblID);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(55, 96, 68, 19);
		panel.add(lblName);
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(133, 96, 255, 19);
		panel.add(textFieldName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(55, 147, 68, 19);
		panel.add(lblPrice);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setColumns(10);
		textFieldPrice.setBounds(133, 147, 255, 19);
		panel.add(textFieldPrice);
		
		JLabel lblDecription = new JLabel("Decription");
		lblDecription.setBounds(55, 191, 68, 19);
		panel.add(lblDecription);
		
		textFieldDecription = new JTextField();
		textFieldDecription.setColumns(10);
		textFieldDecription.setBounds(133, 191, 255, 19);
		panel.add(textFieldDecription);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Product product = new Product();
					product.setId(textFieldID.getText());
					product.setName(textFieldName.getText());
					product.setPrice(Double.parseDouble(textFieldPrice.getText()));
					product.setDecription(textFieldDecription.getText());
					
					ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
					productAPI.create(product).enqueue(new Callback<Void>() {
						
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
		btnCreate.setBounds(78, 236, 85, 21);
		panel.add(btnCreate);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Product product = new Product();
				product.setId(textFieldID.getText());
				product.setName(textFieldName.getText());
				product.setPrice(Double.parseDouble(textFieldPrice.getText()));
				product.setDecription(textFieldDecription.getText());
				
				ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
				productAPI.update(product).enqueue(new Callback<Void>() {
					
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
		btnUpdate.setBounds(187, 236, 85, 21);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Confirm","Are you sure", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
					productAPI.delete(textFieldID.getText()).enqueue(new Callback<Void>() {
						
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
		btnDelete.setBounds(292, 236, 85, 21);
		panel.add(btnDelete);
		
	}
	
	
	private void loadData() {
		try {
			ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
			productAPI.findAll().enqueue(new Callback<List<Product>>() {
				
				@Override
				public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
					if(response.isSuccessful()) {
			                // Display the data in the JTable
							DefaultTableModel defaultTableModel = new DefaultTableModel();
							defaultTableModel.addColumn("id");
							defaultTableModel.addColumn("decription");
							defaultTableModel.addColumn("name");
							defaultTableModel.addColumn("price");
							
							for(Product product : response.body()) {
								defaultTableModel.addRow(new Object[] {
										product.getId()
										,product.getDecription()
										,product.getName()
										,product.getPrice()
										
								});
							}
							tableProduct.setModel(defaultTableModel);
			          
					}
					
				}
				
				@Override
				public void onFailure(Call<List<Product>> call, Throwable t) {
					JOptionPane.showConfirmDialog(null, t.getMessage());
					
				}
			});
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
	}
}
