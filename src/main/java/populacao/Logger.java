package populacao;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Logger {

    private final String LOG = "log.txt";
    public void write(String info) {
        Path path = Paths.get(LOG);
        info.concat("\n");
        try {
            Files.write(path, info.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read() {
        return read(10);
    }

    public String read(int limite) {
        List<String> list = new ArrayList<>(); 
        try (FileInputStream fileInputStream = new FileInputStream(Paths.get(LOG).toFile())) {

            FileChannel channel=fileInputStream.getChannel();
            ByteBuffer buffer=channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            buffer.position((int)channel.size());
            int count=0;
            StringBuilder builder=new StringBuilder();
            for(long i=channel.size()-1; i>=0;i--) {
                char c=(char)buffer.get((int)i);
                builder.append(c);
                if(c == '\n') {
                    if(count==limite)break;
                    count++;
                    builder.reverse();
                    list.add(builder.toString());
                    builder=null;
                    builder=new StringBuilder();
                }
            }
            channel.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        Collections.reverse(list);
        return String.join("", list);
    }
}