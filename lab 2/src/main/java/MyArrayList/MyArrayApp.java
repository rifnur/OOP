package MyArrayList;

import java.lang.reflect.Array;

public class MyArrayApp {
    public static void main(String[] args) {
        MyArrayList<Object> massiv = new MyArrayList<>();
        massiv.add(1);
        massiv.add(2);
        massiv.add("папа");
        for(int i=0;i<massiv.size();i++)
        System.out.println(massiv.get(i));

    }
}
