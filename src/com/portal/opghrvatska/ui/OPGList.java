package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBopg;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class OPGList extends VerticalLayout implements View{
	
	private HeaderLayout header = new HeaderLayout();
	
	public OPGList(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		Label nameLbl = new Label("<span style='font-size:28px'>Popis registriranih obiteljskih poljoprivrednih gospodarstava</span>");
		nameLbl.setContentMode(Label.CONTENT_XHTML);
		
		Table opglist = new Table();
		opglist.setWidth("100%");
		opglist.setColumnHeader("idopg", "ID");
		opglist.setColumnHeader("name", "Naziv");
		opglist.setColumnHeader("first_name", "Ime vlasnika");
		opglist.setColumnHeader("last_name", "Prezime vlasnika");
		opglist.setColumnHeader("city", "Grad");
		opglist.setColumnHeader("addres", "Adresa");
		opglist.setColumnHeader("telephone", "Telefonski broj");
		opglist.setColumnHeader("email", "E-mail");
		opglist.setColumnHeader("description", "Opis");
		opglist.setColumnWidth("description", 350);


		DBopg dbopg;
		try {
			dbopg = new DBopg();
			opglist.setContainerDataSource(dbopg.getAllOPG());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HorizontalLayout wrap = new HorizontalLayout();
		wrap.setSizeFull();
		wrap.addComponents(opglist);
		addComponents(header,nameLbl,wrap);
		setExpandRatio(wrap, 1);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
