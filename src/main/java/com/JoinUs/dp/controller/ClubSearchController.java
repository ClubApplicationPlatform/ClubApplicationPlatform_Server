package com.JoinUs.dp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.service.ClubSearchService;

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
