import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WhalesDeals
{
    private static Properties pr= Main.pr;
    static private HashMap<String, Double> topBuyer = new HashMap<>();
    static private HashMap<String, Double> sellOut = new HashMap<>();
    static private ArrayList<Deal> listOfBuys= new ArrayList<>();
    static private ArrayList<Deal> listOfSells= new ArrayList<>();
    static private HashMap<String,Double> selfSellMap = new HashMap<>();

    static public void mostVolumeDealer(){
        for(Deal deal : listOfSells){
            sellOut.merge(deal.getTarget(), deal.getAmountDouble(), Double::sum);
        }

        for (Deal deal : listOfBuys) {
            topBuyer.merge(deal.getInitiator(), deal.getAmountDouble(), Double::sum);
           if(sellOut.containsKey(deal.getTarget())){
              sellOut.merge(deal.getTarget(), -deal.getAmountDouble(), Double::sum);}
        }
    }

    public static ArrayList<Deal> getListOfBuys(int quantity) {
         listOfBuys.sort((o1, o2) -> (o2.getAmountInt()-o1.getAmountInt()));
         ArrayList<Deal> tmpList;
        tmpList = (ArrayList<Deal>) listOfBuys.stream()
                .limit(quantity)
                .collect(Collectors.toList());
         return tmpList;
    }

    public static ArrayList<Deal> getListOfSells(int quantity) {
        listOfSells.sort((o1, o2) -> (o2.getAmountInt() - o1.getAmountInt()));
        ArrayList<Deal> tmpList;
        tmpList = (ArrayList<Deal>) listOfSells.stream()
                .limit(quantity)
                .collect(Collectors.toList());
        return tmpList;
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
        Deal deal;
       long amount=0;
       boolean IsBuy= operation.equals("buy");
       if (IsBuy){ amount = Long.parseLong(reader.readLine()
               .replace("    \"BitCloutToSellNanos\": ","").replace(",",""));
           listOfBuys.add(deal=new Deal(true, initiator, target, amount));
       }
       else {
           reader.readLine();
           try {
               amount = Long.parseLong(reader.readLine().replace("    \"CreatorCoinToSellNanos\": ","").replace(",",""));

           }catch (NumberFormatException e){ System.out.println("format error cathing"+initiator);}
           listOfSells.add(deal=new Deal(false, initiator, target,amount));
           if(initiator.equals(target)) selfSellMap.merge(initiator, deal.getAmountDouble() , Double::sum);
       }
    }

    public static void writeResultInfile(BufferedWriter whalesOutput, BufferedWriter sellYourselfF) throws IOException {
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


        StringBuilder sb= new StringBuilder();
        selfSellMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(25)
                .forEach(entry -> sb
                .append(userNames.changeHashToName(entry.getKey()))
                .append(" sold a part of his personal coins in the amount of ")
                .append(entry.getValue())
                .append("BitClout.\n"));
        sb.append(pr.getProperty("SkamAlertEnd"));

        sellYourselfF.write(sb.toString());

    }

    public static void writeMaxVolumeDealers(BufferedWriter maxVolume) throws IOException {
        for (int i = 0; i <Integer.parseInt(pr.getProperty("MaxVolumeBuyerCount")); i++) {
            maxVolume.write(pr.getProperty("MaxVolumeBuyIntr"));
            String whaleHash =(Collections.max(topBuyer.entrySet(), Map.Entry.comparingByValue()).getKey());
            maxVolume.write(userNames.changeHashToName(whaleHash)+ " made transactions on "+ topBuyer.get(whaleHash));
            topBuyer.remove(whaleHash);
            maxVolume.write(pr.getProperty("MaxVolumeBuyOutr"));
        }

        for (int i = 0; i <Integer.parseInt(pr.getProperty("MaxVolumeBuyerCount")); i++) {
            maxVolume.write(pr.getProperty("MaxVolSellIntr"));
            String skam =(Collections.max(sellOut.entrySet(), Map.Entry.comparingByValue()).getKey());
            maxVolume.write(userNames.changeHashToName(skam)+ " was sold to "+ sellOut.get(skam));
            sellOut.remove(skam);
            maxVolume.write(pr.getProperty("MaxVolSellOutr"));
        }

    }

    protected static class Deal {
        private boolean IsBuy;
        private String initiator;
        private String target;
        private long  amount;
        private String dealType;


        public boolean isBuy() {
            return IsBuy;
        }

        public String getInitiator() {
            return initiator;
        }

        public String getTarget() {
            return target;
        }

        public int getAmountInt(){   //return int for compare format xxxx.xxx 3sign after dot.
            long fIter = amount/1_000_000;
            return (int)fIter;
        }

        public double getAmountDouble() {
            return (double) getAmountInt()/1000;
        }

        public Deal(boolean isBuy, String initiator, String target, long amount) {
            this.IsBuy = isBuy;
            this.initiator = initiator;
            this.target = target;
            this.amount = amount;
            dealType = isBuy() ? " buy " : " sell ";
        }

        public String getMessageAboutDeal(){
            return userNames.changeHashToName(getInitiator())+ dealType +
                    userNames.changeHashToName(getTarget())+ " for " + getAmountDouble() + " BitClouds";
        }
    }
}
