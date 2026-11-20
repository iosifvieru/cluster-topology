package com.iosifvieru.sockets;

import com.iosifvieru.Constants;
import com.iosifvieru.model.ClusterTopology;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSendSocket extends Thread {
    private final String group;
    private final Integer port;

    private DatagramSocket socket;
    private DatagramPacket packet;

    private ClusterTopology topology;
    private int index = 0;

    private boolean running = true;

    public MulticastSendSocket(String group, int port){
        this.group = group;
        this.port = port;
        this.setRunning(true);
    }

    private void initSend() throws IOException {
        this.socket = new DatagramSocket();

        String message = "Healthy!";
        this.packet = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(group), port);

        System.out.println("Server socket and packet initialized!");

        if(topology == null){
            this.topology = new ClusterTopology();
        }
    }

    public void run() {
        try {
            initSend();

            while(running) {
                socket.send(packet);

                if(index % Constants.NO_HEARTBEATS_UNTIL_CLEANUP == 0) {
                    this.topology.cleanup();
                    this.topology.print();

                    this.index = 0;
                }

                this.index += 1;

                sleep(Constants.MILLIS_HEARTBEAT_SEND);
            }

        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            if (this.socket != null){
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
