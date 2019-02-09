import exception.MergeInterraUsersException;
import model.UserEmail;

import java.util.*;

public class UserEmailMergeAlgorithmImpl implements UserEmailMergeAlgorithm {

    @Override
    public ArrayList<UserEmail> merge(ArrayList<UserEmail> userEmails) {
        if (userEmails == null || userEmails.isEmpty()){
            throw new MergeInterraUsersException("userEmails can not be empty!");
        }

        Collections.sort(userEmails, (o1, o2) -> o2.getEmail().size() - o1.getEmail().size());
        LinkedHashMap<String, String> userEmailList = distinctEmailsAndMergeUsers(userEmails);
        LinkedHashMap<String, String> userEmailsList = joinEmailsByUser(userEmailList);
        return prepareDataToReturnFormat(userEmailsList);
    }

    /**
     * Фильтруем все адреса email, если происходит перезапись по ключу, то мы нашли пользователь для слияния
     * и дальше выполняем под ним следующие записи в карту
     * 0(n) сложность, делаем один обход
     * @return LinkedHashMap<String, String> - формат: email - пользователя
     */
    private LinkedHashMap<String, String> distinctEmailsAndMergeUsers(ArrayList<UserEmail> userEmails) {
        LinkedHashMap<String,String> emailUserMap = new LinkedHashMap<>();
        for (UserEmail userEmail : userEmails){
            String user = userEmail.getUser();
            for (String email : userEmail.getEmail()) {
                String userForMerge = emailUserMap.put(email, user);
                if (userForMerge != null) {
                    emailUserMap.put(email, userForMerge);
                    user = userForMerge;
                }
            }
        }
        return emailUserMap;
    }

    /**
     * Проходим и собираем все email к одному пользователю
     * 0(n) сложность, делаем один обход
     * @param emailUserMap - формат email - пользователя
     * @return LinkedHashMap<String, String> - формат: пользователя - email1,email2,...
     */
    private LinkedHashMap<String,String> joinEmailsByUser(LinkedHashMap<String, String> emailUserMap) {
        LinkedHashMap<String,String> userEmailsMap = new LinkedHashMap<>();
        for (Map.Entry<String,String> entry: emailUserMap.entrySet()) {
            String userEmail = userEmailsMap.put(entry.getValue(), entry.getKey());
            if (userEmail != null){
                String s = userEmailsMap.get(entry.getValue());
                s = s + "," + userEmail;
                userEmailsMap.put(entry.getValue(),s);
            }
        }
        return userEmailsMap;
    }

    /**
     * Тут делаем один обход, чтобы подоготовить к выходному формату
     * @param userEmailsMap - формат: пользователя - email1,email2,...
     * @return ArrayList<UserEmail>  - список пользователей
     */
    private ArrayList<UserEmail> prepareDataToReturnFormat(LinkedHashMap<String, String> userEmailsMap) {
        ArrayList<UserEmail> resultMerge = new ArrayList<>();
        userEmailsMap.forEach((user,emails) -> resultMerge.add(new UserEmail(user, Arrays.asList(emails.split(",")))));
        return resultMerge;
    }
}
