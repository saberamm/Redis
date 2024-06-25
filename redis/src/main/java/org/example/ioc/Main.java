package org.example.ioc;

import java.lang.reflect.Field;

public class Main {
    private static InterfaceService service = ServiceFactory.getService();

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        constructorInjection();
        fieldInjection();
        setterInjection();
    }

    public static void constructorInjection() {
        Client client = new Client(service);
        client.higuys();
    }

    public static void fieldInjection() throws NoSuchFieldException, IllegalAccessException {
        Client client = new Client();
        Client client2 = new Client();
        Field field = client.getClass().getDeclaredField("service");
        field.setAccessible(true);
        field.set(client, service);
        field.set(client2, service);
        client.higuys();
        client2.higuys();
    }

    public static void setterInjection() {
        Client client = new Client();
        client.setService(service);
        client.higuys();
    }
}
