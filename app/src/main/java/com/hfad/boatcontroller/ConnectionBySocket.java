package com.hfad.boatcontroller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public  class ConnectionBySocket implements Runnable {
    private String ip = "";
    private int port = 0;

    public ConnectionBySocket(String ip, int port ) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {

        InetAddress servAddr = null;
        try {
            servAddr = InetAddress.getByName(ip);
            ControlActivity.clientSocket = new Socket(servAddr, port);
            ControlActivity.dataOutputStream = new DataOutputStream(ControlActivity.clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ConnectException e){
            e.printStackTrace();
        } catch(SocketException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}