package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBUser;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.HeaderLayout;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class Login extends Window implements View{
	
	private Navigator navigator;
	
	public Login(HeaderLayout header){
		super("Prijava");
		setWidth("210px");
		center();
		setModal(true);

        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        
        TextField username = new TextField("Korisnièko ime");
        PasswordField pass = new PasswordField("Lozinka");

        Button login = new Button("Prijava");
        login.setClickShortcut(KeyCode.ENTER);
        login.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
            	User user = new User();
            	user.setUsername(username.getValue());
            	user.setPassword(pass.getValue());
            	
            	try {
                    DBUser dbuser = new DBUser();
					if(dbuser.loginUser(user)) header.refreshHeader();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                close();
            }
        });
        
        content.addComponents(username,pass,login);
        content.setComponentAlignment(login, Alignment.BOTTOM_RIGHT);

        setContent(content);
	}


	@Override
	public void enter(ViewChangeEvent event) {
		navigator = event.getNavigator();
	}
        
}
