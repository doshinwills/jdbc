package com.doshin.jdbc;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.doshin.utils.JDBCTutorialUtilities;

public class D_BatchAddCoffee {
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:postgresql://localhost/jdbccon?user=postgres&password=postgres";
		Connection conn = DriverManager.getConnection(url);
		String dbName = "public";
		
		
		batchUpdate(conn);
	}

	public static void batchUpdate(Connection con) throws SQLException {

		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			stmt.addBatch("INSERT INTO COFFEES " + "VALUES('Amaretto', 49, 9.99, 0, 0)");

			stmt.addBatch("INSERT INTO COFFEES " + "VALUES('Hazelnut', 49, 9.99, 0, 0)");

			stmt.addBatch("INSERT INTO COFFEES " + "VALUES('Amaretto_decaf', 49, " + "10.99, 0, 0)");

			stmt.addBatch("INSERT INTO COFFEES " + "VALUES('Hazelnut_decaf', 49, " + "10.99, 0, 0)");

			int[] updateCounts = stmt.executeBatch();
			con.commit();
			
			for(int output : updateCounts) {
				System.out.println(output);
			}

		} catch (BatchUpdateException b) {
			JDBCTutorialUtilities.printBatchUpdateException(b);
		} catch (SQLException ex) {
			JDBCTutorialUtilities.printSQLException(ex);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			con.setAutoCommit(true);
		}
	}
}
