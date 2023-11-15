
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//Cliente que se conecta al puerto local en el que se encuentra el servidor y permite introducir las respuestas del usuario en formato de texto
public class KnockClient {

	static final int PORT = 9000;
	static final String HOST = "localhost";

	public static void main(String[] args) throws IOException {

		String hostName = HOST;
		int portNumber = PORT;
		boolean salir = false;
		Scanner sc = new Scanner(System.in);

		try {
			Socket echoSocket = new Socket(hostName, portNumber);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

			int num = 0;
			while (salir == false) {
				if(num == 3) {
					System.out.println( in.readLine());
					String str = "";
					str = sc.nextLine();
					out.println(str);
					if(str.equalsIgnoreCase("Si")) {
						num = 1;
						out.println("");
						System.out.println( in.readLine());
						str = sc.nextLine();
						out.println(str);	
					}else {
						salir = true;
					}
				}else {
					System.out.println(in.readLine());
					if (num != 2){
						String str = "";
						str = sc.nextLine();
						out.println(str);
					}
					num++;
				}
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}
