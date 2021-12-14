package sdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class HttpClientConnection implements Runnable{

    private final Socket socket; 
    private String [] docRoot; 

    public HttpClientConnection(Socket socket, String[] docRoot){
        this.socket = socket; 
    }

    @Override
    public void run() {
        InputStreamReader is = null; 
        BufferedReader br = null;

        OutputStream os; 
        HttpWriter h_writer = null; 
        //BufferedWriter bw = null;
        
        byte[] buffer = new byte[1024];
        String cmdfrombroswer;
        String method; 
        String resource; 
        String response; 
        boolean fileExist = false;
        String resource_dir;  

        try {
            is = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(is); 
            cmdfrombroswer = br.readLine(); 
            
            h_writer = new HttpWriter(socket.getOutputStream());
            //bw = new BufferedWriter(osw); 

            method = cmdfrombroswer.split(" ")[0]; 
            resource = cmdfrombroswer.split(" ")[1]; 

            //Command received from client: [GET, /index.html, HTTP/1.1]

            //Action 1: Not a GET Method
            if (!method.equals("GET")){
                response = "HTTP/1.1 405 Method Not Allowed \r\n\r\n " + method + " not supported \r\n.";
                
                try {
                    h_writer.writeString(response);
                    h_writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    socket.close();
                }
            } 

            //Action 2: check if resource exists
            if (resource.equals("/")){
                resource = "/index.html"; 

                for (String x: docRoot){
                    File file = new File(x+resource); 
                    if(file.exists()){
                        fileExist = true;
                        resource_dir = x+resource;

                        if (resource_dir.contains(".png")){
                            //Action 4
                            response = "HTTP/1.1 200 OK \r\n Content Type: image/png/ \r\n\r\n ";
                            try {
                                h_writer.writeString(response);
                                os = new FileOutputStream(resource_dir);
                                h_writer.writeBytes(buffer);
                                h_writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                socket.close();
                            }
                        } else {
                            //Action 3
                            response = "HTTP/1.1 200 OK\r\n \r\n ";
                            try {
                                h_writer.writeString(response);
                                os = new FileOutputStream(resource_dir);
                                h_writer.writeBytes(buffer);
                                h_writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                socket.close();
                            }
                        }

                
                    }
                } 
            } else { 
                for (String x: docRoot){
                    File file = new File(x+resource); 
                    if(file.exists()){
                        fileExist = true;
                        resource_dir = x+resource;

                        if (resource_dir.contains(".png")){
                            //Action 4
                            response = "HTTP/1.1 200 OK \r\n Content Type: image/png/ \r\n\r\n ";
                            try {
                                h_writer.writeString(response);
                                os = new FileOutputStream(resource_dir);
                                h_writer.writeBytes(buffer);
                                h_writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                socket.close();
                            }
                        } else {
                            //Action 3
                            response = "HTTP/1.1 200 OK\r\n \r\n ";
                            try {
                                h_writer.writeString(response);
                                os = new FileOutputStream(resource_dir);
                                h_writer.writeBytes(buffer);
                                h_writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                socket.close();
                            }
                        }
                
                    }
                } 
            }

            if (fileExist == false){
                response = "HTTP/1.1 404 Not found \r\n\r\n " + resource + " not supported \r\n.";
                try {
                    h_writer.writeString(response);
                    h_writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    socket.close();
                }
                        
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        


    }
    
}
