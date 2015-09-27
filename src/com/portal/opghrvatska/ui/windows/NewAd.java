package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBad;
import com.portal.opghrvatska.DB.DBopg;
import com.portal.opghrvatska.data.Ad;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.Ads;
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
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewAd extends Window implements View{
	
	private DBad dbad;
	private Ads ads;
	
	public NewAd(Ads ads){
		super("Kreiranje oglasa");
		setWidth("650px");
		center();
		setModal(true);
		this.ads = ads;
		
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        
        TextField title = new TextField("Naslov");
        title.setWidth("625px");
        
        RichTextArea description = new RichTextArea("Sadržaj");
        description.setWidth("625px");
        description.setHeight("250px");
        
        DateField startdate = new DateField("Vrijeme poèetka objave");
        startdate.setWidth("625px");
        startdate.setResolution(DateField.RESOLUTION_MIN);
        
        DateField enddate = new DateField("Vrijeme kraja objave");
        enddate.setWidth("625px");
        enddate.setResolution(DateField.RESOLUTION_MIN);
        
        Button submit = new Button("Objavi");
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {	
				Ad ad = new Ad();
				if(description.getValue().toString().isEmpty() || title.getValue().isEmpty() || startdate.getValue().toString().isEmpty() || enddate.getValue().toString().isEmpty()){
					Notification.show("Potrebno je popuniti sva polja.");
				}else{
				ad.setDescription(description.getValue().toString());
				ad.setTitle(title.getValue().toString());
				ad.setEndDate(enddate.getValue());
				ad.setStartDate(startdate.getValue());
				try {
					DBopg dbopg = new DBopg();
					if(VaadinSession.getCurrent().getAttribute(User.class)!=null){
						SQLContainer container = dbopg.getMyOPG(VaadinSession.getCurrent().getAttribute(User.class));
						if(container.size()>0){
							Item item = container.getItem(container.getIdByIndex(0));
							ad.setUser(VaadinSession.getCurrent().getAttribute(User.class).getId());
							ad.setOpg(Integer.parseInt(item.getItemProperty("idopg").getValue().toString()));
							dbad = new DBad();
							dbad.insertAd(ad);
							ads.refreshGrid();
							close();
							Notification.show("Oglas dodan.");
						}else{
							Notification.show("Morate registrirati vlastiti OPG prije kreiranja oglasa.");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
        
        content.addComponents(title,description,startdate,enddate,submit);
        content.setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
        setContent(content);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
