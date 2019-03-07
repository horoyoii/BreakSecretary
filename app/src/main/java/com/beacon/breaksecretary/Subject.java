package com.beacon.breaksecretary;

import com.beacon.breaksecretary.model.User;

public interface Subject {
    public void registerObserver(Observer ob);
    public void deleteObserver(Observer ob);
    public void notifyTheStatus(User.Status_user status);
}
