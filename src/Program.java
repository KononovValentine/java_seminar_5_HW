import java.util.*;

public class Program {
    public static void main(String[] args) {
        System.out.println("Здравствуйте!");
        startProgram();
    }

    static void startProgram() {
        System.out.println("Введите номер программы (1-4), либо введите \"Q\" для выхода.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Программа № ");
        String program = scanner.nextLine();
        if (program.equalsIgnoreCase("q")) {
            System.out.println("До свидания!");
        } else if (program.chars().allMatch(Character::isDigit)) {
            switch (program) {
                case "1" -> ex0();
                case "2" -> ex1();
                case "3" -> ex2();
                case "4" -> ex3();
                default -> {
                    System.out.println("Введен некорректный номер, пожалуйста, попробуйте еще раз.");
                    startProgram();
                }
            }
        } else {
            System.out.println("Ввод некорректен, пожалуйста, попробуйте еще раз.");
            startProgram();
        }
    }

    // Задача 1. Реализуйте структуру телефонной книги с помощью HashMap, учитывая, что 1 человек может иметь несколько
    // телефонов.
    static void ex0() {
        Map<String, ArrayList<Long>> phoneBook = new HashMap<>();
        addNumber("Иванов", 88888888888L, phoneBook);
        addNumber("Петров", 89999999999L, phoneBook);
        addNumber("Иванов", 88888888890L, phoneBook);
        printBook(phoneBook);
        printOnePerson("Иванов", phoneBook);
    }

    // Добавляет номер в книгу
    static void addNumber(String lastName, Long phoneNumber, Map<String, ArrayList<Long>> phoneBook) {
        if (phoneBook.containsKey(lastName)) {
            phoneBook.get(lastName).add(phoneNumber);
        } else {
            ArrayList<Long> list = new ArrayList<>();
            list.add(phoneNumber);
            phoneBook.put(lastName, list);
        }
    }

    // Выводит на печать всю книгу
    static void printBook(Map<String, ArrayList<Long>> phoneBook) {
        for (var entry : phoneBook.entrySet()) {
            StringBuilder phones = new StringBuilder();
            for (Long element : entry.getValue()) {
                phones.append(element).append(", ");
            }
            System.out.printf("%s: %s \n", entry.getKey(), phones.toString());
        }
    }

    // Выводит на печать одну запись по фамилии.
    static void printOnePerson(String lastName, Map<String, ArrayList<Long>> phoneBook) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(lastName).append(": ");
        ArrayList<Long> numbers = phoneBook.get(lastName);
        String str = numbers.toString().replace("[", "").replace("]", "");
        stringBuilder.append(str);
        System.out.println(stringBuilder);
        startProgram();
    }

    // Задача 2. Пусть дан список сотрудников: Иван Иванов, Светлана Петрова, Кристина Белова, Анна Мусина,
    // Анна Крутова, Иван Юрин, Петр Лыков, Павел Чернов, Петр Чернышов, Мария Федорова, Марина Светлова,
    // Мария Савина, Мария Рыкова, Марина Лугова, Анна Владимирова, Иван Мечников, Петр Петин, Иван Ежов.
    // Написать программу, которая найдет и выведет повторяющиеся имена с количеством повторений. Отсортировать по
    // убыванию популярности. Для сортировки использовать TreeMap.
    static void ex1() {
        String[] workers = new String[]{"Иван Иванов", "Светлана Петрова", "Кристина Белова", "Анна Мусина",
                "Анна Крутова", "Иван Юрин", "Петр Лыков", "Павел Чернов", "Петр Чернышов", "Мария Федорова",
                "Марина Светлова", "Мария Савина", "Мария Рыкова", "Марина Лугова", "Анна Владимирова",
                "Иван Мечников", "Петр Петин", "Иван Ежов"};

        System.out.println("Изначальный список: " + Arrays.toString(workers));
        for (int i = 0; i < workers.length; i++) {
            workers[i] = workers[i].split(" ")[0];
        }

        System.out.println("Список имен: " + Arrays.toString(workers));
        TreeMap<String, Integer> treeMap = new TreeMap<>();

        for (String name : workers) {
            if (treeMap.containsKey(name)) {
                treeMap.replace(name, treeMap.get(name), treeMap.get(name) + 1);
            } else {
                treeMap.put(name, 1);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < treeMap.size(); i++) {
            String key = treeMap.keySet().toArray()[i].toString();
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(treeMap.get(key).toString());
            if (i != treeMap.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");

        System.out.println("Количество вхождений каждого имени: " + stringBuilder);
        SortedSet<Map.Entry<String, Integer>> sortedSet = new TreeSet<Map.Entry<String, Integer>>(
                new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o2, Map.Entry<String, Integer> o1) {
                        int res = o1.getValue().compareTo(o2.getValue());
                        return res != 0 ? res : 1;
                    }
                });
        sortedSet.addAll(treeMap.entrySet());
        System.out.println("Отсортированный список: " + sortedSet);
        startProgram();
    }

    // Задача 3. *Реализовать алгоритм пирамидальной сортировки (HeapSort)
    static void ex2() {
        int[] arr = {123, 321, 12, 2, 14, 5, 1233, 23};
        System.out.println("Изначальный массив: " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println("Отсортированный массив: " + Arrays.toString(arr));
    }

    // пирамидальная сортировка
    private static void heapSort(int[] arr) {
        int length = arr.length;

        // переформирует массив
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, length);
        }

        // меняет последний элемент местами с максимальным
        for (int i = length - 1; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            heapify(arr, 0, i);
        }
    }

    // сравнение потомков с родителем
    private static void heapify(int[] arr, int i, int length) {
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        int largest = i;

        // проверяет левого потомка
        if (l < length && arr[l] > arr[largest]) {
            largest = l;
        }
        // проверяет правого потомка
        if (r < length && arr[r] > arr[largest]) {
            largest = r;
        }
        // если один из потомков больше, меняет его значение на значение родителя
        if (i != largest) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            heapify(arr, largest, length);
        }
        startProgram();
    }

    // Задача 4. **На шахматной доске расставить 8 ферзей так, чтобы они не били друг друга.
    static void ex3() {
        chess();
        startProgram();
    }

    static String[][] field = new String[8][8];
    static int[] chessboard = {0, 0, 0, 0, 0, 0, 0, 0};
    static int index = 0;
    static int version = 1;

    // Проверяет решение, если правильное выводит в терминал, если нет, прибавляет индекс
    static void chess() {

        do {
            if (checking()) {
                if (index == 7) {
                    for (int i = 0; i < field.length; i++) {
                        for (int j = 0; j < field[i].length; j++) {
                            field[i][j] = "+";
                        }
                    }

                    System.out.println();
                    field[0][chessboard[0]] = "@";
                    field[1][chessboard[1]] = "@";
                    field[2][chessboard[2]] = "@";
                    field[3][chessboard[3]] = "@";
                    field[4][chessboard[4]] = "@";
                    field[5][chessboard[5]] = "@";
                    field[6][chessboard[6]] = "@";
                    field[7][chessboard[7]] = "@";
                    System.out.println(version++ + " [0]=" + chessboard[0] + " [1]=" + chessboard[1] +
                            " [2]=" + chessboard[2] + " [3]=" + chessboard[3] + " [4]=" + chessboard[4] +
                            " [5]=" + chessboard[5] + " [6]=" + chessboard[6] + " [7]=" + chessboard[7]);
                    chessboard[index]++;
                    for (int i = 0; i < field.length; i++) {
                        System.out.println(Arrays.toString(field[i]));
                    }
                } else {
                    index++;
                }
            } else {
                chessboard[index]++;
            }
        } while (chessboard[0] < 8);
    }

    // Проверяет доступность постановки ферзя на поле
    static boolean checking() {
        int i;

        if (index == 0) {
            return true;
        }

        if (chessboard[index] > 7) {
            chessboard[index] = 0;
            index--;
            return false;
        }

        for (i = 0; i < index; i++) {
            if ((chessboard[index] == chessboard[i]) | ((Math.abs(chessboard[index] - chessboard[i])) == (index - i))) {
                return false;
            }
        }
        return true;
    }
}