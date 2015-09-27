package com.portal.opghrvatska.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * "Wrapper" for portal layouts
 * @author dfilipaj
 *
 */
public class MainLayout extends VerticalLayout implements View{

	private Navigator navigator;
	private HeaderLayout header = new HeaderLayout();
	private BodyLayout body = new BodyLayout(this);
	private SidebarLayout sidebar = new SidebarLayout(this);
	
	public MainLayout(){
		//setSizeFull();
		setMargin(true);
		header.setHeight("50px");
		header.setWidth("100%");
		
		HorizontalLayout wrap = new HorizontalLayout();
		wrap.setSizeFull();
		wrap.addComponents(body,sidebar);
		addComponents(header,wrap);
		wrap.setExpandRatio(body, 3);
		wrap.setExpandRatio(sidebar, 1);
		setExpandRatio(wrap, 1);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		navigator = event.getNavigator();		
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	
	
}
