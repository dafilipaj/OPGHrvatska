package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;
import java.util.Date;

import com.portal.opghrvatska.DB.DBThread;
import com.portal.opghrvatska.data.ForumThread;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.Forum;
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

public class NewThread extends Window implements View{

	public NewThread(Forum forum){
		super("Nova rasprava");
		setWidth("650px");
		center();
		setModal(true);
		
		VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        
        TextField title = new TextField("Naslov");
        title.setWidth("625px");
        
        RichTextArea threadcontent = new RichTextArea("Tekst");
        threadcontent.setWidth("625px");
        
        Button submit = new Button("Objavi");
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(VaadinSession.getCurrent().getAttribute(User.class)!=null){
					if(title.getValue().isEmpty() || threadcontent.getValue().isEmpty()){
						Notification.show("Popunite sva polja.");
					}else{
						ForumThread thread = new ForumThread();
						thread.setContent(threadcontent.getValue().toString());
						thread.setTitle(title.getValue().toString());
						
					    Date now = new Date();
					    thread.setDate(now);
					    thread.setIdtuser(VaadinSession.getCurrent().getAttribute(User.class).getId());
						try {
							DBThread dbthread = new DBThread();
							dbthread.insertThread(thread);
							forum.refreshForum();
							Notification.show("Uspješna objava");
							close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
        
        content.addComponents(title,threadcontent,submit);
        content.setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
        setContent(content);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
