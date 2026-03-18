package Bai3;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

interface Command {
    void execute();
    void undo();
}

class Light {
    public void on() {
        System.out.println("Đèn: Bật");
    }

    public void off() {
        System.out.println("Đèn: Tắt");
    }
}

class AirConditioner {
    private int temperature = 25;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    public int getTemperature() {
        return temperature;
    }
}

class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
        System.out.println("Undo: Đèn Bật (quay lại trạng thái trước)");
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }

    public void undo() {
        light.on();
        System.out.println("Undo: Đèn Bật (quay lại trạng thái trước)");
    }
}


class ACSetTemperatureCommand implements Command {
    private AirConditioner ac;
    private int newTemp;
    private int oldTemp;

    public ACSetTemperatureCommand(AirConditioner ac, int temp) {
        this.ac = ac;
        this.newTemp = temp;
    }

    public void execute() {
        oldTemp = ac.getTemperature();
        ac.setTemperature(newTemp);
    }

    public void undo() {
        ac.setTemperature(oldTemp);
        System.out.println("Undo: Điều hòa: Nhiệt độ = " + oldTemp + " (nhiệt độ cũ)");
    }
}

class RemoteControl {
    private Map<Integer, Command> buttons = new HashMap<>();
    private Stack<Command> history = new Stack<>();

    public void setCommand(int button, Command command) {
        buttons.put(button, command);
    }

    public void pressButton(int button) {
        Command command = buttons.get(button);

        if (command != null) {
            command.execute();
            history.push(command);
        } else {
            System.out.println("Chưa gán command cho nút này.");
        }
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
        } else {
            System.out.println("Không có lệnh để undo.");
        }
    }
}

public class Bai3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RemoteControl remote = new RemoteControl();
        Light light = new Light();
        AirConditioner ac = new AirConditioner();

        while (true) {
            System.out.println("\n===== REMOTE CONTROL =====");
            System.out.println("1. Gán nút 1: Bật đèn");
            System.out.println("2. Gán nút 2: Tắt đèn");
            System.out.println("3. Gán nút 3: Set điều hòa 26°C");
            System.out.println("4. Nhấn nút");
            System.out.println("5. Undo");
            System.out.println("6. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    remote.setCommand(1, new LightOnCommand(light));
                    System.out.println("Đã gán LightOnCommand cho nút 1");
                    break;
                case 2:
                    remote.setCommand(2, new LightOffCommand(light));
                    System.out.println("Đã gán LightOffCommand cho nút 2");
                    break;
                case 3:
                    remote.setCommand(3, new ACSetTemperatureCommand(ac, 26));
                    System.out.println("Đã gán ACSetTempCommand(26) cho nút 3");
                    break;
                case 4:
                    System.out.print("Nhập số nút: ");
                    int btn = sc.nextInt();
                    remote.pressButton(btn);
                    break;
                case 5:
                    remote.undo();
                    break;
                case 6:
                    return;
            }
        }
    }
}