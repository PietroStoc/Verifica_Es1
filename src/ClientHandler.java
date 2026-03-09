import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private Socket client;
    private HashMap<String, Integer> utenti;

    public ClientHandler(Socket client, HashMap<String, Integer> utenti) {
        this.client = client;
        this.utenti = utenti;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            String linea = in.readLine();
            String risposta = ServerTcp.gestisciComando(linea, utenti);
            out.println(risposta);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
