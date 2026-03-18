import java.util.Scanner;

class HardwareConnection {
    private static HardwareConnection instance;
    private static boolean connected = false;

    private HardwareConnection() {}

    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
        }
        return instance;
    }

    public void connect() {
        if (!connected) {
            System.out.println("HardwareConnection: Đã kết nối phần cứng. (Chỉ hiện 1 lần duy nhất)");
            connected = true;
        }
    }
}

interface Device {
    void turnOn();
    void turnOff();
}

class Light implements Device {
    public void turnOn() {
        System.out.println("Đèn: Bật sáng.");
    }

    public void turnOff() {
        System.out.println("Đèn: Tắt.");
    }
}
class Fan implements Device {
    public void turnOn() {
        System.out.println("Quạt: Bật.");
    }

    public void turnOff() {
        System.out.println("Quạt: Tắt.");
    }
}

class AirConditioner implements Device {
    public void turnOn() {
        System.out.println("Điều hòa: Bật.");
    }

    public void turnOff() {
        System.out.println("Điều hòa: Tắt.");
    }
}

abstract class DeviceFactory {
    abstract Device createDevice();
}

class LightFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Light();
    }
}

class FanFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Fan();
    }
}

class AirConditionerFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new AirConditioner();
    }
}

public class Bai1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Device currentDevice = null;

        while (true) {
            System.out.println("\n1. Kết nối phần cứng");
            System.out.println("2. Tạo thiết bị mới");
            System.out.println("3. Bật thiết bị");
            System.out.println("4. Tạo thêm thiết bị");
            System.out.println("5. Kiểm tra Singleton");
            System.out.println("6. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    HardwareConnection.getInstance().connect();
                    break;
                case 2:
                    System.out.println("Chọn loại: 1. Đèn  2. Quạt  3. Điều hòa");
                    int type = sc.nextInt();

                    DeviceFactory factory = null;

                    if (type == 1)
                        factory = new LightFactory();
                    else if (type == 2)
                        factory = new FanFactory();
                    else if (type == 3)
                        factory = new AirConditionerFactory();

                    if (factory != null)
                        currentDevice = factory.createDevice();

                    break;
                case 3:
                    if (currentDevice != null)
                        currentDevice.turnOn();
                    else
                        System.out.println("Chưa có thiết bị.");
                    break;
                case 4:
                    System.out.println("Chọn loại: 1. Đèn  2. Quạt  3. Điều hòa");
                    int type2 = sc.nextInt();

                    DeviceFactory factory2 = null;

                    if (type2 == 1)
                        factory2 = new LightFactory();
                    else if (type2 == 2)
                        factory2 = new FanFactory();
                    else if (type2 == 3)
                        factory2 = new AirConditionerFactory();

                    if (factory2 != null)
                        factory2.createDevice();

                    break;
                    case 5:
                    HardwareConnection.getInstance().connect();
                    break;
                case 6:
                    return;
            }
        }
    }
}