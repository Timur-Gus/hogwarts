package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class InfoController {


    private final String serverPort;

    public InfoController(@Value("${server.port:blank-server-port}") String serverPort) {
        this.serverPort = serverPort;
    }

    @GetMapping("/port")
    public String getServerPort() {
        return serverPort;
    }

    @GetMapping("/sum")
    public Integer getSum() {
        Integer sum = IntStream
                .iterate(1, a -> a < 1_000_000, a -> a + 1)
                .parallel()
                .sum();
        return sum;
    }
}
