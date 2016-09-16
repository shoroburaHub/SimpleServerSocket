package my_socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by koko on 25.08.16.
 */
public class Socet extends Thread {
    Scanner scanner = new Scanner(System.in);

    Socket s;
    int num;


    public static void main(String[] args) {


        try
        {
            int i = 0; // счётчик подключений

            // привинтить сокет на локалхост, порт 3128
            ServerSocket server = new ServerSocket(3128, 0,
                    InetAddress.getByName("127.0.1.2"));

            System.out.println("server is started");

            InetAddress IP= InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());

            System.out.println(server.getInetAddress().getHostAddress());

            // слушаем порт

            while(true)
            {
                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единичку

                new Socet(i, server.accept());

                i++;

            }
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений
    }

    public Socet(int num, Socket s)
    {
        // копируем данные
        this.num = num;
        this.s = s;

        // и запускаем новый вычислительный поток (см. ф-ю run())
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run()
    {
        try
        {
            // из сокета клиента берём поток входящих данных от клииента к серверу
            InputStream is = s.getInputStream();
            // и оттуда же - поток данных от сервера к клиенту
            OutputStream os = s.getOutputStream();

            // буффер данных в 64 килобайта
            byte buf[] = new byte[64*1024];
            // читаем 64кб от клиента, результат - кол-во реально принятых данных
            int r = is.read(buf);

            // создаём строку, содержащую полученную от клиента информацию
            String data = new String(buf, 0, r);

            // вывод клиентского сообщения
            System.out.println("client : "+ data);

            // запись и отправка ответа
            System.out.println("write answer");
            String answer = scanner.nextLine();
            s.getOutputStream().write(answer.getBytes());

            // завершаем соединение
            s.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }



    }


}
