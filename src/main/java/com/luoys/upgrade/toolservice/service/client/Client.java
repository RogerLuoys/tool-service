package com.luoys.upgrade.toolservice.service.client;

public interface Client <T> {
    String execute(T dto);
}
