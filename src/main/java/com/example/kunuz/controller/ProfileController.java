package com.example.kunuz.controller;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.service.ProfileService;
import com.example.kunuz.util.JwtUtil;
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
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid ProfileDTO dto,
                                             HttpServletRequest request) {
        int prtId = JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, prtId));
    }

    //    2. Update Profile (by only ADMIN)
    @PostMapping("/private/admin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") Integer id,
                                         @RequestBody @Valid ProfileDTO dto,
                                         HttpServletRequest request) {
        dto.setId(id);
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateByAdmin(dto));

    }

    //    3. Update Profile Detail (ANY) (Profile updates own details)
    @PostMapping("/private/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid ProfileDTO dto,
                                    HttpServletRequest request) {
        dto.setId(id);
        JwtUtil.checkToOwnerRequest(request, dto);
        return ResponseEntity.ok(profileService.update(dto));
    }

    // update with jwt
    @PostMapping("/private/update")
    public ResponseEntity<?> updateWithJwt(@RequestBody @Valid ProfileDTO dto,
                                           HttpServletRequest request) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTORequest(request);
        dto.setId(jwtDTO.getId());
        return ResponseEntity.ok(profileService.update(dto));
    }


    //    4. Profile List (ADMIN) (Pagination)
    @GetMapping("/private")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getAll(page, size));
    }


    //    5. Delete Profile By Id (ADMIN)
    @DeleteMapping("/private/{id}")
    public ResponseEntity<ProfileDTO> deleteById(@PathVariable("id") Integer id,
                                                 HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        profileService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // upload image to profile
    @PostMapping("/private/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTORequest(request);
        return ResponseEntity.ok().body(profileService.uploadImage(file, jwtDTO));
    }

    // filter profile by admin
    @PostMapping("/private/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
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
