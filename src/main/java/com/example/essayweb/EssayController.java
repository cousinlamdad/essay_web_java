package com.example.essayweb;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EssayController {

    private final EssayRepository essays;

    public EssayController(EssayRepository essays) {
        this.essays = essays;
    }

    @GetMapping("/")
    public String input() {
        return "input";
    }

    @PostMapping("/essays")
    public String create(@RequestParam(required = false) String title,
                         @RequestParam(required = false) String content,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (title == null || title.isBlank()) {
            model.addAttribute("title", title);
            model.addAttribute("content", content);
            model.addAttribute("error", "input.error");
            return "input";
        }
        essays.save(new Essay(title, content));
        redirectAttributes.addFlashAttribute("status", "input.saved");
        return "redirect:/";
    }

    @GetMapping("/essays")
    public String list(Model model) {
        model.addAttribute("essays", essays.findAllByOrderByCreatedAtDesc());
        return "list";
    }

    @GetMapping("/essays/{id}")
    public String detail(@PathVariable int id, Model model) {
        Essay essay = essays.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Essay not found"));
        model.addAttribute("essay", essay);
        return "detail";
    }
}
