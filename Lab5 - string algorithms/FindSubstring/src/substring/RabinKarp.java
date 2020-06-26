package substring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RabinKarp {
    public static final int INDENT = 5;

    //private String pat;
    private long patHash;  //хеш-значение образца
    private int M; //длина образца
    private long Q; //большое простое число
    private int R = 256; //размер алфавита
    private long RM; //R^(M-1)%Q

    public RabinKarp(String pat) {              //5.3.10
        //this.pat = pat;

        this.M = pat.length();
        Q = longRandomPrime(1000);           // 5.3.33
        RM = 1;
        for (int i = 1; i < M; i++) {
            RM = (R * RM) % Q;
        }
        patHash = hash(pat, M);
    }

    public boolean check(int i) {
        return true;
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    private int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        if (patHash == txtHash) return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash)
                if (check(i - M + 1)) return i - M + 1;
        }
        return N;
    }

    public long longRandomPrime(long n) {   // 5.3.33
        List<Long> randPrimes = LongStream.rangeClosed(2, n)
                .filter(x -> isPrime(x)).boxed()
                .collect(Collectors.toList());
        return randPrimes.get((int) (Math.random() * randPrimes.size()));
    }

    private boolean isPrime(long number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .filter(n -> (n & 0X1) != 0)
                .allMatch(n -> number % n != 0);
    }

    public List<Integer>  allOccurrences(String txt, int sampleLength){
        int lengthOfTxt = txt.length();
        int index = 0;
        int difference = 0;
        List<Integer> occurrences = new ArrayList<>();
        String newTxt = txt;
        String oldTxt;

        do{
            index = search(newTxt);
            if (index == lengthOfTxt || index == newTxt.length()) break;

            if (difference > 0){
               occurrences.add(index + difference);
            }
            else {
                occurrences.add(index);
            }
            oldTxt = newTxt;
            newTxt = newTxt.substring(index+sampleLength);
            difference += oldTxt.length() - newTxt.length();
        }while (newTxt.length() > sampleLength && index+difference != lengthOfTxt);

        return occurrences;
    }


    public int count(List<Integer> occurrences){
        return occurrences.size();
    }

    public void searchAll(String txt, int sampleLength){
        List<Integer> occurrences = allOccurrences(txt, sampleLength);
        int lengthOfTxt = txt.length();
        System.out.println("Все вхождения: ");
        for (int i=0; i < occurrences.size(); i++){
            System.out.println(i + ") Порядковый номер вхождения: " + occurrences.get(i));
            if (occurrences.get(i) - INDENT < 0 && occurrences.get(i) + sampleLength + INDENT > lengthOfTxt){
                System.out.println(txt);
            }
            else {
                if (occurrences.get(i) - INDENT < 0) {
                    System.out.println(txt.substring(0, occurrences.get(i) + sampleLength + INDENT));
                }
                else{
                    if  (occurrences.get(i) + sampleLength + INDENT > lengthOfTxt){
                        System.out.println(txt.substring(occurrences.get(i) - INDENT));
                    }
                    else{
                        System.out.println(txt.substring(occurrences.get(i) - INDENT, occurrences.get(i) + sampleLength + INDENT));
                    }
                }
            }

        }
    }


    public static void main(String[] args) {

        String text = "She felt cold and closed the window. Her flat is close to the shops. Bill is my closest friend. "
                + "I was so close to getting Pete Neon. It makes sense to stay close to Lily. "
                + "Located close to lively places and Parisian nightlife. What being close to someone feels like. "
                + "2 bedroom rooms close to beach, golf and city. Someone who could get close to Lex.";
        String sample = "close";
        RabinKarp algorithm = new RabinKarp(sample);

        int sampleLength = sample.length();

        int count = algorithm.count(algorithm.allOccurrences(text, sampleLength));
        System.out.println("Количество вхождений подстроки: " + count);
        if (count != 0){
            algorithm.searchAll(text, sampleLength);
        }
        else {
            System.out.println("Текст не содержит эту подстроку");
        }
    }
}
