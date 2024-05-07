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
        Long start1 = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        Long end1 = System.currentTimeMillis();
        System.out.println(end1-start1);

        Long start2 = System.currentTimeMillis();
        Integer sum2 = IntStream
                .iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .sum();
        Long end2 = System.currentTimeMillis();
        System.out.println(end2-start2);
        return sum2;
    }
}
