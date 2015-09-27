package com.portal.opghrvatska.DB;

import java.io.Serializable;
import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

public class DBHelper implements Serializable{
	private static JDBCConnectionPool connectionPool = null;
	
	public DBHelper() {
		initConnectionPool();
	}
	
	public void initConnectionPool() {
	
		try {
			connectionPool = new SimpleJDBCConnectionPool(
					"com.mysql.jdbc.Driver",
			        //"jdbc:mysql://localhost:3306/opghrvatskadb?characterEncoding=utf8", "root", "",2,5);
	        		"jdbc:mysql://localhost:3306/opghrvatskadb?characterEncoding=utf8", "root", "fMkzsinJ4g",2,5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JDBCConnectionPool getConnectionPool(){
        return connectionPool;
    }
}
