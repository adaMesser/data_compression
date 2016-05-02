import java.util.*;
import java.util.stream.Stream;

import static javafx.scene.input.KeyCode.K;
import static javafx.scene.input.KeyCode.V;

/**
 *sir sid eastman easily teases sea sick seals
 */
public class main{
    public static void main(String[] args) {
        String texts = new String("swiss_miss");//начальная строчка
        System.out.println(texts);   //выводим начальную строчку

        char[] text = texts.toCharArray();  //строчку передаем в символьный массив

        //асоц. массив для символов (буква - кол-во повторений)
        Map<String,Integer> symbolList = new HashMap<String, Integer>();
        symbolList = listHandler.stringToMap(text);
        listHandler.printMap(symbolList);

        //создаем массивы со значениями границ интервалов символов
        listHandler.calcRanges(symbolList);

        //вывести границы
        System.out.println("low ranges:");
        listHandler.printRange(listHandler.lowRange);
        System.out.println("high ranges:");
        listHandler.printRange(listHandler.highRange);
        System.out.println(listHandler.arithmMethod(text));
    }
}
class listHandler{
    public static Map<String, Double> lowRange = new HashMap<String,Double>();
    public static Map<String, Double> highRange = new HashMap<String,Double>();
    //напечатать cловарь
    public static void printMap(Map<String,Integer> symbolList){
        for (Map.Entry<String,Integer> entry : symbolList.entrySet()) {
            System.out.println("key = "+entry.getKey() + "\t value = "+entry.getValue());
        }
    }
    public static void printRange(Map<String,Double> symbolList){
        for (Map.Entry<String,Double> entry : symbolList.entrySet()) {
            System.out.println("key = "+entry.getKey() + "\t value = "+entry.getValue());
        }
    }
    //массив символов в асоц. массив
    public static Map<String,Integer> stringToMap(char[] text){
        Map<String,Integer> symbolList = new HashMap<String,Integer>();
        for(int i=0; i<text.length; i++){
            if(symbolList.containsKey(String.valueOf(text[i]))){
                symbolList.put(String.valueOf(text[i]),symbolList.get(String.valueOf(text[i]))+1);
            }else{
                symbolList.put(String.valueOf(text[i]),1);
            }
        }
        return symbolList;
    }

    public static int calcSize(Map<String,Integer> symbolList){
        int res=0;
        for (Map.Entry<String,Integer> entry : symbolList.entrySet()){
            res += entry.getValue();
        }
        return res;
    }
    public  static void calcRanges(Map<String,Integer> symbolList){
        Double low=0.0, high=0.0;
        int textSize = 0;
        textSize = calcSize(symbolList);

        for (Map.Entry<String,Integer> entry : symbolList.entrySet()) {
            low = high;
            lowRange.put(entry.getKey(),low);
            high = low + entry.getValue()*1.0/textSize;
            highRange.put(entry.getKey(),high);
        }

    }
    public static Double arithmMethod(char[] text){
        Double result=0.0, low=0.0, high=1.0,range=0.0;
        System.out.println(" low = "+low+" high = "+high);
        for(int i=0; i<text.length; i++){
            range=high-low;
            high = low+range*highRange.get(Character.toString(text[i]));
            low=low+range*lowRange.get(Character.toString(text[i]));
            System.out.println(" low = "+low+" high = "+high);
        }

        result=low+(high-low)/2.0;
        return result;
    }
}
