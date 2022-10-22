import java.util.HashMap;

public class DictionaryUsingHash {
    private static HashMap<String, String> dictionary;
    DictionaryUsingHash(){
        dictionary=new HashMap<String, String>();
        addword("Erwin","This is my first name.");
        addword("Rommel", "This is my Last Name.");
        addword("Moassis", "This is my nick name.");
    }
    public static boolean addword(String word, String meaning){
        dictionary.put(word, meaning);
        return true;
    }
    public static String findMeaning(String word){
        if(!dictionary.containsKey(word)){
            ErwinDictionary.connectToDatabase();
        }
        if(!dictionary.containsKey(word)){
            return "Given word not found";
        }
        else{
            return dictionary.get(word);
        }
    }
}
