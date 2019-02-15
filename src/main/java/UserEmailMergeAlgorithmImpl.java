import exception.MergeInterraUsersException;
import model.PairUserMail;
import model.UserEmail;

import java.util.*;

public class UserEmailMergeAlgorithmImpl implements UserEmailMergeAlgorithm {

    private Map<String,String> userEmailsMap = new HashMap<>();
    private Map<String,String> mergeRule = new HashMap<>();


    @Override
    public ArrayList<UserEmail> merge(ArrayList<UserEmail> userEmails) {
        if (userEmails == null || userEmails.isEmpty()){
            throw new MergeInterraUsersException("userEmails can not be empty!");
        }

        List<PairUserMail> usersEmails = prepareDateForMerge(userEmails);

        prepareUserMergeRuleMap(usersEmails);

        startMerge(usersEmails);

        LinkedHashMap<String, String> userAndEmailsConcat = joinEmailsByUser(userEmailsMap);

        return prepareDataToReturnFormat(userAndEmailsConcat);
    }

    private void startMerge(List<PairUserMail> usersEmails) {
        for (PairUserMail pairUserMail : usersEmails){
            userEmailsMap.put(pairUserMail.getEmail(), checkUserForMerge(pairUserMail.getUser(),mergeRule));
        }
    }

    private List<PairUserMail> prepareDateForMerge(ArrayList<UserEmail> userEmails) {
        List<PairUserMail> usersEmails = new ArrayList<>();
        for (UserEmail userEmail : userEmails) {
            for (String email : userEmail.getEmail()){
                usersEmails.add(new PairUserMail(userEmail.getUser(),email));
            }
        }
        return usersEmails;
    }

    private void prepareUserMergeRuleMap(List<PairUserMail> usersEmails) {
        for (PairUserMail pairUserMail :  usersEmails){
            String replacedUser = userEmailsMap.put(pairUserMail.getEmail(), pairUserMail.getUser());
            if (replacedUser!=null && !Objects.equals(replacedUser,pairUserMail.getUser())){
                mergeRule.put(replacedUser,pairUserMail.getUser());
            }
        }
    }

    private LinkedHashMap<String,String> joinEmailsByUser(Map<String, String> emailUserMap) {
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

    private String checkUserForMerge(String userName, Map<String, String> mergeRule){
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
