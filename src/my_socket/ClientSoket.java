package my_socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by koko on 25.08.16.
 */
public class ClientSoket {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // открываем сокет и коннектимся к localhost:3128
        // получаем сокет сервера
        Socket s = new Socket("127.0.1.2", 3128);
        System.out.println(s.getInetAddress().getHostAddress());
        try {

            while(s.getInputStream().available() == 0) {
                s = new Socket("127.0.1.2", 3128);


                System.out.println("write massage: ");
                String massage = scanner.nextLine();

                s.getOutputStream().write(massage.getBytes());


                // читаем ответ
                byte buf[] = new byte[64 * 1024];
                int r = s.getInputStream().read(buf);
                String data = new String(buf, 0, r);

                // вывод ответа
                System.out.println("server : " + data);
            }
        } catch (Exception e) {
//
            e.printStackTrace();
        }

    }

}
