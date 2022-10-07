package com.paulpinter.ase.task.server;

import org.springframework.beans.factory.annotation.Value;

public class LinkToNextTaskService {

    @Value("${server.port}")
    private String port;
    private final static String HOST = "127.0.0.1";

    public String linkToNextTask(String scenario, String matNum, int stage, int testcase){
        // https://reset.inso.tuwien.ac.at/ase/<scenarioPrefix>/assignment/<yourMatrikelnr>/stage/1/testcase/3?token=eyJhbGciOiJ
        return "http://" + HOST + ":" + port + "/" + scenario + "/" + matNum + "/stage" + scenario;
    }
}
