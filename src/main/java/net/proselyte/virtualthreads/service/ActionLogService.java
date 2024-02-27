package net.proselyte.virtualthreads.service;

public class ActionLogService {

    public boolean saveEmailSentAction(String email) {
        System.out.println("Email sent action saved");
        return true;
    }
}
