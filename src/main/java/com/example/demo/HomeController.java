package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    ToDoRepository toDoRepository;

    @RequestMapping("/")
    public String listToDo(Model model){
        model.addAttribute("tasks", toDoRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String toDoForm(Model model){
        model.addAttribute("task", new ToDo());
        return "toDoform";
    }

    @PostMapping("/process")
    public String processForm(@Valid ToDo task,
        BindingResult result){
        if (result.hasErrors()){
            return "toDoform";
        }
        toDoRepository.save(task);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showTask(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("task", toDoRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateTask(@PathVariable("id") long id,
              Model model){
        model.addAttribute("task",toDoRepository.findById(id).get());
        return "toDoform";
    }

    @RequestMapping("/delete/{id}")
    public String delTask(@PathVariable("id") long id){
        toDoRepository.deleteById(id);
        return "redirect:/";
    }
}
