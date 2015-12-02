import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;

public class InsertBar extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6087171291311947904L;
	
	private JTextField subjectTextField, predicateTextField, objectTextField;
    private JLabel subjectLabel, predicateLabel, objectLabel;
    private JButton submitInsertButton, clearInsertButton;
    private TripleExplorer main;
    
    public InsertBar(TripleExplorer main1) {
    	main = main1;
    	subjectLabel = new JLabel("Subject:");
		subjectTextField = new JTextField(20);
		add(subjectLabel);
		add(subjectTextField);
		
		predicateLabel = new JLabel("Predicate:");
		predicateTextField = new JTextField(20);
		add(predicateLabel);
		add(predicateTextField);
		
		objectLabel = new JLabel("Object:");
		objectTextField = new JTextField(20);
		add(objectLabel);
		add(objectTextField);
		
		submitInsertButton = new JButton("Insert");
		submitInsertButton.addActionListener(this);
		add(submitInsertButton);
		
		clearInsertButton = new JButton("Clear");
		clearInsertButton.addActionListener(this);
		add(clearInsertButton);
    }
    
	public void update() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitInsertButton) {
			if (subjectTextField.getText() != "" && predicateTextField.getText() != "" && objectTextField.getText() != "") {
				String query = "INSERT DATA" +
					"{ <" + subjectTextField.getText() + "> <" + predicateTextField.getText() + "> '" + objectTextField.getText() + "' }";
				System.out.println(query);
				String id = UUID.randomUUID().toString();
				UpdateProcessor upp = UpdateExecutionFactory.createRemote(
		                UpdateFactory.create(String.format(query, id)), 
		                "http://localhost:3030/SOR/update");
		        upp.execute();
				main.update();
			}
			
		}
		else if(e.getSource() == clearInsertButton) {
			clear();
		}
	}
    
	private void clear() {
		subjectTextField.setText("");
		predicateTextField.setText("");
		objectTextField.setText("");
	}
}
