package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBad;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class SidebarLayout extends VerticalLayout{

	public SidebarLayout(MainLayout mainlayout){
		setMargin(true);
		
		Label lblbody = new Label("<span style='font-size:28px'>Oglasi èlanova</span>");
		lblbody.setContentMode(Label.CONTENT_XHTML);
		
		addComponent(lblbody);

		try {
			DBad dbad = new DBad();
			SQLContainer container = dbad.getAllAds();
			if(container.size()>0){
				for(int i=0; i<container.size();i++){
					Item item = container.getItem(container.getIdByIndex(i));
					addComponent(getAdBody(item));
					if(i==4) break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VerticalLayout getAdBody(Item item){
		VerticalLayout body = new VerticalLayout();
		
		Label marginbot = new Label();
		marginbot.setWidth("10px");
		
		Panel ad = new Panel(item.getItemProperty("title").getValue().toString());
		Label content = new Label("<span style='padding:15px;'>"+item.getItemProperty("description").getValue().toString()+"</span>");
		content.setContentMode(Label.CONTENT_XHTML);
		ad.setContent(content);
		body.addComponents(ad,marginbot);
		return body;
	}

}
