package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBPosts;
import com.portal.opghrvatska.ui.windows.NewPost;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PostsManagement extends VerticalLayout implements View{

	private HeaderLayout header = new HeaderLayout();
	Table posts = new Table();
	
	public PostsManagement(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		Label nameLbl = new Label("<span style='font-size:28px'>Lista objava</span>");
		nameLbl.setContentMode(Label.CONTENT_XHTML);
		
		Button newPost = new Button("Nova objava");
		newPost.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				NewPost npw = new NewPost(posts);
				UI.getCurrent().addWindow(npw);
			}
		});
		
		posts.setWidth("100%");
		posts.setColumnHeader("idposts", "ID");
		posts.setColumnHeader("title", "Naslov");
		posts.setColumnHeader("content", "Saržaj");
		posts.setColumnHeader("username", "Autor");
		posts.setColumnHeader("date", "Datum objave");
		posts.setColumnWidth("content", 450);
		
		posts.addGeneratedColumn("Brisanje", new Table.ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Button removeButton = new Button("x");
				removeButton.addClickListener(new ClickListener(){
					public void buttonClick(ClickEvent event) {
						int id = Integer.parseInt(posts.getItem(itemId).getItemProperty("idposts").getValue().toString());

						DBPosts dbposts;
						try {
							dbposts = new DBPosts();
							dbposts.deletePost(id);
							posts.setContainerDataSource(dbposts.getAllPosts());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				return removeButton;
			}
		});
		
		DBPosts dbposts;
		try {
			dbposts = new DBPosts();
			posts.setContainerDataSource(dbposts.getAllPosts());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		HorizontalLayout wrap = new HorizontalLayout();
		HorizontalLayout wrap2 = new HorizontalLayout();

		wrap.setSizeFull();
		wrap.addComponents(posts);
		wrap2.addComponents(nameLbl,newPost);
		addComponents(header,wrap2,wrap);
		setExpandRatio(wrap, 1);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
