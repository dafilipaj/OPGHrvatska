package com.portal.opghrvatska.ui;

import java.sql.SQLException;

import com.portal.opghrvatska.DB.DBad;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.windows.NewAd;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Ads extends VerticalLayout implements View{

	private HeaderLayout header = new HeaderLayout();
	private GridLayout grid;
	VerticalLayout wrap = new VerticalLayout();
	DBad dbad;

	public Ads(){
		setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		wrap.setSizeFull();
		wrap.addComponents(getMyAds());
		addComponents(header,getHeader(),wrap);
		setExpandRatio(wrap, 1);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public VerticalLayout getHeader(){
		VerticalLayout body = new VerticalLayout();

		Label adslbl = new Label("<span style='font-size:28px'>Vaši oglasi</span>");
		adslbl.setContentMode(Label.CONTENT_XHTML);

		
		Button addad = new Button("Novi oglas");
		addad.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				NewAd nadw = new NewAd(Ads.this);
				UI.getCurrent().addWindow(nadw);
			}
		});
		
		body.addComponents(adslbl,addad);
		return body;
	}
	
	public GridLayout getMyAds(){
		grid = new GridLayout();

		try {
			dbad = new DBad();
			if(VaadinSession.getCurrent().getAttribute(User.class)!=null){
	
				SQLContainer container = dbad.getAllMyAds(VaadinSession.getCurrent().getAttribute(User.class).getId());
				if(container.size()<=0){
					Label gridlbl = new Label("Trenutno nemate oglasa");
					grid.addComponent(gridlbl);
				}else{
					grid.setColumns(3);
					grid.setRows(((int) Math.ceil(container.size()/2)>0)? (int) Math.ceil(container.size()/2): 1);
					
					int j = 0;
					int k = 0;
					for(int i=0;i<container.size();i++){
						Item item = container.getItem(container.getIdByIndex(i));
						if(j==3){
							j = 0;
							k++;
						}
						grid.addComponent(getAdPanel(item), j, k);
						grid.setColumnExpandRatio(1, 1);
						j++;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grid;
	}
	
	public Panel getAdPanel(Item item){
			Panel adPanel = new Panel("<b><span style='font-size:18px;'>"+item.getItemProperty("title").getValue().toString()+"</span></b><span style='font-size:14px;'> "
					+ "until "+item.getItemProperty("enddate").getValue().toString()+" by "+item.getItemProperty("name").getValue().toString()+"</span>");
			adPanel.setWidth(Math.floor((UI.getCurrent().getPage().getBrowserWindowWidth()-75)/3)+"px");
			VerticalLayout postBody = new VerticalLayout();
			Label content = new Label("<span style='padding:15px;'>"+item.getItemProperty("description").getValue().toString()+"</span>");
			content.setContentMode(Label.CONTENT_XHTML);
			
			Label margintop = new Label();
			margintop.setHeight("10px");
			
			Label margintbot = new Label();
			margintbot.setHeight("5px");
			
			Button removeButton = new Button("x");
			removeButton.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					try {
						dbad = new DBad();
						dbad.deleteAd(Integer.parseInt(item.getItemProperty("idads").getValue().toString()));
						refreshGrid();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			
			postBody.addComponents(margintop,content,removeButton,margintbot);
			postBody.setComponentAlignment(removeButton, Alignment.BOTTOM_RIGHT);
			adPanel.setContent(postBody);
						
			return adPanel;
	}

	public void refreshGrid(){
		GridLayout newGrid = getMyAds();
		wrap.removeAllComponents();
		wrap.addComponent(newGrid);
		grid = newGrid;
	}
}
