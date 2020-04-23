package com.techverito.d2h;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class D2hDashboardTest {

    private UserD2hAccount account;
    private D2hDashboard d2hDashboard;

    @Before
    public void setConstant() {
        this.account = new UserD2hAccount();

        this.d2hDashboard = new D2hDashboard();
    }

    @After
    public void resetConstant() {
        this.account =null;

        this.d2hDashboard = null;
    }

    @Test
    public void balanceTest() {
        account.setBalance(100);
        Assert.assertEquals(100, d2hDashboard.balance(account));
    }

    @Test
    public void validAddBalance() {
        d2hDashboard.addBalance(account, 100);
        Assert.assertEquals(100, d2hDashboard.balance(account));
    }

    @Test
    public void inValidAddNegativeBalance() {
        long output = d2hDashboard.addBalance(account, -100);
        Assert.assertEquals(-1, output);
    }

    @Test
    public void validRemoveBalance() {
        account.setBalance(100);
        long output = d2hDashboard.removeBalance(account, 50);
        Assert.assertEquals(50, output);
    }

    @Test
    public void inValidRemoveBalance() {
        account.setBalance(100);
        long output = d2hDashboard.removeBalance(account, -50);
        Assert.assertEquals(-1, output);
    }

    @Test
    public void validUpdateContact() {
        UserD2hAccount output = d2hDashboard.updateContact(account, 1234567890L, "aksh@xyz.com");
        Assert.assertEquals(1234567890L, output.getPhoneNumber());
        Assert.assertEquals("aksh@xyz.com", output.getEmail());

    }

    @Test
    public void inValidUpdatePhoneNumber() {
        UserD2hAccount output = d2hDashboard.updateContact(account, 1234567, "aksh@xyz.com");
        Assert.assertNotEquals(1234567, output.getPhoneNumber());
    }

    @Test
    public void inValidUpdateEmailId() {
        UserD2hAccount output = d2hDashboard.updateContact(account, 1234567890, "akshxyz");
        Assert.assertNotEquals("akshxyz", output.getEmail());
    }

    @Test
    public void inValidUpdateEmailIdAndPhoneNumber() {
        UserD2hAccount output = d2hDashboard.updateContact(account, 1234567, "akshxyz");
        Assert.assertNotEquals("akshxyz", output.getEmail());
        Assert.assertNotEquals(1234567, output.getPhoneNumber());
    }

    @Test
    public void validRecharge() {
        long val = account.getBalance() + 10;
        d2hDashboard.recharge(account, 10);
        Assert.assertEquals(val, account.getBalance());
    }

    @Test
    public void inValidRecharge() {
        long val = account.getBalance() - 10;
        d2hDashboard.recharge(account, -10);
        Assert.assertNotEquals(val, account.getBalance());
    }

    @Test
    public void validatePackCode() {
        Assert.assertFalse(d2hDashboard.validatePackCode("s"));
        Assert.assertFalse(d2hDashboard.validatePackCode("S"));
        Assert.assertFalse(d2hDashboard.validatePackCode("g"));
        Assert.assertFalse(d2hDashboard.validatePackCode("G"));
    }

    @Test
    public void inValidatePackCode() {
        Assert.assertTrue(d2hDashboard.validatePackCode(""));
        Assert.assertTrue(d2hDashboard.validatePackCode("y"));
        Assert.assertTrue(d2hDashboard.validatePackCode("bb"));
        Assert.assertTrue(d2hDashboard.validatePackCode("12"));
    }

    @Test
    public void validatePackMonth() {
        Assert.assertFalse(d2hDashboard.validatePackMonth(1));
        Assert.assertFalse(d2hDashboard.validatePackMonth(12));
        Assert.assertFalse(d2hDashboard.validatePackMonth(36));
    }

    @Test
    public void inValidatePackMonth() {
        Assert.assertTrue(d2hDashboard.validatePackMonth(0));
        Assert.assertTrue(d2hDashboard.validatePackMonth(-1));
    }

    @Test
    public void validUserPackDisplaySubscriptionWithoutDiscount() {
        d2hDashboard.addBalance(account, 1000);
        d2hDashboard.userPackDisplaySubscription(ConstantValue.SUBSCRIPTION(), account, "S", 2);
        Assert.assertEquals(900, account.getBalance());
        Assert.assertEquals(2, account.getSubscription().getPackMonths());
        Assert.assertTrue(account.getSubscription().getPack().keySet().contains("Silver"));
        Assert.assertEquals(50, account.getSubscription().getPack().get("Silver").longValue());

    }

    @Test
    public void validUserPackDisplaySubscriptionWithDiscount() {
        d2hDashboard.addBalance(account, 1000);
        d2hDashboard.userPackDisplaySubscription(ConstantValue.SUBSCRIPTION(), account, "G", 3);
        Assert.assertEquals(730, account.getBalance());
        Assert.assertEquals(3, account.getSubscription().getPackMonths());
        Assert.assertTrue(account.getSubscription().getPack().keySet().contains("Gold"));
        Assert.assertEquals(100, account.getSubscription().getPack().get("Gold").longValue());

    }

    @Test
    public void inValidUserPackDisplaySubscriptionLowerBalance() {
        d2hDashboard.addBalance(account, 100);
        d2hDashboard.userPackDisplaySubscription(ConstantValue.SUBSCRIPTION(), account, "G", 3);
        Assert.assertEquals(100, account.getBalance());
        Assert.assertEquals(0, account.getSubscription().getPackMonths());
        Assert.assertFalse(account.getSubscription().getPack().keySet().contains("Gold"));

    }

    @Test
    public void validSubscriptionName() {
        Assert.assertTrue(d2hDashboard.subscriptionName(ConstantValue.SUBSCRIPTION().getPack().keySet()).contains("Silver"));
        Assert.assertTrue(d2hDashboard.subscriptionName(ConstantValue.SUBSCRIPTION().getChannel().keySet()).contains("Zee"));
    }

    @Test
    public void validInputWithDiscount() {
        int val = d2hDashboard.discount(3, 100);
        Assert.assertEquals(30, val);
    }

    @Test
    public void validInputWithoutDiscount() {
        int val = d2hDashboard.discount(2, 100);
        Assert.assertEquals(0, val);
    }

    @Test
    public void validUserChannelSubscriptionDisplay() {
        d2hDashboard.addBalance(account, 100);
        UserD2hAccount userD2hAccount = d2hDashboard.userChannelSubscriptionDisplay(ConstantValue.SUBSCRIPTION(), account, "Sony,Zee");
        Assert.assertEquals(75, account.getBalance());
        Assert.assertEquals(1, account.getSubscription().getChannelMonths());
        Assert.assertTrue(account.getSubscription().getChannel().keySet().contains("Sony"));
        Assert.assertEquals(15, account.getSubscription().getChannel().get("Sony").longValue());
        Assert.assertTrue(account.getSubscription().getChannel().keySet().contains("Zee"));
        Assert.assertEquals(10, account.getSubscription().getChannel().get("Zee").longValue());
    }

    @Test
    public void inValidLowBalanceUserChannelSubscriptionDisplay() {
        d2hDashboard.addBalance(account, 10);
        UserD2hAccount userD2hAccount = d2hDashboard.userChannelSubscriptionDisplay(ConstantValue.SUBSCRIPTION(), account, "Sony,Zee");
        Assert.assertEquals(10, account.getBalance());
        Assert.assertEquals(0, account.getSubscription().getChannelMonths());
        Assert.assertFalse(account.getSubscription().getChannel().keySet().contains("Sony"));
        Assert.assertFalse(account.getSubscription().getChannel().keySet().contains("Zee"));
    }

    @Test
    public void inValidChannelUserChannelSubscriptionDisplay() {
        d2hDashboard.addBalance(account, 10);
        UserD2hAccount userD2hAccount = d2hDashboard.userChannelSubscriptionDisplay(ConstantValue.SUBSCRIPTION(), account, "BBC");
        Assert.assertEquals(10, account.getBalance());
        Assert.assertEquals(0, account.getSubscription().getChannelMonths());
        Assert.assertFalse(account.getSubscription().getChannel().keySet().contains("BBC"));
    }

    @Test
    public void validUserServiceSubscriptionDisplay() {
        d2hDashboard.addBalance(account, 300);
        UserD2hAccount userD2hAccount = d2hDashboard.userServiceSubscriptionDisplay(ConstantValue.SUBSCRIPTION(), account, "LearnCooking");
        Assert.assertEquals(200, account.getBalance());
        Assert.assertEquals(1, account.getSubscription().getServiceMonths());
        Assert.assertTrue(account.getSubscription().getService().keySet().contains("LearnCooking"));
        Assert.assertEquals(100, account.getSubscription().getService().get("LearnCooking").longValue());
    }
    @Test
    public void inValidLowBalanceUserServiceSubscriptionDisplay() {
        d2hDashboard.addBalance(account, 50);
        UserD2hAccount userD2hAccount = d2hDashboard.userServiceSubscriptionDisplay(ConstantValue.SUBSCRIPTION(), account, "LearnCooking");
        Assert.assertEquals(50, account.getBalance());
        Assert.assertEquals(0, account.getSubscription().getServiceMonths());
        Assert.assertFalse(account.getSubscription().getService().keySet().contains("LearnCooking"));

    }
    @Test
    public void userServiceSubscription() {
        d2hDashboard.addBalance(account, 300);
        UserD2hAccount userD2hAccount = d2hDashboard.userServiceSubscription(account,ConstantValue.SUBSCRIPTION(), "LearnCooking");
        Assert.assertEquals(200, account.getBalance());
        Assert.assertEquals(1, account.getSubscription().getServiceMonths());
        Assert.assertTrue(account.getSubscription().getService().keySet().contains("LearnCooking"));
        Assert.assertEquals(100, account.getSubscription().getService().get("LearnCooking").longValue());
    }

    @Test
    public void userChannelSubscription() {
        d2hDashboard.addBalance(account, 100);
        UserD2hAccount userD2hAccount = d2hDashboard.userChannelSubscription(account,ConstantValue.SUBSCRIPTION(),"Sony");
        Assert.assertEquals(85, account.getBalance());
        Assert.assertEquals(1, account.getSubscription().getChannelMonths());
        Assert.assertTrue(account.getSubscription().getChannel().keySet().contains("Sony"));
        Assert.assertEquals(15, account.getSubscription().getChannel().get("Sony").longValue());
    }

    @Test
    public void userPackSubscription() {
        d2hDashboard.addBalance(account, 1000);
        d2hDashboard.userPackSubscription(account,ConstantValue.SUBSCRIPTION(), "G", 3);
        Assert.assertEquals(730, account.getBalance());
        Assert.assertEquals(3, account.getSubscription().getPackMonths());
        Assert.assertTrue(account.getSubscription().getPack().keySet().contains("Gold"));
        Assert.assertEquals(100, account.getSubscription().getPack().get("Gold").longValue());
    }










}
