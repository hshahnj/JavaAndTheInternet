import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTemp {
    public static void main(String[] args) throws IOException {
//
//        if (args.length != 1) {
//            System.err.println("Usage: java SocketServer port");
//            return;
//        }

        int port = 2020;
        Socket echoSocket;
        try {
//            ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
            ServerSocket s = new ServerSocket(port);
//            for (; ; ) {
//                System.out.println("New Socket!");
//                echoSocket = s.accept();
//                System.out.println("Connection from: " + echoSocket.getInetAddress());
//                MainThread NewReq = new MainThread(echoSocket);
//                NewReq.start();
//            }
            MainThread NewReq = new MainThread(s);
            NewReq.start();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }
}

class MainThread extends Thread {
    Socket echoSocket;
    ServerSocket socketServerMain;
    InputStream sin = null;
    OutputStream sout = null;
    int i;
    StringBuilder t;
    String s;
    String[] inputArgs;
    String header;

    public MainThread(ServerSocket socketServer) {
        try {
            socketServerMain = socketServer;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void run() {
        try {
            do {
                System.out.println("In Loop!");
                byte[] b = new byte[1024];
                echoSocket = socketServerMain.accept();
                sin = echoSocket.getInputStream();
                sout = echoSocket.getOutputStream();
                int i = sin.read(b);
                header = new String(b, 0, i);
                System.out.println("Header: " + header + "    " + header.length());

                if (header.equals("Login")) {
                    sout.write("Login Received".getBytes());
                    sout.flush();
                    LoginThread loginThread = new LoginThread(echoSocket, sin, sout);
                    loginThread.start();
                }
                if (header.equals("Open")) {
                    sout.write("Open Received".getBytes());
                    sout.flush();
                    OpenBankAccountThread openBankAccountThread = new OpenBankAccountThread(echoSocket, sin, sout);
                    openBankAccountThread.start();
                    System.out.println("I am in OPEN on SERVER SIDE");
                }
                if (header.equals("Deposit")) {
                    sout.write("Deposit Received".getBytes());
                    sout.flush();
                    DepositThread depositThread = new DepositThread(echoSocket, sin, sout);
                    depositThread.start();
                    System.out.println("I am in DEPOSIT on SERVER SIDE");
                }
                if (header.equals("Withdraw")) {
                    sout.write("Withdraw Received".getBytes());
                    sout.flush();
                    WithdrawThread withdrawThread = new WithdrawThread(echoSocket, sin, sout);
                    withdrawThread.start();
                    System.out.println("I am in WITHDRAW on SERVER SIDE");
                }
                if (header.equals("Transfer")) {
                    sout.write("Transfer Received".getBytes());
                    sout.flush();
                    TransferThread transferThread = new TransferThread(echoSocket, sin, sout);
                    transferThread.start();
                    System.out.println("I am in Transfer on SERVER SIDE");
                }
                if (header.equals("Transactions")) {
                    sout.write("Transactions Received".getBytes());
                    sout.flush();
                    InquireTransactionsThread inquireTransactionsThread = new InquireTransactionsThread(echoSocket, sin, sout);
                    inquireTransactionsThread.start();
                    System.out.println("I am in Transactions on SERVER SIDE");
                }
                if(header.equals("Exit")){
                    System.out.println("Ending connection now!");
                    sout.write("End Signal".getBytes());
                    sout.flush();
                    Thread.sleep(5000);
                    break;
                }
            } while (true);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
/*
    public void run() {
        try {

//            BufferedReader rd = new BufferedReader(new InputStreamReader(sin));
//            s = rd.readLine();
//            System.out.println(s);
            int i = sin.read(b);
            System.out.write(b, 0, i);
            System.out.flush();
            System.out.println();
            header = new String(b, StandardCharsets.UTF_8);
            header = header.replaceAll("\u0000.*", "");

            if (header.equals("Login")) {
                sout.write("Login Received".getBytes());
                sout.flush();
                LoginThread loginThread = new LoginThread(echoSocket, sin, sout);
                loginThread.start();

            } else if (header.equals("Open")) {
                sout.write("Open Received".getBytes());
                sout.flush();
//                System.out.println("I am in OPEN on SERVER SIDE");
                OpenBankAccountThread openBankAccountThread = new OpenBankAccountThread(echoSocket, sin, sout);
                openBankAccountThread.start();
                System.out.println("I am in OPEN on SERVER SIDE");
                System.out.println("Main Thread sleeping now.");
                Thread.sleep(20000);
                System.out.println("Main Thread started.");
                System.out.println("Echo socket closing now!");
                echoSocket.close();
            }


        } catch (Exception e) {
            System.err.println(e);
            return;
        }

        */
}

