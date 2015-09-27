package com.portal.opghrvatska.DB;

import java.io.Serializable;
import java.sql.SQLException;

import com.portal.opghrvatska.data.Ad;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DBad implements Serializable {
	private DBHelper dbhelper;
	private JDBCConnectionPool connectionPool;
	SQLContainer adSqlContainer;
	
	public DBad() throws SQLException{
		dbhelper = new DBHelper();
		connectionPool = this.dbhelper.getConnectionPool();
		TableQuery adQuery = new TableQuery("ads",connectionPool);
		adSqlContainer = new SQLContainer(adQuery);
	}
	
	public void insertAd(Ad ad){
        Object itemId = adSqlContainer.addItem();
        Item adItem = adSqlContainer.getItem(itemId);
        adItem.getItemProperty("idadsopg").setValue(ad.getOpg());
        adItem.getItemProperty("iduser").setValue(ad.getUser());
        adItem.getItemProperty("title").setValue(ad.getTitle());
        adItem.getItemProperty("description").setValue(ad.getDescription());
        adItem.getItemProperty("startdate").setValue(ad.getStartDate());
        adItem.getItemProperty("enddate").setValue(ad.getEndDate());
        
        try {
        	adSqlContainer.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public SQLContainer getAllAds() {     
        SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT ads.idads, ads.title, ads.description, ads.startdate, ads.enddate, users.first_name, users.last_name,opg.name FROM ads JOIN users "
					+ "ON ads.iduser = users.idusers JOIN opg ON ads.idadsopg = opg.idopg ORDER BY ads.enddate ASC", connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
    }
	
	public SQLContainer getAllMyAds(int id) {     
        SQLContainer container = null;
        try {
			container = new SQLContainer(new FreeformQuery("SELECT ads.idads, ads.title, ads.description, ads.startdate, ads.enddate, users.first_name, users.last_name,opg.name FROM ads JOIN users "
					+ "ON ads.iduser = users.idusers JOIN opg ON ads.idadsopg = opg.idopg WHERE ads.iduser ="+id, connectionPool));
			container.removeAllContainerFilters();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return container;
    }
	
	public void deleteAd(int id){
	    RowId itemID = new RowId(new Integer[] { id });
		try {
			adSqlContainer.removeItem(itemID);
			adSqlContainer.commit();
		} catch (UnsupportedOperationException | SQLException e) {
			e.printStackTrace();
		}
	}

}
