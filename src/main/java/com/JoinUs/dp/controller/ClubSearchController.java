package com.JoinUs.dp.controller;

import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.service.ClubSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubSearchController {



    private final ClubSearchService clubSearchService;

    public ClubSearchController(ClubSearchService clubSearchService) {
        this.clubSearchService = clubSearchService;
    }


    //클럽 전체 조회
    @GetMapping
    public List<ClubSearch> searchClubs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer memberCount,
            @RequestParam(required = false) Boolean recruiting) {

        return clubSearchService.searchClubs(name, memberCount, recruiting);
    }

    //클럽  Q&A

}
