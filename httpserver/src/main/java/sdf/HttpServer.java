package sdf;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port; 
    private String[] docRoot; 
    private Socket socket; 
    private ServerSocket serverSocket; 
    private boolean condition_check = true;  

    public HttpServer(int port, String[]docRoot){
        this.port = port;
        this.docRoot = docRoot;
    }

    public void start_server() throws IOError, IOException{
        
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        serverSocket = new ServerSocket(port);
        System.out.println("Server listening at port " + port + "...");
        
        while (true){ 
            socket = serverSocket.accept();
            HttpClientConnection worker = new HttpClientConnection(socket,docRoot);
            threadPool.submit(worker);
        

        } 

        
    }

    
}
