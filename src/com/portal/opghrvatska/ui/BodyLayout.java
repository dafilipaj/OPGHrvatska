package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBPosts;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class BodyLayout extends VerticalLayout{
	
	public BodyLayout(MainLayout mainlayout){
		
		Label lblbody = new Label("<span style='font-size:28px'>Novosti</span>");
		lblbody.setContentMode(Label.CONTENT_XHTML);
		
		addComponent(lblbody);
		try {
			DBPosts dbposts = new DBPosts();
			SQLContainer container = dbposts.getAllPosts();
			for(int i=0;i<container.size();i++){
				Item item = container.getItem(container.getIdByIndex(i));
				Panel postPanel = new Panel("<b><span style='font-size:22px;'>"+item.getItemProperty("title").getValue().toString()+"</span></b><span style='font-size:14px;'> "
						+ "objavio "+item.getItemProperty("username").getValue().toString()+" datuma "+item.getItemProperty("date").getValue().toString()+"</span>");
				
				VerticalLayout postBody = new VerticalLayout();
				Label content = new Label("<span style='padding:15px;'>"+item.getItemProperty("content").getValue().toString()+"</span>");
				content.setContentMode(Label.CONTENT_XHTML);
				
				Label margintop = new Label();
				margintop.setHeight("10px");
				
				Label margintbot = new Label();
				margintbot.setHeight("5px");
				
				postBody.addComponents(margintop,content,margintbot);
				postPanel.setContent(postBody);
				
				Label margin = new Label();
				margin.setHeight("25px");
				addComponents(postPanel,margin);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
