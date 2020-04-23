package com.techverito.d2h;

import java.util.*;

/**
 * This class use for D2H operation
 */
class D2hDashboard {


    /**
     * This is D2H dashboard menu method.it perform all operation of D2H.
     * This method validate input and call display method for show output on
     * console
     *
     * @param menuList     it is D2h menu list it is constant
     * @param subscription it is subscription object. it content all
     *                     available subscription option it is constant,
     * @param number       it is integer value. it is indicate that D2H menu number
     * @param account      it is user account
     */
    public void menu(List<String> menuList, Subscription subscription, int number, UserD2hAccount account) {
        switch (number) {
            case 0:
                display(menuList);
                break;
            case 1:
                display(menuList.get(number - 1));
                display("Current balance is " + balance(account) + " Rs.");
                break;
            case 2:
                long amount = inputInt("Enter the amount to recharge:- ");
                recharge(account, amount);
                break;
            case 3:
                display(menuList.get(number - 1));
                displayAvailableSubscription(subscription);

                break;
            case 4:
                display(menuList.get(number - 1));
                String pack = input("Enter the Pack you wish to subscribe: (Silver: S, Gold: G):");
                if (validatePackCode(pack)) break;
                int months = inputInt("Enter the months:");
                if (validatePackMonth(months)) break;
                account = userPackDisplaySubscription(subscription, account, pack, months);
                break;
            case 5:
                display(menuList.get(number - 1));
                display("channels=" + subscription.getChannel().keySet());
                String channelList = input("Enter channel names to add (separated by commas):");
                account = userChannelSubscriptionDisplay(subscription, account, channelList);
                break;
            case 6:
                display(menuList.get(number - 1));
                display("service=" + subscription.getService().keySet());
                String serviceNme = input("Enter the service name:");
                account = userServiceSubscriptionDisplay(subscription, account, serviceNme);
                break;
            case 7:
                display(menuList.get(number - 1));
                display("Currently subscribed packs and channels:" +
                        subscriptionName(account.getSubscription().getPack().keySet()) + "+" +
                        subscriptionName(account.getSubscription().getChannel().keySet()) + ".");
                display("Currently subscribed services:" +
                        subscriptionName(account.getSubscription().getService().keySet()));

                break;
            case 8:
                display(menuList.get(number - 1));
                String email = input("Enter the email:-");
                long phone = inputLong("Enter phone:-");
                updateContact(account, phone, email);

                break;
            case 9:
                display(menuList.get(number - 1));
                return;
            default:
                display("select correct option ");
        }

        menu(menuList, subscription, inputInt("Enter the option:"), account);

    }
    /**
     * This method validate pack subscription month
     * @param months pack subscription month
     * @return if pack subscription month is wrong than return true otherwise false;
     */
    public boolean validatePackMonth(int months) {
        if (months <= 0) {
            display("Invalid months");
            return true;
        }
        return false;
    }

    /**
     * This method validate pack code
     * @param pack code of pack
     * @return if pack code is wrong than return true otherwise false;
     */
    public boolean validatePackCode(String pack) {
        if (!("G".equalsIgnoreCase(pack) || "S".equalsIgnoreCase(pack))) {
            display("Please select correct pack");
            return true;
        }
        return false;
    }

    /**
     * This method validate user contact details
     *
     * @param email user email id
     * @param phone user phone number
     * @return return false if contact details are correct otherwise true
     */
    private boolean validateContact(String email, long phone) {
        boolean flag = false;
        if (!String.valueOf(phone).matches("[0-9]{10}")) {
            display("invalid phone number");
            flag=true;
        }
        if (!email.matches("^(.+)@(.+)$")) {
            display("invalid Emil id ");
            flag=true;
        }
        return flag;
    }

    /**
     * This method use to service subscription and display output
     *
     * @param subscription constant list of available subscription
     * @param account      user account
     * @param serviceNme   subscribe service name
     * @return return user updated account data
     */
    public UserD2hAccount userServiceSubscriptionDisplay(Subscription subscription, UserD2hAccount account, String serviceNme) {
        if (subscription.getService().containsKey(serviceNme)) {
            if (balance(account) > subscription.getService().get(serviceNme)) {
                account = userServiceSubscription(account, subscription, serviceNme);
                display(serviceNme + " -Service added successfully.");
                notification(account.getEmail(), account.getPhoneNumber());
            } else {
                display("please recharge account");
                return account;
            }
        } else {
            display("service is not available :-" + serviceNme);
            return account;
        }

        display("Account balance:" + balance(account) + "Rs.");
        return account;
    }

    /**
     * This method use to channel List subscription and display output
     *
     * @param subscription constant list of available subscription
     * @param account      user account
     * @param channelList  subscribe channel List
     * @return return updated user account data
     */
    public UserD2hAccount userChannelSubscriptionDisplay(Subscription subscription, UserD2hAccount account, String channelList) {
        String[] channels = channelList.split(",");
        for (String channel : channels) {
            if (subscription.getChannel().containsKey(channel)) {
                if (balance(account) > subscription.getChannel().get(channel)) {
                    account = userChannelSubscription(account, subscription, channel);
                    display(channel+"-Channels added successfully.");
                } else {
                    display("please recharge account");
                    return account;
                }
            } else {
                display("channel is not available :-" + channel);
                break;
            }
        }
        display("Account balance:" + balance(account) + "Rs.");
        return account;
    }

    /**
     * This method use to recharge account
     *
     * @param account user account
     * @param amount  mount to be recharge
     * @return return updated user account data
     */
    public long recharge(UserD2hAccount account, long amount) {
        long balance =addBalance(account, amount);
        if (0 < amount) {
            display("Current balance is " + balance + " Rs.");
        } else {
            display("Invalid amount");
        }
        return balance;
    }

    /**
     * This method use to pack subscription and display output
     *
     * @param subscription constant list of available subscription
     * @param account      user account
     * @param pack         subscribe pack List
     * @param months       number of months subscription.
     * @return return updated user account data
     */
    public UserD2hAccount userPackDisplaySubscription(Subscription subscription, UserD2hAccount account, String pack, int months) {
        account = userPackSubscription(account, subscription, pack, months);
        Subscription accountSubscription = account.getSubscription();
        String accountPack = subscriptionName(accountSubscription.getPack().keySet());
        if (accountPack.isEmpty()) {
            display("please recharge account");
            return account;
        }
        int accountPackPrice = accountSubscription.getPack().get(accountPack);
        display("subscribed the following packs -" + accountPack);
        display("Monthly price: " + accountPackPrice + " Rs.");
        display("No of months: " + months);
        display("Subscription Amount: " + accountPackPrice * months + " Rs.");
        display("Discount applied: " + discount(months, accountPackPrice) + " Rs.");
        display("Final Price after discount:" + (accountPackPrice * months - discount(months, accountPackPrice)) + " Rs.");
        display("Account balance:" + balance(account) + " Rs.");
        notification(account.getEmail(), account.getPhoneNumber());
        return account;
    }

    /**
     * This method use to find subscription name
     *
     * @param names it is set of subscription
     * @return it return individual category all subscription name (ex Zee+Sony.)
     */
    public String subscriptionName(Set<String> names) {
        String name = "";
        for (String value : names) {
            if ("".equals(name)) {
                name = value;
            } else {
                name = name.concat("+" + value);
            }
        }
        return name;
    }

    /**
     * This method use to service subscription
     *
     * @param account      user account which want to subscribe.
     * @param subscription list of subscription
     * @param serviceName  service which is subscribe
     * @return updated user account object
     */
    public UserD2hAccount userServiceSubscription(UserD2hAccount account, Subscription subscription, String serviceName) {
        int price;

        if (subscription.getService().containsKey(serviceName)
                && account.getBalance() >= (price = subscription.getService().get(serviceName))) {
            removeBalance(account, price);
            account.setSubscription(addUserSubscription(account.getSubscription(), serviceName, price, 1, "service"));
        }

        return account;
    }

    /**
     * This method use to channel subscription
     *
     * @param subscription constant list of available subscription
     * @param account      user account
     * @param channel      subscribe channel
     * @return return updated user account data
     */
    public UserD2hAccount userChannelSubscription(UserD2hAccount account, Subscription subscription, String channel) {

        int price;
        if (subscription.getChannel().containsKey(channel)
                && account.getBalance() >= (price = subscription.getChannel().get(channel))) {
            removeBalance(account, price);
            account.setSubscription(addUserSubscription(account.getSubscription(), channel, price, 1, "channel"));
        }
        return account;
    }

    /**
     * This method use to channel subscription
     *
     * @param subscription constant list of available subscription
     * @param account      user account
     * @param pack         subscribe pack
     * @param months       number of months subscription
     * @return return updated user account data
     */
    public UserD2hAccount userPackSubscription(UserD2hAccount account, Subscription subscription, String pack,
                                                int months) {
        String selectedPack;
        int packPrice;
        int discount;
        int finalPrice;
        selectedPack = userSelected(pack);
        packPrice = packMonthlyPrice(subscription, selectedPack);
        discount = discount(months, packPrice);
        if (balance(account) >= (finalPrice = ((packPrice * months) - discount))) {
            removeBalance(account, finalPrice);
            account.setSubscription(
                    addUserSubscription(account.getSubscription(), selectedPack, packPrice, months, "pack"));

        }
        return account;
    }

    /**
     * This method use to send notification
     *
     * @param email       user email id
     * @param phoneNumber user phone number
     */
    private void notification(String email, Long phoneNumber) {
        display("Email notification sent successfully");
        display("SMS notification sent successfully");

    }

    /**
     * This method use to subscription
     *
     * @param subscription user account subscription object
     * @param name         name of subscription channel,pack and service
     * @param price        price of subscription channel,pack and service
     * @param month        number of month subscription
     * @param type         type of subscription channel,pack and service
     * @return update subscription object .
     */
    private Subscription addUserSubscription(Subscription subscription, String name, int price, int month,
                                             String type) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(name, price);
        if ("pack".equals(type)) {
            subscription.setPack(map);
            subscription.setPackMonths(month);
        }
        if ("channel".equals(type)) {
            subscription.getChannel().put(name, price);
            subscription.setChannelMonths(month);
        }
        if ("service".equals(type)) {
            subscription.getService().put(name, price);
            subscription.setServiceMonths(month);
        }
        return subscription;
    }

    /**
     * This method use to find out discount
     *
     * @param months    number of month subscription
     * @param packPrice per month subscription price
     * @return discount amount if number of  month is grater than 3
     */
    public int discount(int months, int packPrice) {
        if (months >= 3) {
            return (months * packPrice) / 10;
        } else {
            return 0;
        }
    }

    /**
     * This method find monthly pack price
     *
     * @param subscription available subscription list
     * @param selectedPack user selected pack name
     * @return monthly pack price
     */
    private int packMonthlyPrice(Subscription subscription, String selectedPack) {
        int packPrice = 0;
        for (Map.Entry<String, Integer> entry : subscription.getPack().entrySet())
            if (entry.getKey().contains(selectedPack))
                packPrice = entry.getValue();
        return packPrice;
    }

    /**
     * This method find user selected pack
     *
     * @param pack user input pack value
     * @return name of pack
     */
    public String userSelected(String pack) {
        String selectedPack;
        if ("G".equalsIgnoreCase(pack)) {
            selectedPack = "Gold";
        } else if ("S".equalsIgnoreCase(pack)) {
            selectedPack = "Silver";
        } else {
            selectedPack = "";
        }
        return selectedPack;
    }


    /**
     * This method use to check balance of user
     *
     * @param account it is account details of user
     * @return long user account balance
     */
    public long balance(UserD2hAccount account) {
        return account.getBalance();
    }

    /**
     * This method use to add  balance
     *
     * @param account it is account details of user
     * @param amount  long value which is added to account
     * @return return user account updated balance .if amount is negative
     * than  return -1 (need to throw exception)
     */
    public long addBalance(UserD2hAccount account, long amount) {
        if (amount > 0) {
            account.setBalance(balance(account) + amount);
            return balance(account);
        }
        return -1;
    }

    /**
     * This method use to remove balance
     *
     * @param account it is account details of user
     * @param amount  long value which is remove from account
     * @return return user account updated balance if amount is negative or grater
     * than balance than return -1 (need to throw exception)
     */
    public long removeBalance(UserD2hAccount account, long amount) {
        if ((amount > 0) && amount < balance(account)) {
            account.setBalance(balance(account) - amount);
            return balance(account);
        }
        return -1;
    }

    /**
     * This method use to add and update contact details
     *
     * @param account     it is account details of user
     * @param phoneNumber user phone number
     * @param email       user email id
     */
    public UserD2hAccount updateContact(UserD2hAccount account, long phoneNumber, String email) {
        if (!validateContact(email, phoneNumber)) {
            account.setPhoneNumber(phoneNumber);
            account.setEmail(email);
            display("Email and Phone updated successfully");
        }
        return account;
    }

    /**
     * This method use to read integer value from console
     *
     * @param msg massage display on console
     * @return int value
     */
    private int inputInt(String msg) {
        int val = 0;
        display(msg);
        Scanner sc = new Scanner(System.in);
        try {
            val = sc.nextInt();
        } catch (Exception e) {
            System.out.println("please enter numerical value");
            inputInt(msg);

        }
        return val;
    }

    /**
     * This method use to read String value from console
     *
     * @param msg massage display on console
     * @return String value
     */
    private String input(String msg) {
        String val = "";
        display(msg);
        Scanner sc = new Scanner(System.in);
        try {
            val = sc.next();
        } catch (Exception e) {
            System.out.println("please enter String value");
            input(msg);

        }
        return val;
    }

    /**
     * This method use to read long value from console
     *
     * @param msg massage display on console
     * @return String value
     */
    private long inputLong(String msg) {
        long val = 0;
        display(msg);
        Scanner sc = new Scanner(System.in);
        try {
            val = sc.nextLong();
        } catch (Exception e) {
            System.out.println("please enter numerical value");
            input(msg);

        }
        return val;
    }

    /**
     * This is generic display method. it can print value on console
     *
     * @param val it is string value
     */
    private void display(String val) {
        if (null != val)
            System.out.println(val);
    }

    /**
     * This is console display method. it can display menu of d2h
     *
     * @param val it is list string value
     */
    private void display(List<String> val) {
        if (null != val && !val.isEmpty()) {
            for (int i = 0; i < val.size(); i++) {
                display(i + 1 + "." + " " + val.get(i));

            }
        }
    }

    /**
     * This is console display method. it can print channel,pack and service details.
     *
     * @param val it is  Map<k,v> k is Name and v= price
     */
    private void display(Map<String, Integer> val) {
        if (null != val && !val.isEmpty()) {
            for (Map.Entry<String, Integer> entry : val.entrySet())
                display(entry.getKey() + ":" + entry.getValue() + " Rs.");
        }
    }

    /**
     * This is console display method. it can print channel,pack and service title.
     *
     * @param map it is  Map<k,v> k is Name and v= price
     */
    private void display(String subscription, Map<String, Integer> map) {
        display("Available " + subscription + " for subscription");
        display(map);

    }

    /**
     * This method use to display subscription details
     *
     * @param subscription subscription object
     */
    private void displayAvailableSubscription(Subscription subscription) {
        display("pack", subscription.getPack());
        display("channels", subscription.getChannel());
        display("services", subscription.getService());
    }


}
