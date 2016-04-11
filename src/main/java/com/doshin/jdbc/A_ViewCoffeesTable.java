package com.doshin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.doshin.utils.JDBCTutorialUtilities;

/**
 * Hello world!
 *
 */
public class A_ViewCoffeesTable {
	public static void main(String[] args) throws SQLException {

		String url = "jdbc:postgresql://localhost/jdbccon?user=postgres&password=postgres";
		Connection conn = DriverManager.getConnection(url);
		String dbName = "public";

		viewTable(conn, dbName);
	}



	public static void viewTable(Connection con, String dbName) throws SQLException {

		Statement stmt = null;
		String query = "select COF_NAME, SUP_ID, PRICE, " + "SALES, TOTAL " + "from " + dbName + ".COFFEES";
		System.out.println(query);
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String coffeeName = rs.getString("COF_NAME");
				int supplierID = rs.getInt("SUP_ID");
				float price = rs.getFloat("PRICE");
				int sales = rs.getInt("SALES");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + "\t" + supplierID + "\t" + price + "\t" + sales + "\t" + total);
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
