package com.applibgroup.contactapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

//    @Query("SELECT * FROM user WHERE uid=:id")
//    User getUserwithUID(String id);

    @Insert
    void insertUser(User user);

    @Update
    public void updateUsers(User user);

    @Insert
    void insertUsers(List<User> users);

    @Delete
    void deleteUser(User user);
}
