import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockchainServerRunnable implements Runnable{

    private Socket clientSocket;
    private Blockchain blockchain;

    public BlockchainServerRunnable(Socket clientSocket, Blockchain blockchain) {
        // implement your code here
    	this.clientSocket = clientSocket;
    	this.blockchain = blockchain;
    }

    public void run() {
        // implement your code here
    	try {
    		handler(clientSocket.getInputStream(), clientSocket.getOutputStream());
    	}
    	catch (Exception e) {
    		System.err.println(e);
    	}
    	finally {
    		reallyClose(clientSocket);
    	}
    }
    
    public void handler(InputStream is, OutputStream os) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
    	PrintWriter pw = new PrintWriter(os, true);
    	String line;
    	while((line = br.readLine()) != null){
 
			if(line.equals("cc")){
				return;
			}
			if(line.equals("pb")){
				synchronized(blockchain){
					pw.println(blockchain.toString() + "\n");
				}
			}
			else {
				synchronized(blockchain){
					if(blockchain.addTransaction(line))
						pw.println("Accepted\n");
					else
						pw.println("Rejected\n");
				}
			}
		
    	}
    }
    
    public void reallyClose(Socket socket){try{socket.close();}catch(Exception e){}}
    // implement any helper method here if you need any
}
