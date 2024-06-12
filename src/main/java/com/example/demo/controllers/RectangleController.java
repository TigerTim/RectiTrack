package com.example.demo.controllers;

// import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.Rectangle;
import com.example.demo.models.RectangleRepository;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class RectangleController {  
    
    // Get rectangles from DB
    @Autowired
    private RectangleRepository rectangleRepo;
    // Purpose of "rectangleRepo": save data and find data from DB

    
    // NOTE: Files in thymeleaf  cannot directly connect via localhost
    // => These files must be connected indirectly via @RequestMapping (@GetMapping is a specified type of generic @RequestMapping)
    @GetMapping("/")      // this means blank (ie = leave the parameter blank ("")
    // If no path specify, computer will automatically fill the "/" into the path
    public String getAllRectangles(Model model){
        System.out.println("Getting all rectangles");   // testing purpose

        List<Rectangle> rectangle = rectangleRepo.findAll();      // "findAll() method = SELECT * FROM rectangle (in SQL)"
        // Summary: Get rectangles from DB and "rectangleRepo" connects DB for me and grab all rectangles and put in "rec" variable below
        // When open DemoApp initially => see no rectangle b/c there is no rectangle to display

        // end of database call
        model.addAttribute("rec", rectangle);      // adding new attribute to Model obj
        // "rec" is attribute's name (key) and rectangle is attribute's value 
        // rectangle here is a list of rectangles
        return "mainPage";
    }

    // Navigate from mainPage to user input page
    @GetMapping("/rec/add")
    public String getAddPage() {
        return "addRec";
    }
    

    // @GetMapping("/users/view/{uid}")
    // public String getUser(Model model, @PathVariable int uid) {
    //     System.out.println("Get User number " + uid);
    //     // User user = userRepo.findById(uid);
    //     // model.addAttribute("us", user);
    //     return "showUser";
    // }

    // The rectangle must exist on mainpage so that when click rec name => navigate to the individual page
    @GetMapping("/view/{name}")
    public String getRec(Model model, @PathVariable String name) {
        System.out.println("Get rectangle name " + name);
        Rectangle rectangle = rectangleRepo.findByName(name);   // modify the RectangleRepo file to get the Rectangle obj instead of List<Rectangle>
        model.addAttribute("rec", rectangle);
        return "showRec";
    }

    // For backend server
    // Data coming from the input form would be a PostMapping (ie being sent to here) (After click SEND)
    @PostMapping("/rec/add")
    public String addRec(@RequestParam Map<String, String> newRec, Model model, HttpServletResponse response) {
        // "HttpServletResponse" will help me w/ the response that Im going to give in my server
        System.out.println("ADD RECTANGLE");    // testing purpose

        // Getting user input 
        String newName = newRec.get("name");    // "newRec" is a map => use "get" method 
        // => grab "newName" from the "name" attribute has value "name" (grab its value and assign to "newName")
        String newWidthStr = newRec.get("width");
        String newHeightStr = newRec.get("height");
        String newColor = newRec.get("color");


        // Check if any fields are left empty 
        if (newName == null || newWidthStr == null ||  newHeightStr == null || newColor == null || 
            newName.isEmpty() || newWidthStr.isEmpty() || newHeightStr.isEmpty() || newColor.isEmpty()) {
            System.out.println("INVALID INPUT");
            model.addAttribute("error1", "All fields must be filled");
            return "addRec";
        }

        // NOTE: Eventho input values are #, they will be coming as a STRING (all communications happen on the web are STRING)
        // convert the value to integer (ie string -> int)
        int newWidth = Integer.parseInt(newWidthStr);
        int newHeight = Integer.parseInt(newHeightStr);

        // Check if enter valid width or height (reach here => All fields are filled)
        if (newWidth <= 0 || newHeight <= 0) {
            System.out.println("INVALID WIDTH OR HEIGHT");
            model.addAttribute("error2", "Please enter valid width or height");
            return "addRec";
        }

        rectangleRepo.save(new Rectangle(newName, newWidth, newHeight, newColor));  // save a new rectangle into DB
        // rectangleRepo will do the INSERT command into the DB
        // This allows us NOT to write any SQL at all (ie dont have to use SQL at all)
        
        response.setStatus(201);    // = response.setStatus(HttpServletResponse.SC_CREATED)
        // 201 is a HTTP status message (server always returns a message for every request) => This means we just create a new obj 
        
        model.addAttribute("successMessage", "Successfully Added Rectangle");
        return "addRec";   // When click SEND => values are clear and remain on the same user input page
    }
    // data on DB will persist even when I do many pushes or restart the application

}