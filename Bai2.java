import java.util.Scanner;

interface TemperatureSensor {
    double getTemperatureCelsius();
}

class OldThermometer {
    public int getTemperatureFahrenheit() {
        return 78;
    }
}

class ThermometerAdapter implements TemperatureSensor {
    private OldThermometer oldThermometer;

    public ThermometerAdapter(OldThermometer oldThermometer) {
        this.oldThermometer = oldThermometer;
    }

    @Override
    public double getTemperatureCelsius() {
        int f = oldThermometer.getTemperatureFahrenheit();
        return (f - 32) * 5.0 / 9.0;
    }
}

class Light1 {
    public void off() {
        System.out.println("FACADE: Tắt đèn");
    }
}

class Fan1 {
    public void off() {
        System.out.println("FACADE: Tắt quạt");
    }

    public void lowSpeed() {
        System.out.println("FACADE: Quạt chạy tốc độ thấp");
    }
}

class AirConditioner1 {
    public void off() {
        System.out.println("FACADE: Tắt điều hòa");
    }

    public void setTemperature(int temp) {
        System.out.println("FACADE: Điều hòa set " + temp + "°C");
    }
}

class SmartHomeFacade {
    private Light1 light = new Light1();
    private Fan1 fan = new Fan1();
    private AirConditioner1 ac = new AirConditioner1();
    private TemperatureSensor sensor;

    public SmartHomeFacade(TemperatureSensor sensor) {
        this.sensor = sensor;
    }

    public void leaveHome() {
        light.off();
        fan.off();
        ac.off();
    }

    public void sleepMode() {
        light.off();
        ac.setTemperature(28);
        fan.lowSpeed();
    }

    public void getCurrentTemperature() {
        double tempC = sensor.getTemperatureCelsius();
        int f = 78;
        System.out.printf("Nhiệt độ hiện tại: %.1f°C (chuyển đổi từ %d°F)\n", tempC, f);
    }
}

public class Bai2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        OldThermometer oldSensor = new OldThermometer();
        TemperatureSensor adapter = new ThermometerAdapter(oldSensor);

        SmartHomeFacade home = new SmartHomeFacade(adapter);

        while (true) {
            System.out.println("1. Xem nhiệt độ");
            System.out.println("2. Chế độ rời nhà");
            System.out.println("3. Chế độ ngủ");
            System.out.println("4. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    home.getCurrentTemperature();
                    break;
                case 2:
                    home.leaveHome();
                    break;
                case 3:
                    home.sleepMode();
                    break;
                case 4:
                    return;
            }
        }
    }
}