package com.portal.opghrvatska.DB;

import java.sql.SQLException;

import com.portal.opghrvatska.data.ForumThread;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DBThread {
	private JDBCConnectionPool connectionPool;
	SQLContainer threadSqlContainer;
	
	public DBThread() throws SQLException{
		connectionPool = DBHelper.getConnectionPool();
		TableQuery threadQuery = new TableQuery("thread",connectionPool);
        threadSqlContainer = new SQLContainer(threadQuery);
	}
	
	public void insertThread(ForumThread thread){
        Object itemId = threadSqlContainer.addItem();
        Item threadItem = threadSqlContainer.getItem(itemId);
        threadItem.getItemProperty("idtuser").setValue(thread.getIdtuser());
        threadItem.getItemProperty("title").setValue(thread.getTitle());
        threadItem.getItemProperty("content").setValue(thread.getContent());
        threadItem.getItemProperty("date").setValue(thread.getDate());
        
        try {
        	threadSqlContainer.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public SQLContainer getLastAllThreads() {     
        SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT thread.idthread,thread.title,thread.content,thread.date,users.first_name,users.last_name FROM thread JOIN"
					+ " users ON thread.idtuser = users.idusers", connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
    }
}
