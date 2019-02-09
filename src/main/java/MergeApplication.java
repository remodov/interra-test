
/*
Требуется написать работающий код, решающий задачу, и приложить инструкцию, как код собрать и запустить.
Также надо написать unittest-ы и сделать возможность получать входные данные для задачи из stdin в каком-нибудь разумном формате.
Задачу реализовать на Java (достаточно как консольное JAVA приложение).

Имеется n пользователей, каждому из них соответствует список email-ов (всего у всех пользователей m email-ов).
Например:
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru
user2 -> foo@gmail.com, ups@pisem.net
user3 -> xyz@pisem.net, vasya@pupkin.com
user4 -> ups@pisem.net, aaa@bbb.ru
user5 -> xyz@pisem.net

Считается, что если у двух пользователей есть общий email, значит это один и тот же пользователь. Требуется построить
и реализовать алгоритм, выполняющий слияние пользователей. На выходе должен быть список пользователей с их email-ами (такой же как на входе).
В качестве имени объединенного пользователя можно брать любое из исходных имен. Список email-ов пользователя должен содержать только уникальные email-ы.
Параметры n и m произвольные, длина конкретного списка email-ов никак не ограничена.
Требуется, чтобы асимптотическое время работы полученного решения было линейным, или близким к линейному.

Возможный ответ на задачу в указанном примере:
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru
user3 -> xyz@pisem.net, vasya@pupkin.com
* */

import model.UserEmail;

import java.io.BufferedReader;
import java.util.*;

public class MergeApplication {

    private final ArrayList<UserEmail> userListInput = new ArrayList<>();
    private final UserEmailMergeAlgorithm alg;

    public MergeApplication(UserEmailMergeAlgorithm alg) {
        this.alg = alg;
    }

    public void start(){
        inputValue();
        merge();
    }

    private  void inputValue(){
        System.out.println("==============================================");
        System.out.println("Enter User: user:asas@email.ru,asasas@email.ru");
        System.out.println("Enter 's' for start merge");

        Scanner scanner = new Scanner(System.in);
        String readString = "";

        while(!readString.equals("s")) {

            if (scanner.hasNextLine()) {
                readString = scanner.nextLine();

                if (!readString.contains(":")){
                   continue;
                }

                if (readString.contains(":")){
                    String[] split = readString.split(":");

                    if (split.length != 2){
                       continue;
                    }
                }

                String[] split = readString.split(":");

                String user = split[0];
                String[] emails = split[1].split(",");

                userListInput.add(new UserEmail(user,Arrays.asList(emails)));
            }
        }
    }

    private void merge() {
        ArrayList<UserEmail> merge = alg.merge(userListInput);
        for (UserEmail userEmail : merge) {
            System.out.println(userEmail.getUser() + ":" + userEmail.getEmail().toString());
        }
    }


    public static void main(String[] args) {
        MergeApplication application = new MergeApplication(new UserEmailMergeAlgorithmImpl());
        application.start();
    }

}