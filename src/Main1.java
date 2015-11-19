import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

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
    /** A template for creating a nice SPARUL query */
    private static final String UPDATE_TEMPLATE = 
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
            + "INSERT DATA"
            + "{ <http://example/%s>    dc:title    \"A new book\" ;"
            + "                         dc:creator  \"A.N.Other\" ." + "}   ";
 
    private JTable table;
    private JPanel panel;
    private ResultSet results;
    private Object[] columnNames;
    private Object[][] tableData;
    
    public static void main(String[] args) {
    	new Main1();
    }
    
    public Main1() {
    	retrieveResults();
    	
    	initGUI();
        //Query the collection, dump output
        
    }
    
    private void initGUI() {
    	setDefaultLookAndFeelDecorated(true);
    	panel = new JPanel(new BorderLayout());
    	createTable();
    	
    	add(panel);
    	pack();
    	setVisible(true);
    }
    
    private void createTable() {
    	table = new JTable(tableData, columnNames);
    	panel.add(table);
    }
 
    private void retrieveResults() {
    	QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/SOR0.1/query", "SELECT * WHERE {?x ?r ?y}");
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
}