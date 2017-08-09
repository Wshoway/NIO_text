package text;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
	public static void main(String[] args) {
		Socket sc = new Socket();
		
		try {
			SocketAddress sa = new InetSocketAddress("localhost", 8083);
			sc.connect(sa);
			OutputStream outputStream = sc.getOutputStream();
			outputStream.write("fuck you man!".getBytes());
			Thread.sleep(10000);
			outputStream.write("fuck you man!,again".getBytes());
			outputStream.close();
			sc.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally{
			try {
				sc.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
