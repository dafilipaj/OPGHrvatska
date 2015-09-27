package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBUser;
import com.portal.opghrvatska.data.User;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Profile extends VerticalLayout implements View{

	private HeaderLayout header = new HeaderLayout();

	public Profile(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		HorizontalLayout wrap = new HorizontalLayout();
		wrap.setSizeFull();
		
		User user = VaadinSession.getCurrent().getAttribute(User.class);
		if(user!=null){
			
			VerticalLayout profile = new VerticalLayout();
			profile.setWidth("450px");
			
	        Label title = new Label("<span style='font-size:28px;'>Ureðivanje profila</span>");
	        title.setContentMode(Label.CONTENT_XHTML);
	
	        TextField first_name = new TextField("Ime");
	        first_name.setWidth("425px");
	        first_name.setValue((user.getFirst_name()==null)? "" : user.getFirst_name());
	        
	        TextField last_name = new TextField("Prezime");
	        last_name.setWidth("425px");
	        last_name.setValue((user.getLast_name()==null)? "" : user.getLast_name());
	        
	        TextField email = new TextField("Email");
	        email.setWidth("425px");
	        email.setValue(user.getEmail());
	        
	        Label pitanje = new Label("<span style='font-size:28px'>Nova lozinka?</style>");
	        pitanje.setContentMode(Label.CONTENT_XHTML);
	        
	        PasswordField pass = new PasswordField("Nova lozinka");
	        pass.setWidth("425px");
	        
	        PasswordField pass2 = new PasswordField("Ponovljena lozinka");
	        pass2.setWidth("425px");
	        
	        Button edit = new Button("Spremi");
	        edit.setClickShortcut(KeyCode.ENTER);
	        edit.addClickListener(new ClickListener() {
	            public void buttonClick(ClickEvent event) {
	            	user.setEmail(email.getValue().toString());
	            	user.setFirst_name(first_name.getValue().toString());
	            	user.setLast_name(last_name.getValue().toString());
	            	if(!pass.isEmpty()||!pass2.isEmpty()){
	            		if(pass.getValue().equalsIgnoreCase(pass2.getValue())){
	            			user.setPassword(pass.getValue().toString());
	            			
		            		DBUser dbuser;
							try {
								dbuser = new DBUser();
			            		dbuser.updateUser(user);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            		}else{
	                		Notification.show("Unesene lozinke nisu iste.");
	            		}
	            	}else{
	            		DBUser dbuser;
						try {
							dbuser = new DBUser();
		            		dbuser.updateUser(user);
		            		Notification.show("Profil ažuriran.");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            }
	        });
			
	        profile.addComponents(title,first_name,last_name,email,pitanje,pass,pass2,edit);
			wrap.addComponents(profile);
	        wrap.setComponentAlignment(profile, Alignment.TOP_CENTER);
		}
		addComponents(header,wrap);
		setExpandRatio(wrap, 1);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
