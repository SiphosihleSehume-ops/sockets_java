package client;

import java.io.*;
import java.net.*;

public class Client {
    static void main(String[] args) throws IOException {
        //Unique: Declare and initialize `localhost`
        String host = "localhost";
        int port = 9090;

        try (//Instantiate client socket
            Socket socket = new Socket(host, port);
            //Build `output` pipeline object
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //Build `input` pipeline object
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Build `input through the CMD line`
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ){

            System.out.println("Server connected. Type JSON and press enter: ");

            //Sending data to server
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                //Send data to server
                out.println(userInput);

                //Wait for server responsive
                String response = in.readLine();
                System.out.println("Server responded " + response);

                if ("quit".equalsIgnoreCase(userInput)) break;
            }
        }

    }
}
