package io.orkes.samples;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.OrkesClients;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.automator.TaskRunnerConfigurer;
import io.orkes.samples.workers.ApprovalWorker;

import java.util.List;


public class Main {
    private static final String ROOT_URI = "http://localhost:8080/api";
    private static final String KEY = "_CHANGE_ME_";
    private static final String SECRET = "_CHANGE_ME_";

    public static void main(String[] args) {
        var taskClient = getTaskClient();
        var runnerConfigurer = new TaskRunnerConfigurer
                .Builder(taskClient, List.of(new ApprovalWorker()))
                .withThreadCount(10)
                .build();
        runnerConfigurer.init();
    }

    private static TaskClient getTaskClient() {
        return new OrkesClients(new ApiClient(ROOT_URI, KEY, SECRET)).getTaskClient();
    }
}
