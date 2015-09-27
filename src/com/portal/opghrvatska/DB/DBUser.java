package com.portal.opghrvatska.DB;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.portal.opghrvatska.data.User;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;

public class DBUser {
	private JDBCConnectionPool connectionPool;
	SQLContainer userSqlContainer;
	
	public DBUser() throws SQLException{
		connectionPool = DBHelper.getConnectionPool();
		TableQuery userQuery = new TableQuery("users",connectionPool);
        userSqlContainer = new SQLContainer(userQuery);
	}
	
	public void registerUser(User user){        
        Object itemId = userSqlContainer.addItem();
        Item userItem = userSqlContainer.getItem(itemId);
        userItem.getItemProperty("username").setValue(user.getUsername().replaceAll("\\s+$", ""));
        userItem.getItemProperty("password").setValue(user.getPassword().replaceAll("\\s+$", ""));
        userItem.getItemProperty("email").setValue(user.getEmail().replaceAll("\\s+$", ""));
        userItem.getItemProperty("role").setValue(2);
        
        try {
            userSqlContainer.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    
	}
	
	public boolean loginUser(User user){
		//userSqlContainer.addContainerFilter(new And(new Like("username", user.getUsername()), new Like("password", user.getPassword())));

		try {
			SQLContainer container = new SQLContainer(new FreeformQuery("SELECT users.*,users_roles.role_name FROM users JOIN users_roles ON users.role = users_roles.idusers_roles "
					+ "WHERE username = '"+user.getUsername()+"' AND password = '"+user.getPassword()+"'", connectionPool));
			
			 if(container.size() == 0 ){
				 	Notification.show("Korisnik ne postoji.");
				 	container.removeAllContainerFilters();
				 	return false;
			 }else{
				 	Item item = container.getItem(container.getIdByIndex(0));
				 	user.setId(Integer.parseInt(item.getItemProperty("idusers").getValue().toString()));
				 	user.setEmail(item.getItemProperty("email").getValue().toString());
				 	if(item.getItemProperty("first_name").getValue()!=null) user.setFirst_name(item.getItemProperty("first_name").getValue().toString());
				 	if(item.getItemProperty("last_name").getValue()!=null) user.setLast_name(item.getItemProperty("last_name").getValue().toString());
				 	user.setRole(item.getItemProperty("role_name").getValue().toString());
				 	
					Notification.show("Uspješna prijava!");
					VaadinSession.getCurrent().setAttribute(User.class, user);
					container.removeAllContainerFilters();
					return true;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public SQLContainer getAllUsers() {     
        SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT users.idusers,users.username,users.first_name,users.last_name,users.email,users_roles.role_name FROM users JOIN users_roles "
					+ "ON users.role = users_roles.idusers_roles", connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
    }
	
	public void updateUser(User user){        
        Connection connection = null;
		try {
			connection = (Connection)	connectionPool.reserveConnection();
			connection.setAutoCommit(false);
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? "
					+ "WHERE idusers = "+user.getId());
			preparedStatement.setString(1, user.getFirst_name());
			preparedStatement.setString(2, user.getLast_name());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		connectionPool.releaseConnection(connection);
	}
	
	public void deleteUser(int id){
	    RowId itemID = new RowId(new Integer[] { id });
		try {
			userSqlContainer.removeItem(itemID);
			userSqlContainer.commit();
		} catch (UnsupportedOperationException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
