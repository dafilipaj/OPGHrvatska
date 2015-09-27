package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;
import java.util.Date;

import com.portal.opghrvatska.DB.DBPosts;
import com.portal.opghrvatska.data.Post;
import com.portal.opghrvatska.data.User;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class NewPost extends Window implements View{

	public NewPost(Table posts){
		super("Nova objava");
		setWidth("650px");
		center();
		setModal(true);
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        
        TextField title = new TextField("Naslov");
        title.setWidth("625px");
        
        RichTextArea rtarea = new RichTextArea("Sadržaj");
        rtarea.setWidth("625px");
        rtarea.setHeight("450px");
        
        Button submit = new Button("Objavi");
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Post post = new Post();
				post.setAuthor(VaadinSession.getCurrent().getAttribute(User.class).getId());
				post.setContent(rtarea.getValue().toString());
				post.setTitle(title.getValue().toString());
				
			    Date now = new Date();
			    //String strDate = now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+" "+now.getDate()+"/"+now.getMonth()+"/"+now.getYear();
			    
				post.setDate(now);
				close();
				
				try {
					DBPosts dbposts = new DBPosts();
					dbposts.insertPost(post);
					posts.setContainerDataSource(dbposts.getAllPosts());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
        content.addComponents(title,rtarea,submit);
        content.setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
        setContent(content);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
