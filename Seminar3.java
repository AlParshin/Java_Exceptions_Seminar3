/*Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол
Форматы данных:
фамилия, имя, отчество - строки
дата_рождения - строка формата dd.mm.yyyy
номер_телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.
Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.*/

package Java_Exceptions.Seminar_3;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Seminar3 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in, "cp866");
        while (true) {
            System.out.println(
                    "Введите строку в формате: 'Фамилия Имя Отчество датарождения(dd.mm.yyyy) номертелефона(целое число без знаков) пол'");
            String data = scanner.nextLine();
            int spaces = data.length() - data.replace(" ", "").length();
            while (spaces != 5) {
                System.out.println("Вы ввели строку в неверном формате! Повторите ввод!");
                System.out.println(
                        "Верный формат: 'Фамилия Имя Отчество датарождения(dd.mm.yyyy) номертелефона(целое число без знаков) пол'");
                data = scanner.nextLine();
                spaces = data.length() - data.replace(" ", "").length();
            }
            String[] words = data.split(" ");
            isDateValid(words[3]);
            checkPhoneFormat(words);
            System.out.println("Это строка будет добавлена в файл:");
            System.out.println(Arrays.toString(words));
            StringBuilder sb = new StringBuilder();
            sb.append(words[0]);
            sb.append(".txt");
            System.out.println(sb);
            FileWriter writer = new FileWriter("" + sb, true);
            writer.write(data + "\n");
            writer.close();
        }
    }

    // Проверим, что телефон записан в корректном формате (89*********) и в нем нет
    // символов:
    public static void checkPhoneFormat(String[] w) {
        boolean isOnlyDigits = true;
        for (int i = 0; i < w[4].length() && isOnlyDigits; i++) {
            if (!Character.isDigit(w[4].charAt(i))) {
                isOnlyDigits = false;
            }
        }
        if (!isOnlyDigits)
            throw new RuntimeException("В телефоне присутствуют не только цифры!");
        if (w[4].length() != 11)
            throw new RuntimeException("Неверное количество символов в номере телефона! Введите в формате 89*********");
    }

    // Проверим, валидная ли дата рождения введена:
    public static void isDateValid(String date) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        myFormat.setLenient(false);
        try {
            myFormat.parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Нет такой даты в календаре! Введите корректную дату!");
        }
    }
}