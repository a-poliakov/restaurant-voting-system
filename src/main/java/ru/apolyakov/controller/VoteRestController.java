package ru.apolyakov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.apolyakov.model.Vote;
import ru.apolyakov.service.VoteService;

import java.net.URI;

import static ru.apolyakov.controller.VoteRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL)
public class VoteRestController {
    static final String REST_URL = "/api";

    private final VoteService voteService;

    @Autowired
    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @RequestMapping(value = "/vote/{restaurantId}", method = RequestMethod.POST)
    public Vote voteFor(@PathVariable("restaurantId")Integer restaurantId,
                                        @RequestParam(value = "rate", defaultValue = "0") Double rate){
        return voteService.voteFor(restaurantId, rate);
            /*Vote created = voteService.voteFor(restaurantId, rate);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "vote" + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);*/
    }
}
