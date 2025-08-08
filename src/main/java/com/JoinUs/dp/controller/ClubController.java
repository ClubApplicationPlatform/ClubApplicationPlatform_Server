package com.JoinUs.dp.controller;

import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.service.ClubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public List<Club> searchClubs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer memberCount,
            @RequestParam(required = false) Boolean recruiting) {

        return clubService.searchClubs(name, memberCount, recruiting);
    }
}
