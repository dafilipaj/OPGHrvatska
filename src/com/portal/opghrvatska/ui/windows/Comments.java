package com.portal.opghrvatska.ui.windows;

import java.sql.SQLException;
import java.util.Date;

import com.portal.opghrvatska.DB.DBComment;
import com.portal.opghrvatska.data.Comment;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.Forum;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class Comments extends Window implements View{
	Forum forum;
	Item thread;
	VerticalLayout commentArea;
	
	public Comments(Forum forum,SQLContainer container, Item thread){
		super("Komentari na raspravu'"+thread.getItemProperty("title").getValue().toString()+"'");
		this.forum = forum;
		this.thread = thread;
		setWidth("750px");
		center();
		setModal(true);
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        
        commentArea = new VerticalLayout();
		if(container.size()>0){
			for(int i=0; i<container.size();i++){
				Item item = container.getItem(container.getIdByIndex(i));
				commentArea.addComponent(getCommentBody(item));
			}
		}

        
        content.addComponent(commentArea);
        if(VaadinSession.getCurrent().getAttribute(User.class)!=null) content.addComponent(getSendForm());
        setContent(content);
	}
	
	public HorizontalLayout getSendForm(){
		HorizontalLayout body = new HorizontalLayout();
		body.setSizeFull();
		
        TextField comment = new TextField("");
        comment.setWidth("100%");
        
        Button submit = new Button("Pošalji");
        submit.setWidth("100px");
        submit.setClickShortcut(KeyCode.ENTER);
        submit.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Comment com = new Comment();
				com.setContent(comment.getValue().toLowerCase());
				Date now = new Date();
				com.setDate(now);
				com.setIdcthread(Integer.parseInt(thread.getItemProperty("idthread").getValue().toString()));
				com.setIdcuser(VaadinSession.getCurrent().getAttribute(User.class).getId());
				try {
					DBComment dbcom = new DBComment();
					dbcom.insertComment(com);
					comment.setValue("");
					VerticalLayout body = new VerticalLayout();		
					body.setSizeFull();
					
					Label content = new Label("<span style='font-size:18px;'>"+com.getContent()+"</span>");
					content.setContentMode(Label.CONTENT_XHTML);

					Label footer = new Label("<span style='font-size:14px;'>"+com.getDate().toString()+"</span>");
					footer.setContentMode(Label.CONTENT_XHTML);


					body.addComponents(content,footer);
					body.addComponent(new Label("<hr />",Label.CONTENT_XHTML));
					
					commentArea.addComponent(body);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        body.addComponents(comment,submit);
        body.setComponentAlignment(submit, Alignment.BOTTOM_RIGHT);
        body.setExpandRatio(comment, 1);
		return body;
	}
	
	public VerticalLayout getCommentBody(Item item){
		VerticalLayout body = new VerticalLayout();		
		body.setSizeFull();
		
		Label content = new Label("<span style='font-size:18px;'>"+item.getItemProperty("content").getValue().toString()+"</span>");
		content.setContentMode(Label.CONTENT_XHTML);

		Label footer = new Label("<span style='font-size:14px;'>"+item.getItemProperty("first_name").getValue().toString()+" "+item.getItemProperty("last_name").getValue().toString()+""
				+ ", vrijeme:"+item.getItemProperty("date").getValue().toString()+"</span>");
		footer.setContentMode(Label.CONTENT_XHTML);


		body.addComponents(content,footer);
		body.addComponent(new Label("<hr />",Label.CONTENT_XHTML));
		return body;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		forum.refreshForum();
		super.close();
	}
	
	@Override
	public void detach() {
		forum.refreshForum();
		super.detach();
	}
}
