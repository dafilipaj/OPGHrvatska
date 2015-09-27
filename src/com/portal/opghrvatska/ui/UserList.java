package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBUser;
import com.portal.opghrvatska.data.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class UserList extends VerticalLayout implements View{
	
	private HeaderLayout header = new HeaderLayout();

	public UserList(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		Label nameLbl = new Label("<span style='font-size:28px'>Lista korisnika</span>");
		nameLbl.setContentMode(Label.CONTENT_XHTML);
		
		Table users = new Table();
		users.setWidth("100%");
		users.setColumnHeader("idusers", "ID");
		users.setColumnHeader("username", "Korisnièko ime");
		users.setColumnHeader("first_name", "Ime");
		users.setColumnHeader("last_name", "Prezime");
		users.setColumnHeader("email", "E-mail");
		users.setColumnHeader("role_name", "Uloga");
		users.addGeneratedColumn("Brisanje", new Table.ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Button removeButton = new Button("x");
				removeButton.addClickListener(new ClickListener(){
					public void buttonClick(ClickEvent event) {
						int id = Integer.parseInt(users.getItem(itemId).getItemProperty("idusers").getValue().toString());
						if(id==VaadinSession.getCurrent().getAttribute(User.class).getId()){
							Notification.show("Odaberite drugog korisnika.");
						}else{
							DBUser dbuser;
							try {
								dbuser = new DBUser();
								dbuser.deleteUser(id);
								users.setContainerDataSource(dbuser.getAllUsers());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				return removeButton;
			}
		});
		
		DBUser dbuser;
		try {
			dbuser = new DBUser();
			users.setContainerDataSource(dbuser.getAllUsers());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HorizontalLayout wrap = new HorizontalLayout();
		wrap.setSizeFull();
		wrap.addComponents(users);
		addComponents(header,nameLbl,wrap);
		setExpandRatio(wrap, 1);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}

