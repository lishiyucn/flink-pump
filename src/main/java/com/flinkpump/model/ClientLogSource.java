package com.flinkpump.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class ClientLogSource {

    private int id;
    private int price;
    private long timestamp;
    private String date;
    private String page;

}
