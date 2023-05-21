package com.example.kunuz.controller;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.service.ProfileService;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/v1/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    //   1. Create profile (ADMIN) (can create MODERATOR,PUBLISHER))
    @PostMapping("/private/admin")
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto, SpringSecurityUtil.getProfileId()));
    }

    //    2. Update Profile (by only ADMIN)
    @PostMapping("/private/admin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") Integer id,
                                         @RequestBody @Valid ProfileDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(profileService.updateByAdmin(dto));

    }

    //    3. Update Profile Detail (ANY) (Profile updates own details)
    @PostMapping("/private/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid ProfileDTO dto,
                                    HttpServletRequest request) {
        dto.setId(id);
//        JwtUtil.checkToOwnerRequest(request, dto);
        return ResponseEntity.ok(profileService.update(dto));
    }

    // update with jwt
    @PostMapping("/private/update")
    public ResponseEntity<?> updateWithJwt(@RequestBody @Valid ProfileDTO dto) {
        dto.setId(SpringSecurityUtil.getProfileId());
        return ResponseEntity.ok(profileService.update(dto));
    }


    //    4. Profile List (ADMIN) (Pagination)
    @GetMapping("/private/admin")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(profileService.getAll(page, size));
    }


    //    5. Delete Profile By Id (ADMIN)
    @DeleteMapping("/private/admin/{id}")
    public ResponseEntity<ProfileDTO> deleteById(@PathVariable("id") Integer id) {
        profileService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // upload image to profile
    @PostMapping("/private/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(profileService.uploadImage(file, SpringSecurityUtil.getProfileId()));
    }

    // filter profile by admin
    @PostMapping("/private/admin/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto) {
        return ResponseEntity.ok().body(profileService.filter(dto));
    }


    private JwtDTO checkToOwner(String auth, ProfileDTO dto) {
        String[] arr = auth.split(" ");
        JwtDTO jwtDTO = JwtUtil.decode(arr[1]);
        if (jwtDTO.getId() == null) {
            throw new ItemNotFoundException("Item not found");
        }
        return jwtDTO;
    }


}
