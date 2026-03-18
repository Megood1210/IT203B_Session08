package Bai4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Observer {
    void update(int temperature);
}

interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}
class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("\nCảm biến: Nhiệt độ = " + temperature);
        notifyObservers();
    }
}

class Fan implements Observer {
    @Override
    public void update(int temperature) {

        if (temperature < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        }
        else if (temperature <= 25) {
            System.out.println("Quạt: Nhiệt độ vừa, chạy tốc độ TRUNG BÌNH");
        }
        else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }
}

class Humidifier implements Observer {
    @Override
    public void update(int temperature) {
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + temperature);
    }
}
public class Bai4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TemperatureSensor sensor = new TemperatureSensor();

        Fan fan = new Fan();
        Humidifier humidifier = new Humidifier();

        boolean fanRegistered = false;
        boolean humidifierRegistered = false;

        int choice;

        do {
            System.out.println("===== TEMPERATURE SYSTEM =====");
            System.out.println("1. Đăng ký Quạt");
            System.out.println("2. Đăng ký Máy tạo ẩm");
            System.out.println("3. Set nhiệt độ");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    if (!fanRegistered) {
                        sensor.attach(fan);
                        fanRegistered = true;
                        System.out.println("Quạt: Đã đăng ký nhận thông báo");
                    } else {
                        System.out.println("Quạt đã đăng ký trước đó.");
                    }
                    break;
                case 2:
                    if (!humidifierRegistered) {
                        sensor.attach(humidifier);
                        humidifierRegistered = true;
                        System.out.println("Máy tạo ẩm: Đã đăng ký");
                    } else {
                        System.out.println("Máy tạo ẩm đã đăng ký trước đó.");
                    }
                    break;
                case 3:
                    System.out.print("Nhập nhiệt độ: ");
                    int temp = sc.nextInt();
                    sensor.setTemperature(temp);
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 4);

        sc.close();
    }
}