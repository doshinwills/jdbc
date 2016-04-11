package com.doshin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.doshin.utils.JDBCTutorialUtilities;

public class C_ResultSetCoffeeInsert {
	
	static Connection conn = null;
	static String dbName = null;

	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:postgresql://localhost/jdbccon?user=postgres&password=postgres";
		conn = DriverManager.getConnection(url);
		dbName = "public";
		
		insertRow("Amaretto", 49, 9.99f, 0, 0);
		

	}

	public static void insertRow(String coffeeName, int supplierID, float price, int sales, int total) throws SQLException {

		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet uprs = stmt.executeQuery("SELECT * FROM " + dbName + ".COFFEES");

			uprs.moveToInsertRow();
			uprs.updateString("COF_NAME", coffeeName);
			uprs.updateInt("SUP_ID", supplierID);
			uprs.updateFloat("PRICE", price);
			uprs.updateInt("SALES", sales);
			uprs.updateInt("TOTAL", total);

			uprs.insertRow();
			uprs.beforeFirst();
		} catch (SQLException e) {
			JDBCTutorialUtilities.printSQLException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

}
