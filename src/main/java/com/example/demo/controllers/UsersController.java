package com.example.demo.controllers;

// import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.User;
import com.example.demo.models.UserRepository;

// import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class UsersController {  
    
    // Get users from DB
    @Autowired
    private UserRepository userRepo;
    // Purpose of "userRepo": save data and find data from DB

    // enter the endpt below => return showAll => Print all users' attributes
    @GetMapping("/users/view")      // endpoint
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        // get all users from database

        // these users above are added to a list manually
        // List <User> users = new ArrayList<>();
        // users.add(new User("Alice","123",25));
        // users.add(new User("Bobby","456",6));
        // users.add(new User("Charlie,","789",7));
        // users.add(new User("David","101",8));
        // users.add(new User("Steve","112",19));

        // Other way: 
        List<User> users = userRepo.findAll();      // "findAll() method = SELECT * FROM users (in SQL)"
        // Summary: Get users from DB and "userRepo" connects DB for me and grab all users and put in "us" variable below
        // When open DemoApp => only see USERS b/c there is no users to display

        // end of database call
        model.addAttribute("us", users);    // "us" is a variable with database name "users"
        return "showAll";
    }

    // @GetMapping("/users/view/{uid}")
    // public String getUser(Model model, @PathVariable int uid) {
    //     System.out.println("Get User number " + uid);
    //     // User user = userRepo.findById(uid);
    //     // model.addAttribute("us", user);
    //     return "showUser";
    // }
    

    // For backend server
    // Data coming from the input form would be a PostMapping (ie being sent to here)
    @PostMapping("/users/add")       // endpoint
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {    
        // "HttpServletResponse" will help me w/ the response that Im going to give in my server
        
        //TODO: process POST request
        
        System.out.println("ADD USER");     // print out what Im going to do in this endpt
        String newName = newuser.get("name");   // "newuser" is a map => use "get" method 
        // => grab "newName" from the "name" attribute has value "name" (grab its value and assign to "newName")
        
        int newWidth = Integer.parseInt(newuser.get("width"));

        // values are # but it will be coming as a STRING (all communications happen on the web are STRING)
        int newHeight = Integer.parseInt(newuser.get("height"));    
        // convert the value to integer (which is string -> int)

        String newColor = newuser.get("color");
        
        userRepo.save(new User(newName, newWidth, newHeight, newColor));      // save a new user into DB
        // userRepo will do the INSERT command into the DB
        // This allows us NOT to write any SQL at all (ie dont have to use SQL at all)

        response.setStatus(201);    // 201 is INT and this means we just create a new obj

        return "addedUser";   // return a file called "addedUser" (ie go to the file and run it)
        // dont have to return "users/addedUser" as in video b/c there is no "users" folder 
    }
    
    // data on DB will persist even when I do many pushes or restart the application
}
