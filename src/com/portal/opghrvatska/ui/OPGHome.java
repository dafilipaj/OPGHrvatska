package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBopg;
import com.portal.opghrvatska.data.OPG;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.windows.NewOPG;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
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
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class OPGHome extends VerticalLayout implements View{

	private HeaderLayout header = new HeaderLayout();
	HorizontalLayout wrap = new HorizontalLayout();


	public OPGHome(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		wrap.setSizeFull();

		try {
			DBopg dbopg = new DBopg();
			if(VaadinSession.getCurrent().getAttribute(User.class)!=null){
				SQLContainer container = dbopg.getMyOPG(VaadinSession.getCurrent().getAttribute(User.class));
				if(container.size()>0){
					VerticalLayout opglay = getOPGLayout(container.getItem(container.getIdByIndex(0)));
					opglay.setWidth("650px");
					wrap.addComponent(opglay);
					wrap.setComponentAlignment(opglay, Alignment.TOP_CENTER);
				}else{
					wrap.addComponent(getEmptyLayout());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addComponents(header,wrap);
		setExpandRatio(wrap, 1);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public VerticalLayout getEmptyLayout(){
		VerticalLayout body = new VerticalLayout();
		Label empty = new Label("<span style='font-size:28px'>Želite registrirati vlastiti OPG?</span>");
		empty.setContentMode(Label.CONTENT_XHTML);
		
		Button addOpg = new Button("Novi opg");
		addOpg.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				NewOPG nopgw = new NewOPG(OPGHome.this);
				UI.getCurrent().addWindow(nopgw);
			}
		});
		
		body.addComponents(empty,addOpg);
		return body;
	}
	
	public VerticalLayout getOPGLayout(Item opgItem){
		VerticalLayout body = new VerticalLayout();
		Label nameLbl = new Label("<span style='font-size:28px'>"+opgItem.getItemProperty("name").getValue().toString()+"</span>");
		nameLbl.setContentMode(Label.CONTENT_XHTML);
        
        TextField name = new TextField("Naziv");
        name.setWidth("625px");
        name.setValue(opgItem.getItemProperty("name").getValue().toString());
        
        TextField inputoib = new TextField("OIB");
        inputoib.setWidth("625px");
        inputoib.setValue(opgItem.getItemProperty("OIB").getValue().toString());

        TextField city = new TextField("Grad");
        city.setWidth("625px");
        city.setValue(opgItem.getItemProperty("city").getValue().toString());
        
        TextField addres = new TextField("Adresa");
        addres.setWidth("625px");
        addres.setValue(opgItem.getItemProperty("addres").getValue().toString());

        TextField email = new TextField("E-mail");
        email.setWidth("625px");
        email.setValue(opgItem.getItemProperty("email").getValue().toString());
        
        TextField tele = new TextField("Tel. broj");
        tele.setWidth("625px");
        tele.setValue(opgItem.getItemProperty("telephone").getValue().toString());
        
        RichTextArea description = new RichTextArea("Opis");
        description.setWidth("625px");
        description.setValue(opgItem.getItemProperty("description").getValue().toString());

        
        Button submit = new Button("Ažuriraj");
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				OPG opg = new OPG();
				opg.setAddres(addres.getValue().toString());
				opg.setCity(city.getValue().toString());
				opg.setDescription(description.getValue().toString());
				opg.setEmail(email.getValue().toString());
				opg.setName(name.getValue().toString());
				opg.setOib(inputoib.getValue().toString());
				opg.setOwner(VaadinSession.getCurrent().getAttribute(User.class).getId());
				opg.setTelephone(tele.getValue().toString());
				DBopg dbopg;
				try {
					dbopg = new DBopg();
					dbopg.updateOPG(opg);
					Notification.show("OPG uspješno ažuriran");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        body.addComponents(nameLbl, name,inputoib,city,addres,email,tele,description,submit);
		return body;
	}
	
	public void refreshLayout(){
		wrap.removeAllComponents();
		try {
			DBopg dbopg = new DBopg();
			if(VaadinSession.getCurrent().getAttribute(User.class)!=null){
				SQLContainer container = dbopg.getMyOPG(VaadinSession.getCurrent().getAttribute(User.class));
				if(container.size()>0){
					VerticalLayout opglay = getOPGLayout(container.getItem(container.getIdByIndex(0)));
					opglay.setWidth("650px");
					wrap.addComponent(opglay);
					wrap.setComponentAlignment(opglay, Alignment.TOP_CENTER);
				}else{
					wrap.addComponent(getEmptyLayout());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
