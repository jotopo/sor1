import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
 
/**
 * Exploring the possibilities of Triplestores with Apache Jena
 * 
 */
public class TripleExplorer extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4946419531503171473L;

    private JPanel mainPanel;    
    private SearchBar searchBar;
    private ResultsArea resultsArea;
    private InsertBar insertBar;
    
    private String searchedSubject, searchedPredicate, searchedObject;
    private String query;
    private ResultSet results;
    private Object[] columnNames;
    private Object[][] tableData;
    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
    	new TripleExplorer();
    }
    /**
     * initialising the TripleExplorer
     * also initialising the other classes
     */
    public TripleExplorer() {
    	searchedSubject = "";
    	searchedPredicate = "";
    	searchedObject = "";
    	updateResults();
    	searchBar = new SearchBar(this);
    	resultsArea = new ResultsArea(this);
    	insertBar = new InsertBar(this);
    	initGUI();
    	update();
        
    }
    /**
     * initialising the GUI for the tripleExplorer
     */
	private void initGUI() {
		setTitle("JENAAAAAAA");
    	setDefaultLookAndFeelDecorated(true);
    	mainPanel = new JPanel(new BorderLayout());
    	mainPanel.add(searchBar, BorderLayout.NORTH);
    	mainPanel.add(resultsArea, BorderLayout.CENTER);
    	mainPanel.add(insertBar, BorderLayout.SOUTH);
    	add(mainPanel);
    	pack();
    	setVisible(true);
    }
    /**
     * updating the GUI when the user searches for something.
     */
    public void update() {
    	updateResults();
    	searchBar.update();
    	resultsArea.update();
    	insertBar.update();
    }
    /**
     * updating the results
     * putting the results in tables so it's easier to read.
     * 
     */
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
    /**
     * the query to be used to update the data shown in the table.
     * normal query SELECt * WHERE {?subject ?predicate ?object};
     * query will be updated when you click on a result in the table or enter something in the search boxes.
     * subjectToUse		=	will be updated when user clicks a result in the first column.
     * predicateToUse	=	will be updated when user clicks a result in the second column.
     * ubjectToUse		=	will be updated when user clicks a result in the third column.
     */
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
    /**
     * 
     * @return
     */
    public String getSearchedSubject() {
		return searchedSubject;
	}
    /**
     * 
     * @param searchedSubject
     */
	public void setSearchedSubject(String searchedSubject) {
		this.searchedSubject = searchedSubject;
	}
	/**
	 * 
	 * @return
	 */
	public String getSearchedPredicate() {
		return searchedPredicate;
	}
	/**
	 * 
	 * @param searchedPredicate
	 */
	public void setSearchedPredicate(String searchedPredicate) {
		this.searchedPredicate = searchedPredicate;
	}
	/**
	 * 
	 * @return
	 */
	public String getSearchedObject() {
		return searchedObject;
	}
	/**
	 * 
	 * @param searchedObject
	 */
	public void setSearchedObject(String searchedObject) {
		this.searchedObject = searchedObject;
	}
    /**
     * 
     * @return
     */
    public Object[][] getTableData() {
		return tableData;
	}
    /**
     * 
     * @param tableData
     */
	public void setTableData(Object[][] tableData) {
		this.tableData = tableData;
	}
	/**
	 * 
	 * @return
	 */
	public Object[] getColumnNames() {
		return columnNames;
	}
	/**	
	 * 
	 * @param columnNames
	 */
	public void setColumnNames(Object[] columnNames) {
		this.columnNames = columnNames;
	}
}