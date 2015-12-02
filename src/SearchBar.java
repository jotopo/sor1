import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchBar extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6087171291311947904L;
	
	private JTextField subjectTextField, predicateTextField, objectTextField;
    private JLabel subjectLabel, predicateLabel, objectLabel;
    private JButton submitSearchButton, clearSearchButton;
    private Main1 main;
    
    public SearchBar(Main1 main1) {
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
		
		submitSearchButton = new JButton("Search");
		submitSearchButton.addActionListener(this);
		add(submitSearchButton);
		
		
		clearSearchButton = new JButton("Clear");
		clearSearchButton.addActionListener(this);
		add(clearSearchButton);
    }
    
	public void update() {
		subjectTextField.setText(main.getSearchedSubject());
		predicateTextField.setText(main.getSearchedPredicate());
		objectTextField.setText(main.getSearchedObject());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitSearchButton) {
			main.setSearchedSubject(subjectTextField.getText());
			main.setSearchedPredicate(predicateTextField.getText());
			main.setSearchedObject(objectTextField.getText());
		}
		else if(e.getSource() == clearSearchButton) {
			
		}
	}
    
}
