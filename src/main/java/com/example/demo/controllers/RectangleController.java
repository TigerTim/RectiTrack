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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class RectangleController {  
    
    // Get rectangles from DB
    @Autowired
    private RectangleRepository rectangleRepo;
    // Purpose of "rectangleRepo": save data and find data from DB

    
    // NOTE: Files in thymeleaf cannot directly connect via localhost
    // => These files must be connected indirectly via @RequestMapping (@GetMapping is a specified type of generic @RequestMapping)
//     @GetMapping("/")      // this means blank (ie = leave the parameter blank ("")
//     // If no path specify, computer will automatically fill the "/" into the path
//     public String getAllRectangles(Model model){
//         System.out.println("Getting all rectangles");   // testing purpose

//         List<Rectangle> rectangle = rectangleRepo.findAll();      // "findAll() method = SELECT * FROM rectangle (in SQL)"
//         // Summary: Get rectangles from DB and "rectangleRepo" connects DB for me and grab all rectangles and put in "rec" variable below
//         // When open DemoApp initially => see no rectangle b/c there is no rectangle to display

//         // end of database call
//         model.addAttribute("rec", rectangle);      // adding new attribute to Model obj
//         // "rec" is attribute's name (key) and rectangle is attribute's value 
//         // rectangle here is a list of rectangles
//         return "mainPage";
//         // Cannot use return "redirect:/" here
//         // NOTE: Since redirect:/ will send a new GET request to the path "/" => Call @GetMapping("/") (ie invoke func above)
//         // => Create an infinite loop 
//         // return "redirect:/" only be used when there is a base to redirect to sth (in which the func return a non-redirect path)
//     }

//     // Navigate from mainPage to user input page
//     @GetMapping("/rec/add")
//     public String getAddPage() {
//         return "addRec";
//     }

//     // The rectangle must exist on mainpage so that when click rec name => navigate to the individual page
//     @GetMapping("/view/{name}")
//     public String getRec(Model model, @PathVariable String name) {
//         System.out.println("Get rectangle name " + name);
//         Rectangle rectangle = rectangleRepo.findByName(name);   // modify the RectangleRepo file to get the Rectangle obj instead of List<Rectangle>
//         model.addAttribute("rec", rectangle);
//         return "showRec";
//     }

//     // For backend server
//     // Data coming from the input form would be a PostMapping (ie being sent to here) (After click SEND)
//     // PostMapping cannot be accessed directly from the link ie URL (only GetMapping can be accessed)
//     @PostMapping("/rec/add")
//     public String addRec(@RequestParam Map<String, String> newRec, Model model, HttpServletResponse response) {
//         // "HttpServletResponse" will help me w/ the response that Im going to give in my server
//         System.out.println("ADD RECTANGLE");    // testing purpose

//         // Getting user input 
//         String newName = newRec.get("name");    // "newRec" is a map => use "get" method 
//         // => grab "newName" from the "name" attribute has value "name" (grab its value and assign to "newName")
//         String newWidthStr = newRec.get("width");
//         String newHeightStr = newRec.get("height");
//         String newColor = newRec.get("color");


//         // Check if any fields are left empty 
//         // 1st check: if user dont fill anything (including whitespace) 
//         // 2nd check: if user fill whitespace (ie newName != null) (but not letters or #)
//         if (newName == null || (newName != null && newName.trim().equals("")) || newWidthStr == null ||  newHeightStr == null || newColor == null || 
//             newName.isEmpty() || newWidthStr.isEmpty() || newHeightStr.isEmpty() || newColor.isEmpty()) {
//             System.out.println("INVALID INPUT");
//             model.addAttribute("error1", "All fields must be filled");
//             return "addRec";    
//             // return "redirect:/rec/add";      
//             // this can be used the same as above because the addRec doesnt change anything and we want to page remain its original state (ie no input value)
//             // when user enter (in)correctly => page will remove all input value in both cases
//             // But the error messange wont be displayed b/c "redirect" sends a new GET request to specifed path (URL) and it doesnt have access to the Model attr 
//             // Model attr in one request is not automatically carried to the next request 
//             // In this case, add the attribute to current request's model. When redirect, a new request is made, and this model attribute is lost
//             // => Cannot displayed any error message in model attr
//         }

//         // Check if rectangle name already exists 
//         Rectangle existedRec = rectangleRepo.findByName(newName);    
//         // Reason why not use String existedRec = rectangleRepo.findByName(newName).getName() here
//         // b/c if cannot find matching name => rectangleRepo.findByName(newName) return null
//         // and call getter on a null obj => throw a NullPointerException 
//         // Thats why need to check to ensure it finds a matching name (ie != null) to proceed the comparison using getter
//         // If == null => skip the cond b/c this name is new to DB => Valid

//         // use getter "getName()" because "name" attr is private and inaccessible from outside
//         if (existedRec != null && newName.trim().equals(existedRec.getName())) {    
//             // trim() only remove space b4 and after the string (Ex: "   Hello  " => "Hello")
//             // But cannot remove space within each character (Ex: "  He l lo  " => "He l lo")
//             System.out.println("NAME ALREADY EXISTS");
//             model.addAttribute("error2", "Name already exists");
//             return "addRec";
//             // return "redirect:/rec/add";      // check reason above
//         }

//         // NOTE: Eventho input values are #, they will be coming as a STRING (all communications happen on the web are STRING)
//         // convert the value to integer (ie string -> int)
//         int newWidth = Integer.parseInt(newWidthStr);
//         int newHeight = Integer.parseInt(newHeightStr);

//         // Check if enter valid width or height (reach here => All fields are filled)
//         if (newWidth <= 0 || newHeight <= 0) {
//             System.out.println("INVALID WIDTH OR HEIGHT");
//             model.addAttribute("error3", "Enter valid width or height");
//             return "addRec";
//             // return "redirect:/rec/add";      // check reason above
//         }

//         rectangleRepo.save(new Rectangle(newName, newWidth, newHeight, newColor));  // save a new rectangle into DB
//         // rectangleRepo will do the INSERT command into the DB
//         // This allows us NOT to write any SQL at all (ie dont have to use SQL at all)
        
//         response.setStatus(201);    // = response.setStatus(HttpServletResponse.SC_CREATED)
//         // 201 is a HTTP status message (server always returns a message for every request) => This means we just create a new obj 
        
//         model.addAttribute("successMessage", "Successfully Added Rectangle");
//         return "addRec";   // When click SEND => values are clear and remain on the same user input page
//         // return "redirect:/rec/add";      // check reason above
//     }
//     // data on DB will persist even when I do many pushes or restart the application


//     // Delete row in mainPage
//     @PostMapping("/rec/delete/{name}")
//     public String deleteRec(@PathVariable String name, Model model) {
//         // PathVariable takes var to do some findBy search
//         Rectangle rectangle = rectangleRepo.findByName(name);   // find rectangle has matching name
//         rectangleRepo.delete(rectangle);       // delete from DB
//         List<Rectangle> recs = rectangleRepo.findAll();
//         model.addAttribute("recs", recs);
//         // no need to add to model unless there is the deleted rec needs to be shown on the page
//         // return "redirect:/";    // not return "mainPage";
//         return "mainPage";
//         // NOTE: redirect to a path (or URL) not a file => redirect:addRec => False (but should be "redirect:/rec/add" instead)
//         // HTTP Redirect: This sends a 302 HTTP response to the client, instructing the browser to make a new GET request to the root URL ("/").
//         // New Request: A new request to "/" will be made, triggering (ie invoking) your controller method mapped to @GetMapping("/") (ie getAllRectangles func)
//         // => This returns a mainPage
//         // => User sees the updated list of rectangles w/o needing to manually refresh the page or perform any additional actions.

//         // If return "mainPage" here => when click DELETE => navigate to the URL ("/rec/delete/{name}") => Whitelable error page
//         // What we need is remain the same page, but need to refresh the page (ie to update the deleted data) => use return "redirect:/";

//         // Summary: 
//         // return "mainPage" directly from the delete method will not refresh the URL and might not have the updated model data.
//         // returning "redirect:/" ensures the browser's URL is updated and the main page is requested afresh

//     }


//     // Update rec from detail displayed page
//     @PostMapping("/rec/update/{name}")
//     public String updateRec(@PathVariable String name, @RequestParam Map<String, String> getRec, Model model) {

//         String newWidthStr = getRec.get("newWidth");
//         String newHeightStr = getRec.get("newHeight");
//         String newColor = getRec.get("newColor");

//         // Get the existing rectangle from repository (Ofc it must exist)
//         Rectangle rectangle = rectangleRepo.findByName(name);
        
//         if (newWidthStr == null || newHeightStr == null || newWidthStr.isEmpty() || newHeightStr.isEmpty()) {
//             model.addAttribute("error", "Input fields must be filled");
//             model.addAttribute("rec", rectangle);      // add to model to be shown in showRec
//             return "showRec";
//         }

//         // Validate and parse newWidth and newHeight
//         int newWidth = Integer.parseInt(newWidthStr);
//         int newHeight = Integer.parseInt(newHeightStr);

//         // Check if newWidth or newHeight are less than or equal to 0
//         if (newWidth <= 0 || newHeight <= 0) {
//             model.addAttribute("error", "Numbers must be positive");
//             model.addAttribute("rec", rectangle);       // add to model to be shown in showRec
//             return "showRec";
//         }

//         // Update and rectangle attributes to DB
//         rectangle.setWidth(newWidth);
//         rectangle.setHeight(newHeight);
//         rectangle.setColor(newColor);
//         rectangleRepo.save(rectangle);

//         // Add success message to model
//         model.addAttribute("success", "Rectangle updated successfully");

//         // Always add the rectangle to model to display in showRec.html
//         model.addAttribute("rec", rectangle);
        
//         // Return the showRec view to display the updated rectangle details and messages
//         return "showRec";
//     }

    @GetMapping("/")
    public String getMainPage(Model model) {
        List<Rectangle> rectangles = rectangleRepo.findAll();
        model.addAttribute("rectangles", rectangles);
        return "mainPage";
    }

    @GetMapping("/return")
    public String returnMainPage(Model model) {
        List<Rectangle> rectangles = rectangleRepo.findAll();
        model.addAttribute("rectangles", rectangles);
        return "mainPage";
    }

    @GetMapping("/rec/add")
    public String getMethodName() {
        return "addRec";
    }

    @PostMapping("/rec/add")
    public String addRectangle(@RequestParam Map<String, String> formData, Model model) {
        String name = formData.get("name");
        String widthStr = formData.get("width");
        String heightStr = formData.get("height");
        String color = formData.get("color");

        if (name.isEmpty() || widthStr.isEmpty() || heightStr.isEmpty()) {
            model.addAttribute("error1", "Please fill all the form");
            return "addRec";
        }

        int width = Integer.parseInt(widthStr);
        int height = Integer.parseInt(heightStr);
        if (width <= 0 || height <= 0) {
            model.addAttribute("error2", "Numbers cannot be negative");
            return "addRec";
        }
        
        // add existing rec name
        if (rectangleRepo.findByName(name) != null) {
            model.addAttribute("error3", "Name exists");
            return "addRec";
        }

        rectangleRepo.save(new Rectangle(name, width, height, color));
        model.addAttribute("success", "Success");
        return "addRec";
    }

    @GetMapping("/view")
    public String viewRec(@RequestParam String name, Model model) {
        model.addAttribute("rec", rectangleRepo.findByName(name));
        return "showRec";
    }

    // @PostMapping("/delete")
    // public String deleteRec(@RequestParam String name, Model model) {
    //     Rectangle rec = rectangleRepo.findByName(name);
    //     rectangleRepo.delete(rec);
    //     model.addAttribute("rectangles", rectangleRepo.findAll());
    //     return "mainPage";
    // }

    @PostMapping("/delete")
    public String postMethodName(@RequestParam String name, Model model) {
        Rectangle rec = rectangleRepo.findByName(name);
        rectangleRepo.delete(rec);
        // model.addAttribute("rectangles", rectangleRepo.findAll());   
        // no need to add attr here b/c redirect will make a NEW req => added attr will not be saved => cannot be used to display
        return "redirect:/mainPage";        // redirect to another url and add attr to model then return the desired page
    }
    
    @GetMapping("/mainPage")
    public String getMethodName(Model model) {
        model.addAttribute("rectangles", rectangleRepo.findAll());
        return "mainPage";
    }

    @PostMapping("/update")
    public String postMethodName(@RequestParam Map<String, String> formData, Model model) {
        Rectangle rectangle = rectangleRepo.findByName(formData.get("name"));
        String newWidthStr = formData.get("newWidth");
        String newHeightStr = formData.get("newHeight");
        String newColor = formData.get("newColor");
        if (newHeightStr.isEmpty() || newWidthStr.isEmpty()) {
            model.addAttribute("error1", "Fill all the form");
            model.addAttribute("rec", rectangle);
            return "showRec";
        }

        int width = Integer.parseInt(newWidthStr);
        int height = Integer.parseInt(newHeightStr);
        if (width <= 0 || height <= 0) {
            model.addAttribute("error2", "Numbers cannot be negative");
            model.addAttribute("rec", rectangle);
            return "showRec";
        }

        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setColor(newColor);
        rectangleRepo.save(rectangle);
        model.addAttribute("rec", rectangle);
        model.addAttribute("success", "Success");
        return "showRec";
    }
    
}
