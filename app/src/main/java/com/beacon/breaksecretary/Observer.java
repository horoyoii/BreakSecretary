package com.beacon.breaksecretary;

import com.beacon.breaksecretary.model.User;

public interface Observer {
    public void update(User.Status_user status);
}
