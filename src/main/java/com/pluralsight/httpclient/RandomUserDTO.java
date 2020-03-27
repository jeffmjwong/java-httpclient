package com.pluralsight.httpclient;

import java.util.ArrayList;

public class RandomUserDTO {
    private ArrayList<UserDTO> results;
    private InfoDTO info;

    public ArrayList<UserDTO> getResults() {
        return results;
    }
    public InfoDTO getInfo() {
        return info;
    }
}
