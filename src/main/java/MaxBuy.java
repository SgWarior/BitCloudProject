import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;

public class MaxBuy extends SimpleFileVisitor {
    private BufferedWriter sell;
    private BufferedWriter buy;
    static private TreeMap<Long, String> buyTree= new TreeMap();
    static private TreeMap<Long, String> sellTree= new TreeMap();
    public MaxBuy (BufferedWriter sell, BufferedWriter buy) {
        this.sell = sell;
        this.buy = buy;
    }


    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        //System.out.println(file.toString());
        if (attrs.isRegularFile()){
            File current = new File(file.toString());
            try(BufferedReader reader = new BufferedReader(new FileReader(current))){
                while (reader.ready()){
                    String tmp = reader.readLine();
                    if(tmp.contains("  \"TxnType\": \"CREATOR_COIN\",")){
                        String initiator = reader.readLine().replace("  \"TransactorPublicKeyBase58Check\": \"", "").replace("\",", "");
                        for (int i = 0; i <6 ; i++) reader.readLine();
                        String target = reader.readLine().replace("      \"PublicKeyBase58Check\": \"", "").replace("\",", "");
                        for (int i = 0; i <11 ; i++) reader.readLine();
                        String typeTransaction =reader.readLine().replace("    \"OperationType\": \"", "").replace("\",", "");
                        long raw;
                        if(typeTransaction.equals("buy")){ raw = Long.parseLong(reader.readLine().replace("    \"BitCloutToSellNanos\": ", "").replace(",", ""));}
                        else {reader.readLine(); raw = Long.parseLong(reader.readLine().replace("    \"CreatorCoinToSellNanos\": ", "").replace(",", ""));}
                        long amount = raw/1_000_000_000;
                        if(amount <10) {continue;}else{ }
                        String answer = initiator+"  "+ typeTransaction +"  "+ target + " for "+amount + " BitClouds";
                        if(typeTransaction.equals("sell")){ buyTree.put(amount, answer); WhaleNamesByHash.addWhales(initiator);
                            System.out.println(buyTree.size() +" ==? " +WhaleNamesByHash.whaleMap.size());}
                        else {sellTree.put(amount, answer); }
                    }

                }
            }

        }

        return FileVisitResult.CONTINUE;
    }


    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        for (Map.Entry<Long, String> e: sellTree.entrySet()
             ) {sell.write(sellTree.get(e.getKey()));
             sell.newLine();

        }
        for (Map.Entry<Long, String> e: buyTree.entrySet()
        ) {buy.write(buyTree.get(e.getKey()));
            buy.newLine();
        }
        WhaleNamesByHash.writeWhales();
        return FileVisitResult.CONTINUE;
    }

}
