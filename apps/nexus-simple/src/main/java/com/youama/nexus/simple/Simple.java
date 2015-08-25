package com.youama.nexus.simple;

import com.youama.nexus.core.system.NexusCoreUtil;
import com.youama.nexus.core.system.ServiceUtil;
import com.youama.nexus.scheduler.entity.task.TaskEntity;
import com.youama.nexus.scheduler.entity.task.TaskService;

import java.util.List;

/**
 * @author David Belicza
 * @since 2015.08.22.
 */
public class Simple {

    public void applicationRunningSimulation() {
        Thread one = new Thread() {
            public void run() {
                NexusCoreUtil.initServices("nexus-app-simple");

                for (int i = 0; i < 10; i++) {
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    List<String> drivers = ServiceUtil.getInstalledDrivers();
                    for (String driver : drivers) {
                        ServiceUtil.switchDriver(driver);
                        TaskService service = (TaskService) ServiceUtil.getService(TaskService.class);
                        TaskEntity taskEntity = new TaskEntity();
                        service.save(taskEntity);
                        System.out.println("one: " + i);
                    }
                }

                NexusCoreUtil.removeServices();
            }
        };

        Thread two = new Thread() {
            public void run() {
                NexusCoreUtil.initServices("nexus-app-simple");

                for (int i = 0; i < 5; i++) {
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    List<String> drivers = ServiceUtil.getInstalledDrivers();
                    for (String driver : drivers) {
                        ServiceUtil.switchDriver(driver);
                        TaskService service = (TaskService) ServiceUtil.getService(TaskService.class);
                        TaskEntity taskEntity = new TaskEntity();
                        service.save(taskEntity);
                        System.out.println("two: " + i);
                    }
                }

                NexusCoreUtil.removeServices();
            }
        };

        try {
            two.start();
            one.start();
            two.join();
            one.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}