package com.portal.opghrvatska.DB;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.portal.opghrvatska.data.OPG;
import com.portal.opghrvatska.data.User;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DBopg {
	private JDBCConnectionPool connectionPool;
	SQLContainer opgSqlContainer;
	
	public DBopg() throws SQLException{
		connectionPool = DBHelper.getConnectionPool();
		TableQuery opgQuery = new TableQuery("opg",connectionPool);
		opgSqlContainer = new SQLContainer(opgQuery);
	}
	
	public void insertOPG(OPG opg){
        Object itemId = opgSqlContainer.addItem();
        Item opgItem = opgSqlContainer.getItem(itemId);
        opgItem.getItemProperty("name").setValue(opg.getName());
        opgItem.getItemProperty("owner").setValue(opg.getOwner());
        opgItem.getItemProperty("addres").setValue(opg.getAddres());
        opgItem.getItemProperty("city").setValue(opg.getCity().replaceAll("\\s+$", ""));
        opgItem.getItemProperty("OIB").setValue(opg.getOib());
        opgItem.getItemProperty("telephone").setValue(opg.getTelephone());
        opgItem.getItemProperty("email").setValue(opg.getEmail().replaceAll("\\s+$", ""));
        opgItem.getItemProperty("description").setValue(opg.getDescription());
        try {
        	opgSqlContainer.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public SQLContainer getAllOPG(){
		SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT opg.idopg,opg.name,opg.addres,opg.city,opg.OIB,opg.telephone,opg.email,opg.description,"
					+ "users.first_name,users.last_name FROM opg JOIN users ON opg.owner = users.idusers", connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
	}
	
	public SQLContainer getMyOPG(User user){
		SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT opg.idopg,opg.name,opg.addres,opg.city,opg.OIB,opg.telephone,opg.email,opg.description "
					+ "FROM opg WHERE opg.owner = "+user.getId(), connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
	}
	
	public void updateOPG(OPG opg){        
        Connection connection = null;
		try {
			connection = (Connection)	connectionPool.reserveConnection();
			connection.setAutoCommit(false);
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("UPDATE opg SET name = ?, addres = ?, city = ?, OIB = ?, telephone = ?, email = ?, description = ? "
					+ "WHERE owner = "+opg.getOwner());
			preparedStatement.setString(1, opg.getName());
			preparedStatement.setString(2, opg.getAddres());
			preparedStatement.setString(3, opg.getCity());
			preparedStatement.setString(4, opg.getOib());
			preparedStatement.setString(5, opg.getTelephone());
			preparedStatement.setString(6, opg.getEmail());
			preparedStatement.setString(7, opg.getDescription());

			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		connectionPool.releaseConnection(connection);
	}
	
	public void deleteOpg(int id){
	    RowId itemID = new RowId(new Integer[] { id });
		try {
			opgSqlContainer.removeItem(itemID);
			opgSqlContainer.commit();
		} catch (UnsupportedOperationException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
