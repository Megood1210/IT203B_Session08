package Bai5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

interface Command {
    void execute();
}

class Light {
    private boolean isOn = true;

    public void off() {
        isOn = false;
        System.out.println("Đèn: Tắt");
    }

    public boolean isOn() {
        return isOn;
    }
}

class Fan implements Observer {
    private String speed = "Tắt";

    public void setLow() {
        speed = "Chạy tốc độ thấp";
        System.out.println("Quạt: " + speed);
    }

    public void setHigh() {
        speed = "Chạy tốc độ mạnh";
        System.out.println("Quạt: " + speed);
    }

    @Override
    public void update(int temperature) {

        if (temperature > 30) {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
            speed = "Chạy tốc độ mạnh";
        }
    }

    public String getSpeed() {
        return speed;
    }
}

class AirConditioner implements Observer {
    private int temperature = 25;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temperature);
    }

    @Override
    public void update(int temp) {

        if (temp > 30) {
            System.out.println("Điều hòa: Nhiệt độ = " + temperature + " (vẫn giữ)");
        }
    }

    public int getTemperature() {
        return temperature;
    }
}

interface Observer {
    void update(int temperature);
}

interface Subject {
    void attach(Observer o);
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
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("\nCảm biến: Nhiệt độ = " + temperature);
        notifyObservers();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}

class ACSetTempCommand implements Command {
    private AirConditioner ac;
    private int temp;

    public ACSetTempCommand(AirConditioner ac, int temp) {
        this.ac = ac;
        this.temp = temp;
    }

    @Override
    public void execute() {
        ac.setTemperature(temp);
    }
}

class FanLowCommand implements Command {
    private Fan fan;

    public FanLowCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.setLow();
    }
}

class SleepModeCommand implements Command {
    private List<Command> commands = new ArrayList<>();

    public SleepModeCommand(Command... cmds) {
        commands.addAll(Arrays.asList(cmds));
    }

    @Override
    public void execute() {

        System.out.print("\nSleepMode:");

        for (Command c : commands) {
            c.execute();
        }
    }
}

public class Bai5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();
        TemperatureSensor sensor = new TemperatureSensor();

        sensor.attach(fan);
        sensor.attach(ac);

        Command sleepMode = new SleepModeCommand(new LightOffCommand(light),new ACSetTempCommand(ac, 28),new FanLowCommand(fan));

        int choice;

        do {
            System.out.println("===== SMART HOME =====");
            System.out.println("1. Kích hoạt chế độ ngủ");
            System.out.println("2. Thay đổi nhiệt độ");
            System.out.println("3. Xem trạng thái thiết bị");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sleepMode.execute();
                    break;
                case 2:
                    System.out.print("Nhập nhiệt độ: ");
                    int temp = sc.nextInt();
                    sensor.setTemperature(temp);
                    break;
                case 3:
                    System.out.println("Đèn: " + (light.isOn() ? "Bật" : "Tắt"));
                    System.out.println("Điều hòa: Nhiệt độ = " + ac.getTemperature());
                    System.out.println("Quạt: " + fan.getSpeed());
                    break;
            }
        } while (choice != 4);

        sc.close();
    }
}