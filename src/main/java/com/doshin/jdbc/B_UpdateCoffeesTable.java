package com.doshin.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.doshin.utils.JDBCTutorialUtilities;

public class B_UpdateCoffeesTable {
	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:postgresql://localhost/jdbccon?user=postgres&password=postgres";
		Connection conn = DriverManager.getConnection(url);
		String dbName = "public";
		
		cursorHoldabilitySupport(conn);

		A_ViewCoffeesTable.viewTable(conn, dbName);
		modifyPrices(conn, dbName, 0.5f);
		A_ViewCoffeesTable.viewTable(conn, dbName);

		
	}
	/**
	 * 
     *	HOLD_CURSORS_OVER_COMMIT: ResultSet cursors are not closed; they are holdable: they are held open when the method commit is called. 
     *		Holdable cursors might be ideal if your application uses mostly read-only ResultSet objects.
     *	CLOSE_CURSORS_AT_COMMIT: ResultSet objects (cursors) are closed when the commit method is called. 
     *		Closing cursors when this method is called can result in better performance for some applications.
	 *
	 * @param conn
	 * @throws SQLException
	 */
	public static void cursorHoldabilitySupport(Connection conn)
		    throws SQLException {

		    DatabaseMetaData dbMetaData = conn.getMetaData();
		    System.out.println("ResultSet.HOLD_CURSORS_OVER_COMMIT = " +
		        ResultSet.HOLD_CURSORS_OVER_COMMIT);

		    System.out.println("ResultSet.CLOSE_CURSORS_AT_COMMIT = " +
		        ResultSet.CLOSE_CURSORS_AT_COMMIT);

		    System.out.println("Default cursor holdability: " +
		        dbMetaData.getResultSetHoldability());

		    System.out.println("Supports HOLD_CURSORS_OVER_COMMIT? " +
		        dbMetaData.supportsResultSetHoldability(
		            ResultSet.HOLD_CURSORS_OVER_COMMIT));

		    System.out.println("Supports CLOSE_CURSORS_AT_COMMIT? " +
		        dbMetaData.supportsResultSetHoldability(
		            ResultSet.CLOSE_CURSORS_AT_COMMIT));
		}
    
	
	/**
	 * ResultSet Types
	 * -----------------
	 * TYPE_FORWARD_ONLY: The result set cannot be scrolled; its cursor moves forward only, from before the first row to after the last row. 
	 * 	The rows contained in the result set depend on how the underlying database generates the results. 
	 * 	That is, it contains the rows that satisfy the query at either the time the query is executed or as the rows are retrieved.
	 * TYPE_SCROLL_INSENSITIVE: The result can be scrolled; its cursor can move both forward and backward relative to the current position, 
	 * 	and it can move to an absolute position. The result set is insensitive to changes made to the underlying data source while it is open. 
	 * 	It contains the rows that satisfy the query at either the time the query is executed or as the rows are retrieved.
	 * TYPE_SCROLL_SENSITIVE: The result can be scrolled; its cursor can move both forward and backward relative to the current position, 
	 * 	and it can move to an absolute position. The result set reflects changes made to the underlying data source while the result set remains open.
	 * 
	 * ResultSet Concurrency
	 * ----------------------
	 * CONCUR_READ_ONLY: The ResultSet object cannot be updated using the ResultSet interface.
	 * CONCUR_UPDATABLE: The ResultSet object can be updated using the ResultSet interface.
	 *
	 */
	public static void modifyPrices(Connection con, String dbName, float percentage) throws SQLException {

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet uprs = stmt.executeQuery("SELECT * FROM " + dbName + ".COFFEES");

			while (uprs.next()) {
				float f = uprs.getFloat("PRICE");
				uprs.updateFloat("PRICE", f * percentage);
				uprs.updateRow();
			}

		} catch (SQLException e) {
			JDBCTutorialUtilities.printSQLException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

}
