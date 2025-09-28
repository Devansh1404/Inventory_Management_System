package com.ims.Inventory.controller;

import com.ims.Inventory.model.Location;
import com.ims.Inventory.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Display the list of all locations
    @GetMapping("/locations")
    public String viewLocationsPage(Model model) {
        model.addAttribute("listLocations", locationService.getAllLocations());
        return "locations"; // We will create this HTML file next
    }

    // Show the form to add a new location
    @GetMapping("/locations/new")
    public String showNewLocationForm(Model model) {
        model.addAttribute("location", new Location());
        model.addAttribute("pageTitle", "Add New Location");
        return "location_form"; // We will create this HTML file next
    }

    // Process the form to save a location
    @PostMapping("/locations/save")
    public String saveLocation(@ModelAttribute("location") Location location, RedirectAttributes redirectAttributes) {
        locationService.saveLocation(location);
        redirectAttributes.addFlashAttribute("successMessage", "Location saved successfully!");
        return "redirect:/locations";
    }

    // Show the form to edit a location
    @GetMapping("/locations/edit/{id}")
    public String showEditLocationForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Location location = locationService.getLocationById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + id));
            model.addAttribute("location", location);
            model.addAttribute("pageTitle", "Edit Location");
            return "location_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/locations";
        }
    }

    // Delete a location
    @GetMapping("/locations/delete/{id}")
    public String deleteLocation(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            locationService.deleteLocation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Location deleted successfully!");
        } catch (Exception e) {
            // This will catch errors if you try to delete a location that still has stock
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting location: " + e.getMessage());
        }
        return "redirect:/locations";
    }
}