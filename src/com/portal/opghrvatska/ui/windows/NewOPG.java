package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBopg;
import com.portal.opghrvatska.data.OPG;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.OPGHome;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewOPG extends Window implements View{
	 
	public NewOPG(OPGHome opghome){
		super("OPG registracija");
		setWidth("650px");
		center();
		setModal(true);
		
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        
        TextField name = new TextField("Naziv");
        name.setWidth("625px");
        
        TextField inputoib = new TextField("OIB");
        inputoib.setWidth("625px");
        
        TextField city = new TextField("Grad");
        city.setWidth("625px");
        
        TextField addres = new TextField("Adresa");
        addres.setWidth("625px");
        
        TextField email = new TextField("E-mail");
        email.setWidth("625px");
        
        TextField tele = new TextField("Tel. broj");
        tele.setWidth("625px");
        
        RichTextArea description = new RichTextArea("Opis");
        description.setWidth("625px");
        
        Button submit = new Button("Registriraj");
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(addres.getValue().isEmpty() || city.getValue().isEmpty() || description.getValue().isEmpty() || email.getValue().isEmpty() || name.getValue().isEmpty()
						|| inputoib.getValue().isEmpty() || tele.getValue().isEmpty()){
					Notification.show("Popunite sva polja.");
				}else{
					OPG opg = new OPG();
					opg.setAddres(addres.getValue().toString());
					opg.setCity(city.getValue().toString());
					opg.setDescription(description.getValue().toString());
					opg.setEmail(email.getValue().toString());
					opg.setName(name.getValue().toString());
					opg.setOib(inputoib.getValue().toString());
					opg.setOwner(VaadinSession.getCurrent().getAttribute(User.class).getId());
					opg.setTelephone(tele.getValue().toString());
					try {
						DBopg dbopg = new DBopg();
						dbopg.insertOPG(opg);
						opghome.refreshLayout();
						Notification.show("Uspješna registracija OPG-a");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
        
        content.addComponents(name,inputoib,city,addres,email,tele,description,submit);
        content.setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
        setContent(content);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
