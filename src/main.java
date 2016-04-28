import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;

import static java.lang.Math.abs;

/**
 * Created by Lizabeth on 20.03.2016.
 */
public class main{
    public static void main(String[] args) {
        String texts = new String("aaaaaaaaaaaaaaabbbbbbbccccccdddddeeee");//начальная строчка
        char[] text = texts.toCharArray();
        Map<String,Double> symbolList = new HashMap<String,Double>(); //асоц. массив для

        //формировка списка - из строки в асоц. массив
        symbolList=listHandler.stringToMap(text);
        System.out.println("List of the letters:");
        listHandler.printMap(symbolList);//вывод асоц. массива

        //сортировка массива по значениям (кол-ву символов в тексте)
        symbolList=listHandler.mapSorter(symbolList);
        System.out.println("List of the letters:");
        listHandler.printMap(symbolList);

        listHandler.fillCipherList(symbolList);

        //Построение дерева

        //деление на две части


        //начальное заполнение массива буква-шифр
        listHandler.fillCipherList(symbolList);
        //передаем на обработку
        listHandler.recursiveDiv(1,symbolList.size(),symbolList);

        for (Map.Entry<String,String> entry : listHandler.cipherList.entrySet()) {
            System.out.printf("\nkey = %s, value = %s", entry.getKey(), entry.getValue());
        }

        String result = new String();
        result=listHandler.codeText(text);
        System.out.println("\n"+result);
    }
}
//сравнение
class ValueComparator implements Comparator {

    Map base;
    public ValueComparator(Map base) {
        this.base = base;
    }

    public int compare(Object a, Object b) {

        if((Double)base.get(a) < (Double)base.get(b)) {
            return 1;
        } else if((Double)base.get(a) == (Double)base.get(b)) {
            return 0;
        } else {
            return -1;
        }
    }
}
class listHandler{

    //буква-шифр
    public  static Map<String,String> cipherList = new HashMap<String,String>();

    //начальное заполнение списка шифров
    public static void fillCipherList(Map<String,Double> symbolList){
        for (Map.Entry<String,Double> entry : symbolList.entrySet()) {
            cipherList.put(entry.getKey(), "");
        }
    }

    //массив символов в асоц. массив
    public static Map<String,Double> stringToMap(char[] text){
        Map<String,Double> symbolList = new HashMap<String,Double>();
        for(int i=0; i<text.length; i++){
            if(symbolList.containsKey(String.valueOf(text[i]))){
                symbolList.put(String.valueOf(text[i]),symbolList.get(String.valueOf(text[i]))+1);
            }else{
                symbolList.put(String.valueOf(text[i]),1.0);
            }
        }
        return symbolList;
    }

    //напечатать асоц. массив
    public static void printMap(Map<String,Double> symbolList){
        for (Map.Entry<String,Double> entry : symbolList.entrySet()) {
            System.out.printf("key = %s, value = %.0f\n", entry.getKey(), entry.getValue());
        }
    }

    //сортировка асоц. масс.
    public static Map<String,Double> mapSorter(Map<String,Double> map){
        ValueComparator bvc =  new ValueComparator(map);
        TreeMap<String,Double> sorted_map = new TreeMap(bvc);
        sorted_map.putAll(map);

        return sorted_map;
    }

    //деление
    public static int dividingList(Map<String,Double> map){

        Double sum=0.0, sum1=0.0, sum2=0.0;
        //общая сумма элем. массива
        for (Map.Entry<String,Double> entry : map.entrySet()) {
            sum+=entry.getValue();
        }
        System.out.println(sum);

        Double diff=0.0, min_diff=0.0;//поточная и минимальная разницы
        int min_i=1; //минимум
        System.out.println(map.size());

        //просмотр всех вариантов деления
        for(int i=1; i<map.size()-1; i++){
            int j=1;

            //просмотр всех элементов массива
            for(Double value : map.values()){
                //если элемент до разделителя - прибавляем к первой сумме
                if(j++ <= i){
                    sum1+=value;
                }else{
                    //если элемент после разделителя - приб. ко второй сумме
                    sum2+=value;
                }
            }
            //разница между двумя половинками
            diff= abs(sum1-sum2);

            //любое первое значение разницы будет присвоено мин. разнице
            if(i==1){
                min_diff=diff;
            }else{ //если не первое значение
                if(diff<=min_diff) {  //если новая разница меньше мин. разницы
                    min_diff = diff; //мин. разница получает значение этой разницы
                    min_i = i;  //и сохраняем индекс последнего элем. массива, вошедшего в первую сумму
                }
            }

            System.out.println("sum1="+sum1+" sum2="+sum2+" diff="+diff+" min_diff="+min_diff+" min_i="+min_i);
            sum1=0.0;
            sum2=0.0;
        }
        System.out.println("min_diff="+min_diff+" i="+min_i);

        //заполнение шифра
        int ind = 1;
        //для всех значений из разделенного массива
        for (Map.Entry<String,Double> entry : map.entrySet()) {
            if(ind<=min_i){  //если значение в первой половине
                for (Map.Entry<String,String> en : cipherList.entrySet()){  //перебираем значения глобального массива
                    if(en.getKey()==entry.getKey()){   //если значение совпало
                        en.setValue(en.getValue()+'0');   //дописываем 0 к шифру
                    }
                }
            }else{   //если со второй половины
                for (Map.Entry<String,String> en : cipherList.entrySet()){
                    if(en.getKey()==entry.getKey()){
                        en.setValue(en.getValue()+'1');   // дописываем 1
                    }
                }
            }
            ind++;
        }

        return min_i;
    }

    public static void recursiveDiv(int s1, int s2, Map<String,Double> symbolList){
         if(s1==s2){

        }else{
            //новий список який треба поділити навпіл
            Map<String,Double> newList = new TreeMap<String,Double>();
            int i=1;
            for (Map.Entry<String,Double> entry : symbolList.entrySet()) {
                if(i>=s1 && i<=s2) {
                    newList.put(entry.getKey(), entry.getValue());
                }
                i++;
            }
            int half = dividingList(newList) + s1-1;
            System.out.print("s1="+s1+" half="+half+"  s2="+s2);
            recursiveDiv(s1, half,symbolList);
            recursiveDiv(half+1,s2,symbolList);
        }

    }

    public static String codeText(char[] text){
        String res = new String();
        //перебор каждой буквы, которую нужно зашифровать
        for(int i=0; i<text.length; i++){
            //перебор массива буква-код
            for (Map.Entry<String,String> entry : cipherList.entrySet()) {
                if(Character.toString(text[i]).equals(entry.getKey())){
                    res+=entry.getValue();
                }
            }

        }

        return res;
    }
}
