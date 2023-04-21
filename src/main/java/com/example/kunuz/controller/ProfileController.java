package com.example.kunuz.controller;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.ProfileDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.exps.MethodNotAllowedException;
import com.example.kunuz.service.ProfileService;
import com.example.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kunuz.util.JwtUtil.checkToAdmin;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    //   1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
    @PostMapping("/")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, jwtDTO.getId()));
    }
    //    2. Update Profile (by only ADMIN)
    @PostMapping("/admin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") Integer id,
                                         @RequestBody ProfileDTO dto,
                                         @RequestHeader("Authorization") String authorization) {
        dto.setId(id);
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateByAdmin(dto, jwtDTO));

    }

    //    3. Update Profile Detail (ANY) (Profile updates own details)
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ProfileDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        dto.setId(id);
        checkToOwner(auth, dto);
        return ResponseEntity.ok(profileService.update(dto));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateWithJwt(@RequestBody ProfileDTO dto,
                                           @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = checkToOwner(auth, dto);
        dto.setId(jwtDTO.getId());
        return ResponseEntity.ok(profileService.update(dto));
    }


    //    4. Profile List (ADMIN) (Pagination)
    @GetMapping("/")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestHeader("Authorization") String auth) {
        checkToAdmin(auth);
        return ResponseEntity.ok(profileService.getAll(page, size));
    }


    //    5. Delete Profile By Id (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileDTO> deleteById(@PathVariable("id") Integer id,
                                                 @RequestHeader("Authorization") String auth) {
        checkToAdmin(auth);
        profileService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private JwtDTO checkToOwner(String auth, ProfileDTO dto) {
        String[] arr = auth.split(" ");
        JwtDTO jwtDTO = JwtUtil.decode(arr[1]);
        if (jwtDTO.getId() == null) {
            throw new ItemNotFoundException("Item not found");
        }// else if (jwtDTO.getId().equals(dto.getId())) {
//            throw new MethodNotAllowedException("Not access");
//        }
        return jwtDTO;
    }


}
