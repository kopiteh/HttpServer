package sdf;

import java.io.IOException;


public class Main {
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        int port = 3000;
        String[] docRoot = {"/static"};

        //Task 3
        if (args != null && args.length >= 1 && args.length == 2){

            if (args[0].equals("--port")){
                port = Integer.valueOf(args[1]);
            } else if (args[0].equals("--docRoot")){
                docRoot = args[1].split(":"); 
            } else {
                System.out.println("The HTTP Server will run on Port 3000 and the docRoot is ./target");
            }
        } else if (args != null && args.length >= 1 && args.length == 4){
                port = Integer.valueOf(args[1]); 
                docRoot = args[3].split(":"); 
        } else {
            System.out.println("The HTTP Server will run on Port 3000 and the docRoot is ./target");
        }
        


        HttpServer myserver = new HttpServer(port,docRoot);
        myserver.start_server();
       


    }
}
