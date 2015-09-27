package com.portal.opghrvatska.DB;

import java.sql.SQLException;

import com.portal.opghrvatska.data.Post;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DBPosts {
	private JDBCConnectionPool connectionPool;
	SQLContainer postsSqlContainer;
	
	public DBPosts() throws SQLException{
		connectionPool = DBHelper.getConnectionPool();
		TableQuery postQuery = new TableQuery("posts",connectionPool);
		postsSqlContainer = new SQLContainer(postQuery);
	}
	
	public void insertPost(Post post){
        Object itemId = postsSqlContainer.addItem();
        Item postItem = postsSqlContainer.getItem(itemId);
        postItem.getItemProperty("title").setValue(post.getTitle());
        postItem.getItemProperty("content").setValue(post.getContent());
        postItem.getItemProperty("author").setValue(post.getAuthor());
        postItem.getItemProperty("date").setValue(post.getDate());
        
        try {
        	postsSqlContainer.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public SQLContainer getAllPosts(){
        
        SQLContainer container = null;
        
		try {
			container = new SQLContainer(new FreeformQuery("SELECT posts.idposts,posts.title,posts.content,posts.date,users.username FROM posts "
					+ "JOIN users ON posts.author = users.idusers ORDER BY posts.idposts DESC", connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return container;
	}
	
	public void deletePost(int id){
	    RowId itemID = new RowId(new Integer[] { id });
		try {
			postsSqlContainer.removeItem(itemID);
			postsSqlContainer.commit();
		} catch (UnsupportedOperationException | SQLException e) {
			e.printStackTrace();
		}
	}

}
