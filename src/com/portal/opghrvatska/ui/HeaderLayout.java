package com.portal.opghrvatska.ui;

import java.io.File;

import com.portal.opghrvatska.data.User;
import com.portal.opghrvatska.ui.windows.Login;
import com.portal.opghrvatska.ui.windows.Register;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

public class HeaderLayout extends HorizontalLayout implements View{

	private Navigator navigator;
	MenuBar barmenu;

	public HeaderLayout(){

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath +"/WEB-INF/images/opglogo.png"));

		Image logo = new Image();
		logo.setSource(resource);
		logo.setHeight("32px");
		logo.addClickListener(new ClickListener() {
			
			@Override
			public void click(ClickEvent event) {
				getUI().getNavigator().navigateTo("main");
			}
		});
		
		Label space = new Label();
		space.setWidth("50%");
		
		barmenu = newMenu();

		addComponents(logo);
		setDefaultComponentAlignment(Alignment.TOP_RIGHT);
		addComponent(barmenu);

	}
	
	public MenuBar newMenu(){
		MenuBar newbarmenu = new MenuBar();
		
		MenuBar.Command login = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Login l = new Login(HeaderLayout.this);
				UI.getCurrent().addWindow(l);				
			}
		};
		
		MenuBar.Command logout = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				VaadinSession.getCurrent().setAttribute(User.class, null);
				getUI().getNavigator().addView("main", new MainLayout());
				getUI().getNavigator().navigateTo("main");
				refreshHeader();
			}
		};
		
		MenuBar.Command register = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Register r = new Register();	
				UI.getCurrent().addWindow(r);				
			}
		};
		
		MenuBar.Command users = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().addView("users", new UserList());
				getUI().getNavigator().navigateTo("users");			
			}
		};
		
		MenuBar.Command posts = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().addView("posts", new PostsManagement());
				getUI().getNavigator().navigateTo("posts");			
			}
		};
		
		MenuBar.Command homecmd = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().navigateTo("main");			
			}
		};
		
		MenuBar.Command opgcmd = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				getUI().getNavigator().addView("opg", new OPGHome());
				getUI().getNavigator().navigateTo("opg");			

			}
		};
		
		MenuBar.Command profilcmd = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				getUI().getNavigator().addView("profile", new Profile());
				getUI().getNavigator().navigateTo("profile");			

			}
		};
		
		MenuBar.Command opglistcmd = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().addView("opglist", new OPGList());
				getUI().getNavigator().navigateTo("opglist");					
			}
		};
		
		MenuBar.Command adscmd = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().addView("ads", new Ads());
				getUI().getNavigator().navigateTo("ads");	
			}
		};
		
		MenuBar.Command forumcmd = new Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().addView("forum", new Forum());
				getUI().getNavigator().navigateTo("forum");
			}
		};
		
		MenuItem home = newbarmenu.addItem("Poèetna", homecmd);
		MenuItem opgs = newbarmenu.addItem("OPG popis", opglistcmd);
		MenuItem forum = newbarmenu.addItem("Forum", forumcmd);
		
		if(VaadinSession.getCurrent().getAttribute(User.class) != null){
			MenuItem userItems = newbarmenu.addItem(VaadinSession.getCurrent().getAttribute(User.class).getUsername(), null);
			userItems.addItem("Osobni profil", profilcmd);
			userItems.addItem("OPG profil", opgcmd);
			userItems.addItem("Oglasi", adscmd);
			
			if(VaadinSession.getCurrent().getAttribute(User.class).getRole().equalsIgnoreCase("admin")){
				MenuItem adminItems = newbarmenu.addItem("Administracija", null);
				adminItems.addItem("Lista korisnika", users);
				adminItems.addItem("Objave", posts);
			}

			MenuItem btnLogout = newbarmenu.addItem("Odjava", null, logout);
		}else{
			MenuItem btnLogin = newbarmenu.addItem("Prijava", login);
			MenuItem btnReg = newbarmenu.addItem("Registracija", register);
		}
		return newbarmenu;
	}
	
	public void refreshHeader(){
		MenuBar newMenu = newMenu();
		replaceComponent(barmenu, newMenu);
		barmenu = newMenu;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshHeader();
	}
	
	

}
