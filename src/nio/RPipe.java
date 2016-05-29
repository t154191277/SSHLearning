package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RPipe {
//	Java NIO �ܵ���2���߳�֮��ĵ����������ӡ�Pipe��һ��sourceͨ����һ��sinkͨ�������ݻᱻд��sinkͨ������sourceͨ����ȡ��
	public static void pipe(){
		Pipe pipe = null;
		ExecutorService exec = Executors.newFixedThreadPool(2);
		try {
			pipe = Pipe.open();
			final Pipe pipeTemp = pipe;
			
			exec.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					Pipe.SinkChannel sinkChannel = pipeTemp.sink();
					while(true){
						TimeUnit.SECONDS.sleep(1);
						String newData = "Pipe Test At Time "+System.currentTimeMillis();
						ByteBuffer buf = ByteBuffer.allocate(1024);
						buf.clear();
						buf.put(buf);
						buf.flip();
						
						while(buf.hasRemaining()){
							System.out.println(buf);
							sinkChannel.write(buf);
						}
					}
				}
			});
			
			exec.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					Pipe.SourceChannel sourceChannel = pipeTemp.source();
					while(true){
						TimeUnit.SECONDS.sleep(1);
						ByteBuffer buf = ByteBuffer.allocate(1024);
						buf.clear();
						int bytesRead = sourceChannel.read(buf);
						while(bytesRead > 0){
							buf.flip();
							byte[] b = new byte[bytesRead];
							int i = 0;
							while(buf.hasRemaining()){
								b[i] = buf.get();
								System.out.printf("%X",b[i]);
								i++;
							}
							String s = new String(b);
							System.out.println("===="+s);
							bytesRead = sourceChannel.read(buf);
						}
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			exec.shutdown();
		}
	}
}
