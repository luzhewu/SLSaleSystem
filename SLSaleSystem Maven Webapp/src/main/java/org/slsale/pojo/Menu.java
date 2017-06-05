package org.slsale.pojo;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private Function mainMenu;
	private List<Function> subMenu = new ArrayList<>();

	public Function getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(Function mainMenu) {
		this.mainMenu = mainMenu;
	}

	public List<Function> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Function> subMenu) {
		this.subMenu = subMenu;
	}

	public Menu() {
		super();
	}

}
