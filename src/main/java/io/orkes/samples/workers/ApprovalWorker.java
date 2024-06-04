package io.orkes.samples.workers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApprovalWorker implements Worker {

    private static final Logger log = LogManager.getLogger(ApprovalWorker.class);
    private static final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String getTaskDefName() {
        return "approval_task";
    }

    @Override
    public TaskResult execute(Task task) {
        String data = (String) task.getInputData().get("data");
        log.info("Data: {} ", data);

        User user = parseXml(data);
        String result = "rejected";
        String notification = null;
        if (user.getAge() >= 18) {
            // Only if the user is 18 or above then we can start
            // this marketing campaign for this beer company.
            result = "approved";
            notification = toXml(Notification.builder()
                    .template("accepted")
                    .email(user.getEmail())
                    .text("Welcome to the Duff experience!")
                    .build());
        }

        TaskResult taskResult = new TaskResult(task);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.getOutputData().put("result", result);
        taskResult.getOutputData().put("notification", notification);

        return taskResult;
    }

    @SneakyThrows
    private static String toXml(Notification notification) {
        return xmlMapper.writeValueAsString(notification);
    }

    @SneakyThrows
    private static User parseXml(String xml) {
        return xmlMapper.readValue(xml, User.class);
    }
}
