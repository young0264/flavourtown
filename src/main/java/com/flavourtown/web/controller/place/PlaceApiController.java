package com.flavourtown.web.controller.place;

import com.flavourtown.domain.place.PlaceDto;
import com.flavourtown.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceApiController {
    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<String> savePlace(@RequestBody PlaceDto placeDto){
        log.info("placeDto = {}", placeDto);
        placeService.savePlace(placeDto);
        return ResponseEntity.ok().body("ok");
    }





}
