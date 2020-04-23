package com.techverito.d2h;

/**
 * main class to run D2H project
 */
public class Main {

    public static void main(String[] args) {
        D2hDashboard d2hDashboard = new D2hDashboard();
        UserD2hAccount account = new UserD2hAccount();
        d2hDashboard.menu(ConstantValue.MENU_LIST, ConstantValue.SUBSCRIPTION(), 0, account);

    }

}
