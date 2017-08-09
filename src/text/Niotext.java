package text;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Niotext {
	public static void main(String[] args) throws IOException {
		Selector selector =Selector.open();
		
		try {
			//��socketͨ��,���÷�����ģʽ
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.socket().bind(new InetSocketAddress(8083));
			ssc.configureBlocking(false);
			//ע�����
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			
			while(true){
				//ѡ��
				int m = selector.select();
				if(m==0){
					continue;
				}
				Set keyset = selector.selectedKeys();
				Iterator it = keyset.iterator();
				while(it.hasNext()){
				
					
					SelectionKey key =(SelectionKey)it.next();
					
					if((key.readyOps()&SelectionKey.OP_ACCEPT)==SelectionKey.OP_ACCEPT){
						System.out.println("SHIT!and FUCK!");
						SocketChannel accept = ((ServerSocketChannel) key.channel()).accept();
						if(accept!=null){
							accept.configureBlocking(false);
							accept.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ);
							
						}
						it.remove();
					}
					if((key.readyOps()&SelectionKey.OP_READ)==SelectionKey.OP_READ){
						System.out.println("can read some");
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel channel = (SocketChannel)key.channel();
						buffer.clear();
						int n;
						n = channel.read(buffer);
						while(n>0){
							buffer.flip();
							byte[] bts = buffer.array();
							
							String aa = new String(bts, 0,buffer.limit());
							System.out.println(new String(aa));
							buffer.clear();
							n = channel.read(buffer);
						}
						if(n==-1){
							channel.close();
							key.cancel();
						}
						it.remove();
					}
					
				}
				
			}
		} catch (ClosedChannelException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		
	}
}
