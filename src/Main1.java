import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
 
/**
 * Example connection to Fuseki. For this to work, you need to start a local
 * Fuseki server like this: ./fuseki-server --update --mem /ds
 */
public class Main1 extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4946419531503171473L;

    private JPanel mainPanel;    
    private SearchBar searchBar;
    private ResultsArea resultsArea;
    
    private String searchedSubject, searchedPredicate, searchedObject;
    private String query;
    private ResultSet results;
    private Object[] columnNames;
    private Object[][] tableData;
    
    public static void main(String[] args) {
    	new Main1();
    }
    
    public Main1() {
    	searchedSubject = "";
    	searchedPredicate = "";
    	searchedObject = "";
    	updateResults();
    	searchBar = new SearchBar(this);
    	resultsArea = new ResultsArea(this);
    	initGUI();
    	update();
        
    }

	private void initGUI() {
    	setDefaultLookAndFeelDecorated(true);
    	mainPanel = new JPanel(new BorderLayout());
    	mainPanel.add(searchBar, BorderLayout.NORTH);
    	mainPanel.add(resultsArea, BorderLayout.CENTER);
    	add(mainPanel);
    	pack();
    	setVisible(true);
    }
    
    public void update() {
    	updateResults();
    	searchBar.update();
    	resultsArea.update();
    }

	private void updateResults() {
    	updateQuery();
    	System.out.println(query);
    	QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/SOR/query", query);
        results = qe.execSelect();
        columnNames = results.getResultVars().toArray();
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        while (results.hasNext()) {
        	ArrayList<Object> arrayRow = new ArrayList<Object>();
        	QuerySolution row = results.next();
        	for (Object columnName : columnNames) {
        		arrayRow.add(row.get(columnName.toString()));
        	}
        	data.add(arrayRow.toArray());
        }
        
        Object[][] finalArray = new Object[data.size()][];
        int i = 0;
        for(Object[] singleData : data) {
        	finalArray[i] = singleData;
        	i++;
        }
        tableData = finalArray;
        qe.close(); 
    }
    
    private void updateQuery() {
    	//Default query to start.
    	if (query == null) {
    		query = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";
    		return;
    	}
    	String subjectToUse = "?subject";
    	String predicateToUse = "?predicate";
    	String objectToUse = "?object";
    	
    	if (!searchedSubject.equals("")) {
    		subjectToUse = '<' + searchedSubject + '>';
    	}
    	if (!searchedPredicate.equals("")) {
    		predicateToUse = '<' + searchedPredicate + '>';
    	}
    	if (!searchedObject.equals("")) {
    		objectToUse = '"' + searchedObject + '"';
    	}
    
		query = "SELECT ?subject ?predicate ?object WHERE {" + subjectToUse + " " + predicateToUse + " " + objectToUse + "}";
    }
    
    public String getSearchedSubject() {
		return searchedSubject;
	}

	public void setSearchedSubject(String searchedSubject) {
		this.searchedSubject = searchedSubject;
	}

	public String getSearchedPredicate() {
		return searchedPredicate;
	}

	public void setSearchedPredicate(String searchedPredicate) {
		this.searchedPredicate = searchedPredicate;
	}

	public String getSearchedObject() {
		return searchedObject;
	}

	public void setSearchedObject(String searchedObject) {
		this.searchedObject = searchedObject;
	}
    
    public Object[][] getTableData() {
		return tableData;
	}

	public void setTableData(Object[][] tableData) {
		this.tableData = tableData;
	}

	public Object[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Object[] columnNames) {
		this.columnNames = columnNames;
	}
}