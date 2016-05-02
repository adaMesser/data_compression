import java.util.*;
import java.util.stream.Stream;



/**
 * 44 in string
 * 35 in book (289)
 * 19 in my prog (274)
 */
public class main{
    public static void main(String[] args) {
        String texts = new String("sir sid eastman easily teases sea sick seals");//начальная строчка
        System.out.println(texts);   //выводим начальную строчку
        char[] text = texts.toCharArray();  //строчку передаем в символьный массив
        //создаем начальный словарь
        VocabularyHandler.vocabularyCreator();
        //сортируем словарь
        VocabularyHandler.vocabulary = listHandler.mapSorter(VocabularyHandler.vocabulary);
        //выводим начальный словарь
        listHandler.printVoc(VocabularyHandler.vocabulary);
        //кодируем фразу
        System.out.println(listHandler.codeText(text));
        //выводим остаточный словарь
        listHandler.printVoc(VocabularyHandler.vocabulary);
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

class VocabularyHandler{
    public static Map<String, Integer> vocabulary = new HashMap<String, Integer>();

    public static void vocabularyCreator(){
        for(int i=0; i<256; i++){
            vocabulary.put(""+(char)i,i);
        }
    }
}

class listHandler{
    //напечатать cловарь
    public static void printVoc(Map<String,Integer> symbolList){
        int i = 1;
        for (Map.Entry<String,Integer> entry : symbolList.entrySet()) {
            System.out.print(entry.getKey() + "("+entry.getValue()+")\t");
            if(i%16==0){
                System.out.print("\n");
            }

            i++;
        }
        System.out.print("\n");
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> mapSorter( Map<K, V> map )
    {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted( Map.Entry.comparingByValue(Comparator.naturalOrder()) ).forEachOrdered( e -> result.put(e.getKey(), e.getValue()) );

        return result;
    }

    public static String codeText(char[] text){
        String result = new String();
        String tmp = new String();
        //цикл по массиву символов кодируемой фразы
        for(int i=0; i<text.length; i++){
            //добавляем очередной символ во временную переменную фразы
            tmp+=text[i];
            //если фразы нет в словаре
            if(VocabularyHandler.vocabulary.get(tmp)==null){
                //добавляем ее в словарь
                VocabularyHandler.vocabulary.put(tmp,VocabularyHandler.vocabulary.size());
                //ее код прибавляем в результат
                result+="("+tmp+")"+VocabularyHandler.vocabulary.size();
                //фразу обнуляем
                tmp="";
                //для удобного вывода
                if(i%10==0){
                    result+="\n";
                }
            }
        }
        return result;
    }
}
