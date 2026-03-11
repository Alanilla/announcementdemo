package com.example.announcementdemo.controller;

import com.example.announcementdemo.entity.Announcement;
import com.example.announcementdemo.repository.AnnouncementRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/")
    public String viewAnnouncement(Model model) {
        Announcement announcement = announcementRepository.findById(1).orElseGet(() -> {
            Announcement a = new Announcement();
            a.setTitle("System Notice");
            a.setContent("Welcome to the announcement demo app.");
            return announcementRepository.save(a);
        });

        model.addAttribute("announcement", announcement);
        model.addAttribute("pageNote", "This page displays the current announcement.");
        return "view";
    }

    @GetMapping("/edit")
    public String editAnnouncement(Model model) {
        Announcement announcement = announcementRepository.findById(1).orElseGet(() -> {
            Announcement a = new Announcement();
            a.setTitle("System Notice");
            a.setContent("Welcome to the announcement demo app.");
            return announcementRepository.save(a);
        });

        model.addAttribute("announcement", announcement);
        return "edit";
    }

    @PostMapping("/save")
    public String saveAnnouncement(@ModelAttribute Announcement announcement) {
        Announcement existing = announcementRepository.findById(1).orElse(new Announcement());

        existing.setTitle(announcement.getTitle());
        existing.setContent(announcement.getContent());

        if (existing.getId() == null) {
            existing.setId(1);
        }

        announcementRepository.save(existing);
        return "redirect:/";
    }
}