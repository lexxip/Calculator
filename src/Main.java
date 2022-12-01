import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String calc(String input) {
        boolean rome = false;
        int op1, op2, result = 0;
        String [] str = input.split("[+*-/]");
        try {
            op1 = Integer.parseInt(str[0]);
            op2 = Integer.parseInt(str[1]);
        } catch (NumberFormatException e) {
            op1 = Rome.valueOf(str[0]).getTranslation();
            op2 = Rome.valueOf(str[1]).getTranslation();
            rome = true;
        }
        if (op1 < 1 || op1 > 10 || op2 < 1 || op2 > 10) {
            try {
                throw new IOException();
            } catch (IOException e) {
                return (char)27 + "[31m" + "Один или несколько операндов не входят в диапазон {1;10}" + (char)27 + "[39m";
            }
        }
        Pattern pattern = Pattern.compile("[^0-9A-Za-z]");
        Matcher delimiter = pattern.matcher(input);
        delimiter.find();
        String operator = delimiter.group();
        switch (operator) {
            case "/" :
                result = op1 / op2;
                break;
            case "*" :
                result = op1 * op2;
                break;
            case "+" :
                result = op1 + op2;
                break;
            case "-" :
                result = op1 - op2;
        }
        if (rome == true) {
            if (result < 1) {
                try {
                    throw new ArithmeticException();
                } catch (ArithmeticException e) {
                    return (char) 27 + "[31m" + "Результат арифметического выражения не может быть меньше 'I'" + (char) 27 + "[39m";
                }
            }
            return String.valueOf(Rome.values()[result-1]);
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) throws IOException {
        Main calc = new Main();
        String result;
        while (true) {
            System.out.println("Введите математическое выражение, состоящее из двух операндов и одного оператора +,-,/,*");
            System.out.println("Каждый из операндов должен находиться в диапазоне от 1 до 10");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String [] str = input.toUpperCase().split("[+*-/]");
            if (str.length != 2) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println((char)27 + "[31m" + "Вы ввели неправильное количество операндов" + (char)27 + "[39m");
                }
            } else {
                for (int i = 0; i < str.length; i++) {
                    str[i] = str[i].trim();
                }
                boolean flag = false;
                try {
                    Enum.valueOf(Rome.class, str[0]);
                    Enum.valueOf(Rome.class, str[1]);
                    flag = true;
                } catch (IllegalArgumentException e) {

                }
                try {
                    Integer.parseInt(str[0]);
                    Integer.parseInt(str[1]);
                    flag = true;
                } catch (NumberFormatException e) {

                }
                if (flag) {
                    Pattern pattern = Pattern.compile("[^0-9A-Za-z ]");
                    Matcher delimiter = pattern.matcher(input);
                    delimiter.find();
                    result = calc(str[0]+ delimiter.group() +str[1]);
                    System.out.println(result);
                } else {
                    try {
                        throw new IOException();
                    } catch (IOException e) {
                        System.out.println((char)27 + "[31m" + "Операнды не удовлетворяют условиям" + (char)27 + "[39m");
                    }
                }
            }

            String request;
            while (true) {
                System.out.print("Продолжить выполнение программы? (y/n):");
                request = in.next().trim().toLowerCase();
                if (request.length() == 1 && "yn".contains(request)) {
                    break;
                }
            }
            if ("n".contains(request)) {
                break;
            }
        }
        // Main calc = new Main();
        // String result = calc("2 + 5");
        // System.out.println(result);
    }
}