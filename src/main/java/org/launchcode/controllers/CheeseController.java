package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.CheeseData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute Cheese newCheese) {
        CheeseData.add(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            CheeseData.remove(cheeseId);
        }

        return "redirect:";
    }

    //edit get
    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable("cheeseId") int cheeseId){

        Cheese foundCheese = CheeseData.getById(cheeseId);
        model.addAttribute("cheese", foundCheese);
        return "cheese/edit";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable("cheeseId") int cheeseId, @RequestParam("name") String name,
                                  @RequestParam("description") String description){

        Cheese cheese = CheeseData.getById(cheeseId);

        if((null == name) || name.equals("")){
            model.addAttribute("cheese", cheese);
            model.addAttribute("error", "You should actually provide a name");
            return "cheese/edit";
        } else {

            cheese.setName(name);
            cheese.setDescription(description);

            //CheeseData.remove(cheeseId);
            //CheeseData.add(cheese);

            model.addAttribute("message", "Cheese updated");
            return "redirect:..";
        }


    }

}
