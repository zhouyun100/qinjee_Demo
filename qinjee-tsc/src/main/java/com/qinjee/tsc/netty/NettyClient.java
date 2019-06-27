package com.qinjee.tsc.netty;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NettyClient {

	
    public static void main(String[] args) throws Exception {
    	String host = "localhost";
    	int port = 8070;
    	System.out.println("netty port:" + port +" start。。。");
    	try {
            Socket socket=new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.write("This's test request!");
            printWriter.flush();
            socket.shutdownOutput();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	System.out.println("netty client finished!");
    }
    
}
