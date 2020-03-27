package com.pluralsight.httpclient;

import lombok.Getter;
import java.util.ArrayList;

@Getter
public class RandomUserDataDTO {
    private ArrayList<UserDTO> results;
    private InfoDTO info;
}
