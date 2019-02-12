import exception.MergeInterraUsersException;
import model.UserEmail;

import java.util.*;

public class UserEmailMergeAlgorithmImpl implements UserEmailMergeAlgorithm {

    class PairUserMail{

        private final String user;
        private final String email;

        PairUserMail(String user, String email) {
            this.user = user;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getUser() {
            return user;
        }
    }

    @Override
    public ArrayList<UserEmail> merge(ArrayList<UserEmail> userEmails) {
        if (userEmails == null || userEmails.isEmpty()){
            throw new MergeInterraUsersException("userEmails can not be empty!");
        }

        List<PairUserMail> usersEmails = new ArrayList<>();

        for (UserEmail userEmail : userEmails){
            for (String email : userEmail.getEmail()){
                usersEmails.add(new PairUserMail(userEmail.getUser(),email));
            }
        }

        Collections.sort(usersEmails, Comparator.comparing(o -> o.email+o.user));

        HashMap<String,String> userEmailsMap = new HashMap<>();

        String currUserForEmail = usersEmails.get(0).user;
        String currEmail = usersEmails.get(0).email;

        for (PairUserMail pairUserMail :  usersEmails){
            String put = userEmailsMap.put(pairUserMail.email, pairUserMail.user);

            if (put!=null){
                System.out.println("Merge users->" + put + "->" + pairUserMail.user );
                mergeRule.put(put,pairUserMail.user);
            }
        }

        LinkedHashMap<String, String> stringStringLinkedHashMap = joinEmailsByUser(userEmailsMap);

        ArrayList<UserEmail> userEmails1 = prepareDataToReturnFormat(stringStringLinkedHashMap);

        ArrayList<UserEmail> userEmails2 = new ArrayList<>();
        for (UserEmail userEmail : userEmails1){
            String s = checkUserForMerge(userEmail.getUser());
            userEmails2.add(new UserEmail(s,userEmail.getEmail()));

        }

        return userEmails2;
    }

    Map<String,String> mergeRule = new HashMap<>();

    private LinkedHashMap<String,String> joinEmailsByUser(HashMap<String, String> emailUserMap) {
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

    private String checkUserForMerge(String userName){
        String mergeUser = "";
        String currUser = userName;
       while (mergeUser != null){
           mergeUser = mergeRule.get(currUser);
           if (mergeUser != null){
               currUser = mergeUser;
           }
       }

       return currUser;
    }

    private ArrayList<UserEmail> prepareDataToReturnFormat(HashMap<String, String> userEmailsMap) {
        ArrayList<UserEmail> resultMerge = new ArrayList<>();
        userEmailsMap.forEach((user,emails) -> resultMerge.add(new UserEmail(user, Arrays.asList(emails.split(",")))));
        return resultMerge;
    }
}
