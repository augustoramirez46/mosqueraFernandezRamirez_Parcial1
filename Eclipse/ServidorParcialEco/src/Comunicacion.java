import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {
	
	// Se definenn las variables de la comunciacion, y de paso se importa la libreria data.
	
	private ServerSocket ss;
	private Socket s;
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	public Comunicacion() {	
		
		//el try que no sabemos bien porque es necesario, pero pues se pone y eclipse lo pide.
		try {
			//Se define el nuevo Socket del server y su puerto de entrada
			ss = new ServerSocket(5000);
			
			//Asi acepta la peticion de entrada al socket del servidor
			s = ss.accept();
			
			//Se define donde entran los datos
			entrada = new DataInputStream(s.getInputStream());
			
			//Y asimismo como salen estos
			salida = new DataOutputStream(s.getOutputStream());
			
			//El ding ding de conexion
			System.out.println("CONECTADO");
		} catch (IOException e) {			
			e.printStackTrace();
		}			
	}
	

	//El thread de la conexion
	@Override
	public void run() {
		while(true) {			
			try {
				
				//donde se reciben los datos 
				recibir();
				
				//el delay
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}

	//No estamos seguros del throw, creemos que es como el try catch
	private void recibir() throws IOException {
		
		//Se lee lo que llega a la variable de entrada
		String mensaje = entrada.readUTF();
		
		//Del observer, avisa que llego algo
		setChanged();
		notifyObservers(mensaje);
		clearChanged();		
	}
	
	public void enviar(String mensaje) {
		try {
			
			//Pone lo que se va a enviar en la variable salida
			salida.writeUTF(mensaje);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}
