package com.muates.springbootbookstore.controller;

import com.muates.springbootbookstore.domain.Gender;
import com.muates.springbootbookstore.dto.request.GenderRequest;
import com.muates.springbootbookstore.dto.response.GenderResponse;
import com.muates.springbootbookstore.service.GenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genders")
public class GenderController {

    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<GenderResponse>> getAllGenders() {
        List<Gender> genderList = genderService.getAllGenders();
        List<GenderResponse> genderResponses = genderList.stream().map(gender -> convertToGenderResponse(gender)).collect(Collectors.toList());
        return ResponseEntity.ok(genderResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderResponse> getGenderById(@PathVariable Long id) {
        GenderResponse genderResponse = convertToGenderResponse(genderService.getGenderById(id));
        return ResponseEntity.ok(genderResponse);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<GenderResponse> saveGender(@Valid @RequestBody GenderRequest genderRequest) {
        Gender savedGender = genderService.saveGender(convertToGender(genderRequest));
        return ResponseEntity.ok(convertToGenderResponse(savedGender));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenderById(@PathVariable Long id, @Valid @RequestBody GenderRequest genderRequest) {
        Gender gender = convertToGender(genderRequest);
        genderService.updateGenderById(id, gender);
        return ResponseEntity.ok("Gender is updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenderById(@PathVariable Long id) {
        genderService.deleteGenderById(id);
        return ResponseEntity.ok("Gender is deleted");
    }

    private Gender convertToGender(GenderRequest genderRequest) {
        return Gender.builder()
                .id(genderRequest.getId())
                .gender(genderRequest.getGender())
                .build();
    }

    private GenderResponse convertToGenderResponse(Gender gender) {
        return GenderResponse.builder()
                .id(gender.getId())
                .gender(gender.getGender())
                .build();
    }
}
