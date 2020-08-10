package com.util;
 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 
public class SocketManager {
 
    public static void main(String[] args) {
        SocketManager manager = new SocketManager();
        manager.doListen();
    }
 
    public void doListen() {
        ServerSocket server;
        try {
            server = new ServerSocket(9999);
            while (true) {
                Socket client = server.accept();
                new Thread(new SSocket(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    class SSocket implements Runnable {
        Socket client;
 
        public SSocket(Socket client) {
            this.client = client;
        }
 
        public void run() {
            DataInputStream input;
            DataOutputStream output;
            try {
                input = new DataInputStream(client.getInputStream());
                output = new DataOutputStream(client.getOutputStream());
                Thread.sleep(5000L);
                String data = input.readUTF();
                output.writeUTF("Recive1:  " + data);
                System.out.println("接口名称 access {}"+ data);
                data = input.readUTF();
                output.writeUTF("Recive2:  " + data);
                System.out.println("接收的数据 {}"+ data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {}
        }
    }
}