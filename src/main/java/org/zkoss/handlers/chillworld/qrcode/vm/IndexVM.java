package org.zkoss.handlers.chillworld.qrcode.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class IndexVM {

    private String link = "This is just data inserted in a label.";
    private String secondLink = "http://www.google.be";
    private String thirthLink = "You can add the datahandler manually on other ZK components";

    @Command
    @NotifyChange("link")
    public void changeLink() {
        link = "The data is fully dynamic.";
    }

    @Command
    @NotifyChange("secondLink")
    public void changeSecondLink() {
        secondLink = "http://www.mail.yahoo.com";
    }
    
    @Command
    @NotifyChange("thirthLink")
    public void changeThirthLink() {
        thirthLink = "Just add the property value as the datahandler parameter.";
    }

    public String getLink() {
        return link;
    }

    public String getSecondLink() {
        return secondLink;
    }

    public String getThirthLink() {
        return thirthLink;
    }
}
