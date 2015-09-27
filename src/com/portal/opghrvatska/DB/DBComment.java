package com.portal.opghrvatska.DB;

import java.sql.SQLException;

import com.portal.opghrvatska.data.Comment;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DBComment {
	private JDBCConnectionPool connectionPool;
	SQLContainer commentSqlContainer;
	
	public DBComment() throws SQLException{
		connectionPool = DBHelper.getConnectionPool();
		TableQuery commentQuery = new TableQuery("comment",connectionPool);
		commentSqlContainer = new SQLContainer(commentQuery);
	}
	
	public void insertComment(Comment comment){
        Object itemId = commentSqlContainer.addItem();
        Item threadItem = commentSqlContainer.getItem(itemId);
        threadItem.getItemProperty("idcuser").setValue(comment.getIdcuser());
        threadItem.getItemProperty("idthread").setValue(comment.getIdcthread());
        threadItem.getItemProperty("content").setValue(comment.getContent());
        threadItem.getItemProperty("date").setValue(comment.getDate());
        
        try {
        	commentSqlContainer.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public SQLContainer getCommentForThread(int id) {     
        SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT comment.content,comment.date,users.first_name,users.last_name FROM comment JOIN"
					+ " users ON comment.idcuser = users.idusers WHERE comment.idthread = "+id, connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
    }
	
}
