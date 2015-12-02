import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultsArea extends JPanel implements ActionListener {
	
	private JTable table;
    private JScrollPane scrollPane;
    private Main1 main;
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8443783513995441929L;

	public ResultsArea(Main1 main1) {
		main = main1;
		setLayout(new BorderLayout());
		table = new JTable();
    	scrollPane = new JScrollPane(table);
    	add(scrollPane, BorderLayout.CENTER);
	}
	
	public void update() {
		table.setModel(new DefaultTableModel(main.getTableData(), main.getColumnNames()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
