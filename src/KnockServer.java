
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Class KnockServer que abre un servidor local en el puerto 9000 y ejecuta el Thread que se encarga de las respuestas
public class KnockServer {

    static final int PORT = 9000;
    
    public static void main(String[] args) throws IOException {

        int portNumber = PORT;
        int num = 1;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
        	
        	while(true) {
        		Socket clientSocket = serverSocket.accept();
        		ThreadServerHandler hiloNewClient = new ThreadServerHandler(clientSocket);
        		hiloNewClient.start();
        		num++;
        	}
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

//Thread que se encarga de proporcionar las respuestas del servidor dependiendo de lo que proporcione el usuario simulando un chiste de formato toc toc en varios idiomas
class ThreadServerHandler extends Thread {
	
	private Socket clientSocket;
	private int vuelta,idioma;
	
	public ThreadServerHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
            
			
			out.println("Knok Knok");
			vuelta =0;
			boolean regame = false;
			while ((inputLine = in.readLine()) != null) {
				if(vuelta == 0) {	
					if(regame == true) {
						out.println("Knok Knok");
						inputLine = in.readLine();
					}
					if(inputLine.equalsIgnoreCase("Qui és?")) {
						out.println("Advocat");
						idioma = 1;
					}else if(inputLine.equals("Who is?")) {
						out.println("Atch");
						idioma = 2;
					}else if(inputLine.equals("¿Quien es?")) {
						out.println("Thomas");
						idioma = 3;
					}else {
						out.println("Se suposa que hauries d'haver dit... Qui és?, Who is? o ¿Quien es? i has dit "+inputLine);
					}
					vuelta++;
				}
				if(vuelta == 1) {
					inputLine = in.readLine();
					if(idioma == 1) {
						if(inputLine.equalsIgnoreCase("Quin advocat?")){
							out.println("El que tinc aqui penjat");
						}
						else {
							out.println("Se suposa que hauries d'haver dit... Quin advocat? i has dit "+inputLine);
						}
					}
					if(idioma == 2) {
						if(inputLine.equalsIgnoreCase("Atch who?")){
							out.println("Bless you!");
						}
						else {
							out.println("Se suposa que hauries d'haver dit... Atch who? i has dit "+inputLine);
						}
					}
					if(idioma == 3) {
						if(inputLine.equalsIgnoreCase("¿Qué Thomas?")){
							out.println("Yo un cubata, ¿y tú?");
						}
						else {
							out.println("Se suposa que hauries d'haver dit... ¿Qué Thomas? i has dit "+inputLine);
						}
					}
					out.println("Quieres volver a jugar? (Si/No)");
					vuelta++;
				}
				if(vuelta == 2) {	
					inputLine = in.readLine();
					if(inputLine.equalsIgnoreCase("Si")) {
						vuelta = 0;
						regame = true;
					}else if(inputLine.equalsIgnoreCase("No")) {
						out.println("Adeu");
					}else {
						out.println("Se suposa que hauries d'haver dit... Si o No i has dit "+inputLine);
					}
				}
            }
			
        }
        catch (IOException e) {
            System.out.println("Exception caught on thread");
            System.out.println(e.getMessage());
        }
      }
}
