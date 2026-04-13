package server;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] cmdArgs) throws IOException{
        //Declare and initialize port number
        int port = 9090;
        //We instantiate a ServerSocket object; "Turns on Server" at a specific entrance gate (port)
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);

        while (true) {  //Handling multiple clients one by one

            // blocks until client connects; accept() is a blocking call; server will finish with one client, loop back
            // the top, and wait for next. It's a "sequential" server
            try (Socket clientSocket = serverSocket.accept();
                 //BufferedReader for receiving input from client; is there to provide the readLine() method ? entire
                 //lines of text : raw bytes (input pipe)
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 //To send data to client machine (output pipe)
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                //Crucial; line for logging and debugging; `getInetAddress` pulls the IP Address
                //Java automatically calls the `.toString()` method on Ip Address object
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                /* Summary notes:
                1. Waits for client
                2. Connects and sets up the pipes
                3. Prints "Client connected"
                4. Immediately closes connection (because the `try` block ends)
                * */

                String jsonLine;
                while ((jsonLine = in.readLine()) != null) { // readLine blocks until \n
                    System.out.println("Received: " + jsonLine);

                    //This is where we `parse` JSON. FOR DEMO WE ECHO BACK
                    String response = "{\"status\":\"ok\",\"echo\":" + jsonLine + "}\n";
                    out.println(response); //Println adds \n, true in constructor = autoflush
                }
            } catch (IOException e) {
                System.out.println("Client disconnected or error: " + e.getMessage());
            }
        }



    }
}
