package com.pluralsight.httpclient;

import lombok.Getter;
import java.util.ArrayList;

@Getter
public class RandomUserDTO {
    private ArrayList<UserDTO> results;
    private InfoDTO info;
}
