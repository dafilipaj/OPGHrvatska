package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBUser;
import com.portal.opghrvatska.data.User;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class Register extends Window {

	public Register(){
		super("Registracija");
		setWidth("395px");
		center();
		setModal(true);

        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);

        TextField username = new TextField("Korisnièko ime");
        username.setWidth("370px");
        
        TextField email = new TextField("E-mail");
        email.setWidth("370px");
        
        HorizontalLayout passwords = new HorizontalLayout();
        
        PasswordField pass = new PasswordField("Lozinka");
        PasswordField pass2 = new PasswordField("Ponovljena lozinka");

        passwords.addComponents(pass, pass2);

        Button register = new Button("Potvrdi");
        register.setClickShortcut(KeyCode.ENTER);
        register.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
            	User user = new User();
            	user.setUsername(username.getValue());
            	user.setPassword(pass.getValue());
            	user.setEmail(email.getValue());
            	if(pass.getValue().toString().equalsIgnoreCase(pass2.getValue().toString())){
	                try {
	                    DBUser dbuser = new DBUser();
						dbuser.registerUser(user);
						Notification.show("Registracija uspješna.");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	close();
            	}else{
            		Notification.show("Unesene lozinke nisu iste.");
            	}
            }
        });
        
        content.addComponents(username,email,passwords,register);
        content.setComponentAlignment(register, Alignment.BOTTOM_RIGHT);
        setContent(content);
	}
        

}
