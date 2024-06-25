package org.example.ioc;

public class Client {
   private InterfaceService service;

    public Client(InterfaceService service) {
        this.service = service;
    }

    public Client() {
    }

    public void higuys(){
        service.print();
    }

    public void setService(InterfaceService service) {
        this.service = service;
    }
}
