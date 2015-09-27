package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBComment;
import com.portal.opghrvatska.DB.DBThread;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.windows.Comments;
import com.portal.opghrvatska.ui.windows.NewThread;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Forum extends VerticalLayout implements View{

	private HeaderLayout header = new HeaderLayout();
	VerticalLayout wrap = new VerticalLayout();

	public Forum(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		Label lbl = new Label("<span style='font-size:28px'>Javne rasprave</span>");
		lbl.setContentMode(Label.CONTENT_XHTML);
		
		Button newPost = new Button("Zapoèni raspravu");
		newPost.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				NewThread nth = new NewThread(Forum.this);
				UI.getCurrent().addWindow(nth);
			}
		});
		
		HorizontalLayout wrap2 = new HorizontalLayout();

		
		wrap.setSizeFull();
		wrap.addComponents(getThreads());
		if(VaadinSession.getCurrent().getAttribute(User.class)!=null){
			wrap2.addComponents(lbl,newPost);
		}else{
			wrap2.addComponents(lbl);	
		}
		addComponents(header,wrap2,wrap);
		setExpandRatio(wrap, 1);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public VerticalLayout getThreads(){
		VerticalLayout body = new VerticalLayout();
		
		try {
			DBThread dbthread = new DBThread();
			SQLContainer container = dbthread.getLastAllThreads();
			if(container.size()>0){
				for(int i=0;i<container.size();i++){
					Item item = container.getItem(container.getIdByIndex(i));
					body.addComponent(getThreadBody(item));
				}
			}else{
				Label gridlbl = new Label("Trenutno nema raspava");
				body.addComponent(gridlbl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return body;
	}
	
	public void refreshForum(){
		wrap.removeAllComponents();
		wrap.addComponent(getThreads());
	}
	
	public VerticalLayout getThreadBody(Item item){		
		VerticalLayout body = new VerticalLayout();
		
		HorizontalLayout wrap = new HorizontalLayout();
		VerticalLayout wrap2 = new VerticalLayout();
		
		Label lbl = new Label("<span style='font-size:24px'>"+item.getItemProperty("title").getValue().toString()+" "
				+ "</span><span style='font-size:14px'>"+item.getItemProperty("date").getValue().toString()+"</span>");
		lbl.setContentMode(Label.CONTENT_XHTML);
		Label lblc;
		if(item.getItemProperty("content").getValue().toString().length()>125){
			lblc = new Label("<span style='font-size:15px'>"+item.getItemProperty("content").getValue().toString().substring(0, 125)+"...</span>");
			lblc.setContentMode(Label.CONTENT_XHTML);
		}else{
			lblc = new Label("<span style='font-size:15px'>"+item.getItemProperty("content").getValue().toString()+"</span>");
			lblc.setContentMode(Label.CONTENT_XHTML);
		}
		
		Button comments = null;
		try {
			DBComment dbcom = new DBComment();
			SQLContainer container = dbcom.getCommentForThread(Integer.parseInt(item.getItemProperty("idthread").getValue().toString()));
			int sum = (container==null)? 0 : container.size();
			comments = new Button(sum+" komentara");
			comments.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Comments com = new Comments(Forum.this,container, item);
					UI.getCurrent().addWindow(com);
				}
			});
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		wrap2.addComponent(lbl);
		wrap2.addComponent(lblc);
		Label space = new Label();
		space.setWidth("100%");
		wrap.setSizeFull();
		wrap.addComponents(wrap2,space,comments);
		wrap.setComponentAlignment(comments, Alignment.MIDDLE_RIGHT);

		body.addComponent(wrap);
		
		body.addComponent(new Label("<hr />",Label.CONTENT_XHTML));
		Label mbot = new Label();
		mbot.setHeight("5px");
		body.addComponent(mbot);
		return body;
	}

}
