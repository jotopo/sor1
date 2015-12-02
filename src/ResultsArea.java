import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultsArea extends JPanel {
	
	private JTable table;
    private JScrollPane scrollPane;
    private TripleExplorer main;
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8443783513995441929L;

	public ResultsArea(TripleExplorer main1) {
		main = main1;
		setLayout(new BorderLayout());
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable sourceTable = (JTable)e.getSource();
					String columnName = sourceTable.getColumnName(sourceTable.getSelectedColumn());
					String value = sourceTable.getValueAt(sourceTable.getSelectedRow(), sourceTable.getSelectedColumn()).toString();
					System.out.println(columnName);
					System.out.println(value);
					if(columnName.equals("subject")) {
						main.setSearchedSubject(value);
					}
					if(columnName.equals("predicate")) {
						main.setSearchedPredicate(value);
					}
					if(columnName.equals("object")) {
						main.setSearchedObject(value);
					}
					main.update();
				}
			}
		});
    	scrollPane = new JScrollPane(table);
    	add(scrollPane, BorderLayout.CENTER);
	}
	
	public void update() {
		table.setModel(new DefaultTableModel(main.getTableData(), main.getColumnNames()) {
			private static final long serialVersionUID = -4619957349411016142L;
			@Override
		    public boolean isCellEditable(int i, int i1) {
		        return false;
		    }
		});
	}
}
