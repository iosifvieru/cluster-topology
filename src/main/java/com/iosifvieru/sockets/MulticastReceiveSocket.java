package com.iosifvieru.sockets;

import com.iosifvieru.model.ClusterTopology;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;

public class MulticastReceiveSocket extends Thread {
    private final String group;
    private final Integer port;

    private MulticastSocket socket;
    private ClusterTopology topology;

    private byte[] buffer;
    private final int bufSize;

    private boolean running = true;

    public MulticastReceiveSocket(String group, int port){
        this.group = group;
        this.port = port;
        this.bufSize = 256;
        this.setRunning(true);
    }

    private void initClient() throws IOException {
        this.socket = new MulticastSocket(port);
        System.out.println("Multicast client socket initialized.");

        socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByIndex(0));

        this.buffer = new byte[bufSize];

        if(topology == null){
            this.topology = new ClusterTopology();
        }
    }

    public void run() {
        try {
            this.initClient();

            DatagramPacket packet = new DatagramPacket(buffer, bufSize);

            while(running){
                socket.receive(packet);

                InetAddress addr = packet.getAddress();
                topology.update(addr.getHostAddress(), packet.getPort());

                System.out.println("Received message from: " + addr.getHostAddress() + ":" + packet.getPort() + " at: " + LocalDateTime.now());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public void setTopology(ClusterTopology topology){
        this.topology = topology;
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }
}