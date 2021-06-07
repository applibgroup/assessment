package Utils;

import ohos.data.rdb.RdbStore;
import ohos.data.rdb.ValuesBucket;

public class DatabaseMethods
{
    private RdbStore db;
    private String TABLENAME = "test";

    public void insertData(String firstName, String lastName, String email, String password, String mobile, int gender)
    {
        ValuesBucket valuesBucket =  new ValuesBucket();
        valuesBucket.putString("firstname", firstName);
        valuesBucket.putString("lastname", lastName);
        valuesBucket.putString("email", email);
        valuesBucket.putString("password", password);
        valuesBucket.putString("mobile", mobile);
        valuesBucket.putInteger("gender", gender);
        db.insert(TABLENAME, valuesBucket);
    }
}
