import exception.MergeInterraUsersException;
import model.UserEmail;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserEmailMergeAlgorithmImplTest {

    private final  UserEmailMergeAlgorithm userEmailMergeAlgorithm = new UserEmailMergeAlgorithmImpl();

    @Test(expected = MergeInterraUsersException.class)
    public void merge_null_list_and_accept_exception() {
        userEmailMergeAlgorithm.merge(null);
    }

    @Test(expected = MergeInterraUsersException.class)
    public void merge_empty_list_and_accept_exception() {
        userEmailMergeAlgorithm.merge(new ArrayList<>());
    }

    @Test
    public void merge_list_with_one_row() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListInput).toArray());
    }

    @Test
    public void merge_list_with_two_rows_diff_users_with_same_emails() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));
        userListInput.add(new UserEmail("user2", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));

        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }

    @Test
    public void merge_list_with_two_rows_diff_users_with_diff_emails() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));
        userListInput.add(new UserEmail("user2", Arrays.asList("lol@mail.ru","xxx2@ya.ru","foo3@gmail.com")));

        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com","xxx2@ya.ru","foo3@gmail.com")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }

    @Test
    public void merge_list_with_one_user_and_many_rows_email() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx2@ya.ru","foo3@gmail.com")));
        userListInput.add(new UserEmail("user1", Arrays.asList("lol2@mail.ru","xxx2@ya.ru","foo3@gmail.com")));
        userListInput.add(new UserEmail("user1", Arrays.asList("lol3@mail.ru","xxx2@ya.ru","foo3@gmail.com")));
        userListInput.add(new UserEmail("user1", Arrays.asList("lol4@mail.ru","xxx2@ya.ru","foo3@gmail.com")));


        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user1", Arrays.asList("foo3@gmail.com,foo@gmail.com,lol2@mail.ru,lol3@mail.ru,lol4@mail.ru,lol@mail.ru,xxx2@ya.ru,xxx@ya.ru")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void merge_list_with_one_user_and_null_email_list_exception() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1", null));
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx2@ya.ru","foo3@gmail.com")));


        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user1", Arrays.asList("foo3@gmail.com,foo@gmail.com,lol2@mail.ru,lol3@mail.ru,lol4@mail.ru,lol@mail.ru,xxx2@ya.ru,xxx@ya.ru")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void merge_list_with_one_user_and_null_user_list_exception() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail(null, Arrays.asList("lol@mail.ru","xxx2@ya.ru","foo3@gmail.com")));
        userListInput.add(new UserEmail("user1", Arrays.asList("lol@mail.ru","xxx2@ya.ru","foo3@gmail.com")));


        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user1", Arrays.asList("foo3@gmail.com,foo@gmail.com,lol2@mail.ru,lol3@mail.ru,lol4@mail.ru,lol@mail.ru,xxx2@ya.ru,xxx@ya.ru")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }


    @Test
    public void merge_list_with_many_user_and_many_emails() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1",Arrays.asList("lol@mail.ru","xxx@ya.ru","foo@gmail.com")));
        userListInput.add(new UserEmail("user2",Arrays.asList("foo@gmail.com","ups@pisem.net")));
        userListInput.add(new UserEmail("user3",Arrays.asList("xyz@pisem.net","vasya@pupkin.com")));
        userListInput.add(new UserEmail("user4",Arrays.asList("ups@pisem.net","aaa@bbb.ru")));
        userListInput.add(new UserEmail("user5",Arrays.asList("xyz@pisem.net")));


        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user1", Arrays.asList("aaa@bbb.ru,foo@gmail.com,lol@mail.ru,ups@pisem.net,xxx@ya.ru")));
        userListExpected.add(new UserEmail("user3", Arrays.asList("xyz@pisem.net","vasya@pupkin.com")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }

    @Test
    public void merge_list_with_many_user_and_many_emails_to_one_user() {
        ArrayList<UserEmail> userListInput = new ArrayList<>();
        userListInput.add(new UserEmail("user1",Arrays.asList("lol@mail.ru")));
        userListInput.add(new UserEmail("user2",Arrays.asList("foo@gmail.com")));
        userListInput.add(new UserEmail("user3",Arrays.asList("lol@mail.ru","foo@gmail.com")));

        ArrayList<UserEmail> userListExpected = new ArrayList<>();
        userListExpected.add(new UserEmail("user3", Arrays.asList("lol@mail.ru","foo@gmail.com")));

        ArrayList<UserEmail> mergeResult = userEmailMergeAlgorithm.merge(userListInput);

        Assert.assertArrayEquals(prepareListForTest(mergeResult).toArray(),prepareListForTest(userListExpected).toArray());
    }

    private List<String> prepareListForTest(ArrayList<UserEmail> prepare){
        ArrayList<String> sortedResult = new ArrayList<>();

        for (UserEmail userEmail : prepare){
            List<String> emails = new ArrayList<>(userEmail.getEmail());
            Collections.sort(emails);
            sortedResult.add(userEmail.getUser() +":"+ String.join(",", emails));
        }
        return sortedResult;
    }

}

