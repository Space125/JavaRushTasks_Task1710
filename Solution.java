package com.javarush.task.task17.task1710;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* 
CRUD
CrUD - Create, Update, Delete.

Программа запускается с одним из следующих наборов параметров:
-c name sex bd
-u id name sex bd
-d id
-i id

Значения параметров:
name - имя, String
sex - пол, "м" или "ж", одна буква
bd - дата рождения в следующем формате 15/04/1990
-c - добавляет человека с заданными параметрами в конец allPeople, выводит id (index) на экран
-u - обновляет данные человека с данным id
-d - производит логическое удаление человека с id, заменяет все его данные на null
-i - выводит на экран информацию о человеке с id: name sex (м/ж) bd (формат 15-Apr-1990)

id соответствует индексу в списке.
Все люди должны храниться в allPeople.
Используйте Locale.ENGLISH в качестве второго параметра для SimpleDateFormat.

Пример параметров:
-c Миронов м 15/04/1990

Пример вывода для параметра -і:
Миронов м 15-Apr-1990


Требования:
1. Класс Solution должен содержать public static поле allPeople типа List.
2. Класс Solution должен содержать статический блок, в котором добавляются два человека в список allPeople.
3. При запуске программы с параметром -с программа должна добавлять человека с заданными параметрами в конец списка allPeople, и выводить id (index) на экран.
4. При запуске программы с параметром -u программа должна обновлять данные человека с заданным id в списке allPeople.
5. При запуске программы с параметром -d программа должна логически удалять человека с заданным id в списке allPeople.
6. При запуске программы с параметром -i программа должна выводить на экран данные о человеке с заданным id по формату указанному в задании.
*/

public class Solution {
    public static List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) {
        //start here - начни тут
        Date oldDate = null;
        Person person;
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        switch (args[0]) {
            case "-d": // Логическое удаление записи из списка, всем значениям записи присваивается null
                allPeople.get(Integer.parseInt(args[1])).setName(null);
                allPeople.get(Integer.parseInt(args[1])).setSex(null);
                allPeople.get(Integer.parseInt(args[1])).setBirthDay(null);
                break;

            case "-i": // Вывод информация о персоне по индексу
                System.out.format("%s %s %s",
                        allPeople.get(Integer.parseInt(args[1])).getName(),
                        allPeople.get(Integer.parseInt(args[1])).getSex().equals(Sex.MALE) ? 'м' : 'ж',
                        newDateFormat.format(allPeople.get(Integer.parseInt(args[1])).getBirthDay()));
                break;

            case "-c": // Добавление записи в allPeople, и возврат индекса который соответствует добавленной записи
                try {
                    oldDate = oldDateFormat.parse(args[3]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                person = (("м").equals(args[2])) ? Person.createMale(args[1], oldDate) : Person.createFemale(args[1], oldDate);
                allPeople.add(person);
                System.out.println(allPeople.indexOf(person));
                break;

            case "-u": // Обновление записи в allPeople по индексу
                try {
                    oldDate = oldDateFormat.parse(args[4]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                allPeople.get(Integer.parseInt(args[1])).setName(args[2]);
                if ("ж".equals(args[3])) {
                    allPeople.get(Integer.parseInt(args[1])).setSex(Sex.FEMALE);
                } else if ("м".equals(args[3])) {
                    allPeople.get(Integer.parseInt(args[1])).setSex(Sex.MALE);
                }
                allPeople.get(Integer.parseInt(args[1])).setBirthDay(oldDate);
                break;
        }
    }
}
