package com.portal.opghrvatska;

import javax.servlet.annotation.WebServlet;

import com.portal.opghrvatska.DB.DBHelper;
import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.Ads;
import com.portal.opghrvatska.ui.Forum;
import com.portal.opghrvatska.ui.MainLayout;
import com.portal.opghrvatska.ui.OPGHome;
import com.portal.opghrvatska.ui.OPGList;
import com.portal.opghrvatska.ui.PostsManagement;
import com.portal.opghrvatska.ui.Profile;
import com.portal.opghrvatska.ui.UserList;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("opghrvatska")
public class OpghrvatskaUI extends UI {
	private Navigator navigator;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = OpghrvatskaUI.class, widgetset = "com.portal.opghrvatska.widgetset.OpghrvatskaWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("OPG Hrvatska");
		DBHelper dbhelp = new DBHelper();
		
		navigator = new Navigator(this, this);
		navigator.addView("", new MainLayout());
		navigator.addView("main", new MainLayout());
		navigator.addView("posts", new PostsManagement());
		navigator.addView("users", new UserList());
		navigator.addView("opg", new OPGHome());
		navigator.addView("profile", new Profile());
		navigator.addView("opglist", new OPGList());
		navigator.addView("ads", new Ads());
		navigator.addView("forum", new Forum());

		
		navigator.addViewChangeListener(new ViewChangeListener() {
			
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				if(event.getNewView() instanceof UserList || event.getNewView() instanceof PostsManagement){
					if(VaadinSession.getCurrent().getAttribute(User.class)==null || !VaadinSession.getCurrent().getAttribute(User.class).getRole().equalsIgnoreCase("admin")){
						navigator.navigateTo("main");
						return false;
					}else{
						return true;
					}
				}else if(event.getNewView() instanceof OPGHome || event.getNewView() instanceof Profile || event.getNewView() instanceof Ads){
					if(VaadinSession.getCurrent().getAttribute(User.class)==null){
						navigator.navigateTo("main");
						return false;
					}else{
						return true;
					}
				}else{
					return true;
				}
			}
			
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}