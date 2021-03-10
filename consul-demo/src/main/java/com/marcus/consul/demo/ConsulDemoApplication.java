package com.marcus.consul.demo;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

import java.util.Scanner;

/**
 * @author: MarcusJiang1306
 */
public class ConsulDemoApplication {

    public static void main(String[] args) throws NotRegisteredException {
        Consul client = Consul.builder().build();
        AgentClient agentClient = client.agentClient();
        Registration service = ImmutableRegistration.builder()
                .id("demo2")
                .name("application")
                .port(8082)
                .check(Registration.RegCheck.ttl(15L))
                .build();

        agentClient.register(service);
        agentClient.pass("demo2");
        System.out.println("Service started");
        System.out.println(new Scanner(System.in).next());

    }
}
