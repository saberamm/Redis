package org.example.ioc;

public class ServiceFactory {
    public static InterfaceService getService(){
        return new Service();
    }
}
