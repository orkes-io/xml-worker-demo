package io.orkes.samples.workers;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JacksonXmlRootElement(localName = "notification")
public class Notification {
    private String template;
    private String email;
    private String text;
}
