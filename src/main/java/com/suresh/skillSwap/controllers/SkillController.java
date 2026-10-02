package com.suresh.skillSwap.controllers;

import com.suresh.skillSwap.dto.SkillPatchRequest;
import com.suresh.skillSwap.dto.SkillRequest;
import com.suresh.skillSwap.dto.SkillResponse;
import com.suresh.skillSwap.servicies.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    final private SkillService skillService;

    public SkillController(SkillService skillService){
        this.skillService = skillService;
    }

    /* ========================> SKILL CONTROLLER LAYER <====================== */
    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest skillRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.createSkill(skillRequest));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getSkills(){
        return ResponseEntity.ok(skillService.getSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkill(@PathVariable(name = "id") Long skillId){
        return ResponseEntity.ok(skillService.getSkill(skillId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable(name = "id") Long skillId,
                                                     @Valid @RequestBody SkillRequest skillRequest){
        return ResponseEntity.ok(skillService.updateSkill(skillId, skillRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SkillResponse> partialUpdateSkill(@PathVariable(name = "id") Long skillId,
                                                            @Valid @RequestBody SkillPatchRequest skillPatchRequest){
        return ResponseEntity.ok(skillService.partialUpdateSkill(skillId, skillPatchRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable(name = "id") Long skillId){
        skillService.deleteSkill(skillId);
        return ResponseEntity.noContent().build();
    }
}
