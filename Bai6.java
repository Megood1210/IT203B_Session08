package Bai6;

import java.util.Scanner;

interface PaymentMethod {
    void pay(double amount);
}

class CODPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán khi nhận hàng: " + amount);
    }
}

interface DiscountStrategy {
    double applyDiscount(double total);
}

interface NotificationService {
    void notifyUser();
}

interface SalesChannelFactory {
    DiscountStrategy createDiscountStrategy();

    PaymentMethod createPaymentMethod();

    NotificationService createNotificationService();
}

class WebsiteFactory implements SalesChannelFactory {
    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new WebsiteDiscount();
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new CreditCardPayment();
    }

    @Override
    public NotificationService createNotificationService() {
        return new EmailNotification();
    }
}

class WebsiteDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        double discount = total * 0.1;
        System.out.println("Áp dụng giảm giá 10%: " + discount);
        return total - discount;
    }
}

class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + amount);
    }
}

class EmailNotification implements NotificationService {
    @Override
    public void notifyUser() {
        System.out.println("Gửi email: Đơn hàng thành công");
    }
}

class MobileAppFactory implements SalesChannelFactory {
    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new FirstTimeDiscount();
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new MomoPayment();
    }

    @Override
    public NotificationService createNotificationService() {
        return new PushNotification();
    }
}

class FirstTimeDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        double discount = total * 0.15;
        System.out.println("Áp dụng giảm giá 15% (lần đầu): " + discount);
        return total - discount;
    }
}

class MomoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + amount);
    }
}

class PushNotification implements NotificationService {
    @Override
    public void notifyUser() {
        System.out.println("Gửi push notification: Đơn hàng thành công");
    }
}


class POSFactory implements SalesChannelFactory {
    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new MemberDiscount();
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new CODPayment();
    }

    @Override
    public NotificationService createNotificationService() {
        return new PrintReceipt();
    }
}

class MemberDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        double discount = total * 0.05;
        System.out.println("Áp dụng giảm giá 5% cho thành viên: " + discount);
        return total - discount;
    }
}

class PrintReceipt implements NotificationService {
    @Override
    public void notifyUser() {
        System.out.println("In hóa đơn tại cửa hàng");
    }
}

class OrderService {
    private DiscountStrategy discount;
    private PaymentMethod payment;
    private NotificationService notification;

    public OrderService(SalesChannelFactory factory) {

        discount = factory.createDiscountStrategy();
        payment = factory.createPaymentMethod();
        notification = factory.createNotificationService();
    }

    public void createOrder(double price, int quantity) {

        double total = price * quantity;

        System.out.println("Tổng tiền: " + total);

        double finalAmount = discount.applyDiscount(total);

        payment.pay(finalAmount);

        notification.notifyUser();
    }
}

public class Bai6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Chọn kênh bán hàng:");
        System.out.println("1. Website");
        System.out.println("2. Mobile App");
        System.out.println("3. POS");

        int choice = sc.nextInt();

        SalesChannelFactory factory = null;

        switch (choice) {
            case 1:
                factory = new WebsiteFactory();
                System.out.println("Bạn đã chọn kênh Website");
                break;
            case 2:
                factory = new MobileAppFactory();
                System.out.println("Bạn đã chọn kênh Mobile App");
                break;
            case 3:
                factory = new POSFactory();
                System.out.println("Bạn đã chọn kênh POS");
                break;
        }

        OrderService service = new OrderService(factory);

        System.out.print("Nhập giá sản phẩm: ");
        double price = sc.nextDouble();

        System.out.print("Nhập số lượng: ");
        int quantity = sc.nextInt();

        service.createOrder(price, quantity);
    }
}