/*
 * My-Wine-Cellar, copyright 2019
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 */

package info.mywinecellar.controller;

import info.mywinecellar.model.Bottle;
import info.mywinecellar.model.Tasted;
import info.mywinecellar.model.Wine;
import info.mywinecellar.nav.Attributes;
import info.mywinecellar.nav.Paths;
import info.mywinecellar.security.model.User;
import info.mywinecellar.ui.BottleUIFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/bottle")
public class BottleController extends AbstractController {

    public BottleController() {
        super();
    }

    @InitBinder("bottle")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String bottleNewGet(@RequestParam Long wineId, Model model, Principal principal) {
        if (principal == null) {
            return Paths.REDIRECT_ROOT;
        }

        Bottle bottle = new Bottle();
        Wine wine = wineService.findById(wineId);
        bottle.setWine(wine);
        model.addAttribute(Attributes.BOTTLE, bottle);
        return Paths.BOTTLE_ADD_EDIT;
    }

    @PostMapping("/new")
    public String bottleNewPost(@Valid Bottle bottle, BindingResult result, Model model,
                                @RequestParam Long wineId, Principal principal,
                                @RequestParam("action") String action) {
        if (principal == null) {
            return Paths.REDIRECT_ROOT;
        }

        if (result.hasErrors()) {
            model.addAttribute(Attributes.BOTTLE, bottle);
            return Paths.BOTTLE_ADD_EDIT;
        } else {
            User user = userService.findByUsername(principal.getName());
            Bottle b = bottleService.findByWine(user.getId(), wineId);
            Tasted tasted = tastedService.findByWine(user.getId(), wineId);

            if (action.equals("save")) {
                if (b == null) {
                    Wine wine = wineService.findById(wineId);
                    bottle.setWine(wine);
                    bottle.setShow(true);
                    bottle.setUser(user);
                    user.getBottles().add(bottle);
                    bottleService.save(bottle);
                } else {
                    b.setNumber(bottle.getNumber());
                    b.setLocation(bottle.getLocation());
                    b.setShow(bottle.getShow());
                    bottleService.save(b);
                }

                if (tasted != null)
                    tastedService.delete(tasted);

                return Paths.REDIRECT_BOTTLE_LIST;
            } else {
                return Paths.REDIRECT_WELCOME;
            }
        }
    }

    @GetMapping("/{bottleId}/edit")
    public String bottleEditGet(@PathVariable Long bottleId, Model model, Principal principal) {
        if (principal == null) {
            return Paths.REDIRECT_ROOT;
        }

        User user = userService.findByUsername(principal.getName());
        Bottle bottle = bottleService.findByUser(user.getId(), bottleId);

        if (bottle == null)
            return Paths.REDIRECT_ROOT;

        model.addAttribute(Attributes.BOTTLE, bottle);
        return Paths.BOTTLE_ADD_EDIT;
    }

    @PostMapping("/{bottleId}/edit")
    public String bottleEditPost(@Valid Bottle bottle, BindingResult result, Model model,
                                 @PathVariable Long bottleId, @RequestParam Long wineId, Principal principal,
                                 @RequestParam("action") String action) {
        if (principal == null) {
            return Paths.REDIRECT_ROOT;
        }

        if (result.hasErrors()) {
            model.addAttribute(Attributes.BOTTLE, bottle);
            return Paths.BOTTLE_ADD_EDIT;
        } else {
            User user = userService.findByUsername(principal.getName());
            Bottle b = bottleService.findByUser(user.getId(), bottleId);

            if (b != null) {
                if (bottle.getNumber() > 0) {
                    b.setNumber(bottle.getNumber());
                    b.setLocation(bottle.getLocation());
                    b.setShow(bottle.getShow());

                    if (action.equals("save")) bottleService.save(b);
                    return Paths.REDIRECT_BOTTLE_LIST;

                } else {
                    user.getBottles().remove(b);
                    bottleService.delete(b);

                    Wine wine = wineService.findById(wineId);
                    Tasted tasted = new Tasted(user, wine);
                    user.getTasted().add(tasted);
                    tastedService.save(tasted);

                    return Paths.REDIRECT_TASTED_LIST;
                }
            }
            return Paths.REDIRECT_ROOT;
        }
    }

    @GetMapping("/list")
    public String bottleListGet(Model model, Principal principal) {
        if (principal == null) {
            return Paths.REDIRECT_ROOT;
        }

        User user = userService.findByUsername(principal.getName());
        model.addAttribute(Attributes.BOTTLES, BottleUIFactory.instance().createList(user.getBottles()));
        return Paths.BOTTLE_LIST;
    }
}