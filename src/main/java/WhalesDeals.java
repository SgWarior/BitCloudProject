import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WhalesDeals
{
    private static Properties pr= Main.pr;
    static private HashMap<String, Double> topBuyer = new HashMap<>();
    static private HashMap<String, Double> sellOut = new HashMap<>();
    static private ArrayList<Deal> listOfBuys= new ArrayList<Deal>();
    static private ArrayList<Deal> listOfSells= new ArrayList<Deal>();

    static public void mostVolumeDealer(){
        for (Deal deal : listOfBuys) {
            topBuyer.merge(deal.buyer, deal.amount, Double::sum);
            sellOut.merge(deal.buyer, deal.amount, Double::sum);
        }
    }

    public static ArrayList<Deal> getListOfBuys(int quantity) {
         listOfBuys.sort((o1, o2) -> (int) (o2.amount-o1.amount));
        listOfBuys = (ArrayList<Deal>) listOfBuys.stream()
                .limit(quantity)
                .collect(Collectors.toList());
         return listOfBuys;
    }

    public static ArrayList<Deal> getListOfSells(int quantity) {
        listOfSells.sort((o1, o2) -> (int) (o2.amount - o1.amount));
        listOfSells = (ArrayList<Deal>) listOfSells.stream()
                .limit(quantity)
                .collect(Collectors.toList());
        return listOfSells;
    }

    public static void addDeal(BufferedReader reader) throws IOException {
        for (int i = 0; i < 3; i++) reader.readLine();
        String initiator = reader.readLine().replace(("      \"PublicKeyBase58Check\": \""),"")
                .replace(pr.getProperty("noHashL"),"");

        for (int i = 0; i < 3; i++) reader.readLine();
        String target = reader.readLine().replace(("      \"PublicKeyBase58Check\": \""),"")
                .replace(pr.getProperty("noHashL"),"");

        for (int i = 0; i < 11; i++) reader.readLine();
       String operation= reader.readLine().replace("    \"OperationType\": \"","")
               .replace(pr.getProperty("noHashL"),"");

       long amount=0;
       boolean IsBuy= operation.equals("buy");
       if (IsBuy){ amount = Long.parseLong(reader.readLine()
               .replace("    \"BitCloutToSellNanos\": ","").replace(",",""));
           listOfBuys.add(new Deal(true, initiator, target, amount));
       }
       else if (operation.equals("sell")){reader.readLine();
                amount = Long.parseLong(reader.readLine()
               .replace("    \"CreatorCoinToSellNanos\": ","").replace(",",""));}
        listOfSells.add(new Deal(false, initiator, target, amount));
    }

    public static void writeResultInfile(BufferedWriter whalesOutput) throws IOException {
        int counter = 0;

        for (Deal whaleList : WhalesDeals.getListOfBuys(Integer.parseInt(pr.getProperty("ResultInWhaleList")))) {
            if(counter==0)whalesOutput.write(pr.getProperty("WhaleTopBef"));
            whalesOutput.write(whaleList.getMessageAboutDeal());counter++;whalesOutput.newLine();
            if(counter==3){whalesOutput.write(pr.getProperty("WhaleAfter")); counter=0;}
        }
        if (counter<3)whalesOutput.write(pr.getProperty("WhaleAfter"));

        counter=0;
        for (Deal whaleList : WhalesDeals.getListOfSells(Integer.parseInt(pr.getProperty("ResultInWhaleList")))) {
            if(counter==0)whalesOutput.write(pr.getProperty("MaxSellInBlock"));
            whalesOutput.write(whaleList.getMessageAboutDeal());counter++;whalesOutput.newLine();
            if(counter==3){whalesOutput.write(pr.getProperty("WhaleAfter")); counter=0;}
        }
        if (counter<3)whalesOutput.write(pr.getProperty("WhaleAfter"));


    }

    protected static class Deal {
        private boolean IsBuy;
        private String  buyer;
        private String  sellout;
        private double  amount;

        public boolean isBuy() {
            return IsBuy;
        }

        public String getBuyer() {
            return buyer;
        }

        public String getSellout() {
            return sellout;
        }

        public double getAmount() {
            return amount;
        }

        public Deal(boolean isBuy, String buyer, String sellout, long amount) {
            this.IsBuy = isBuy;
            this.buyer = buyer;
            this.sellout = sellout;
            long tmp = amount;
            this.amount = amount/10000000;
        }

        public String getMessageAboutDeal(){
            double dAmount = this.amount/100;
            return WhaleNamesByHash.changeHashToName(getBuyer())+ " buy " +
                    WhaleNamesByHash.changeHashToName(getSellout())+ " for " + dAmount + " BitClouds";
        }
    }
}
