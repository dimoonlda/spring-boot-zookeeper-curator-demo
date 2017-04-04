package ua.dimoon.test.zookeeper.manager;

import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ServiceProvider serviceProvider;

    @RequestMapping("/delegate")
    public String delegate() throws Exception {
        ServiceInstance instance = serviceProvider.getInstance();
        String address = instance.buildUriSpec();
        String response = (address + "/worker");
        System.out.println("ManagerController: " + response);
        return response;
    }
}
