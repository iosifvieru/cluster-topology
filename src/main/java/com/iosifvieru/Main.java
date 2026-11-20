package com.iosifvieru;

import com.iosifvieru.benchmark.CPUBenchmark;
import com.iosifvieru.model.ClusterTopology;
import com.iosifvieru.sockets.MulticastReceiveSocket;
import com.iosifvieru.sockets.MulticastSendSocket;

public class Main {
    public static void main(String[] args) {
        ClusterTopology topology = new ClusterTopology();

        MulticastReceiveSocket receiveSocket = new MulticastReceiveSocket(Constants.MULTICAST_GROUP, Constants.MULTICAST_PORT);
        receiveSocket.setTopology(topology);

        MulticastSendSocket sendSocket = new MulticastSendSocket(Constants.MULTICAST_GROUP, Constants.MULTICAST_PORT);
        sendSocket.setTopology(topology);

        receiveSocket.start();
        sendSocket.start();

        var benchmark = new CPUBenchmark();
        benchmark.run();
    }
}
