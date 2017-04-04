package ua.dimoon.test.zookeeper.worker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @RequestMapping(method = RequestMethod.GET)
    public String work() throws Exception {
        String response = "Work done by " + WorkerApplication.workerName;
        System.out.println(response);
        return response;
    }
}
