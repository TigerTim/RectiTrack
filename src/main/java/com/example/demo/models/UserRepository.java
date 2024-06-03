package com.example.demo.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


// Note that this is an interface not a class as usual
public interface UserRepository extends JpaRepository<User, Integer> {     
    // JpaRepository is an interface => an inteface (UserRepository) extends another interface (which is "JpaRepository")
    // JpaRepo needs to be attached to a class that already defined as a table 
    // => In bracket, "User" class and second parameter is "id" type (which is of type "int")
    // Now, for ex, If write a code to enter new user to database (DB) => repository will automatically translate that code into SQL to send it over DB
    
    List<User> findBySize(int size);    // "findBy" is a standard method and the other phrase would be what I look for ("Size" in this case) 
    List<User> findByNameAndPassword(String name, String password);     // notice the camel case
    // Note: the parameters' name must match the variable in "User" class in User.java file
}
