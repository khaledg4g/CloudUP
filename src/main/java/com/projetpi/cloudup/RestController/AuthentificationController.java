package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.OTP.OtpValidationRequest;
import com.projetpi.cloudup.OTP.SmsService;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.entities.UserResponse;
import com.projetpi.cloudup.service.AuthentificationService;
import com.projetpi.cloudup.service.LogoutService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification")
public class AuthentificationController {
    private final AuthentificationService service;
    private final LogoutService logoutService;

    @PostMapping("/Register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUserbyPhoneNumber")
    public User getUserbyPhoneNumber(@RequestParam String phone){
        return service.getUserbyPhoneNumber(phone);
        }

        @PostMapping("/authenticate")
    public ResponseEntity<AuthentificationResponse> authenticate(
            @RequestBody @Valid AuthentificationRequest request) {

        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication) {
        try {
            logoutService.logout(request, response, authentication);

            return ResponseEntity.ok().body("User has been logged out successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during logout: " + e.getMessage());
        }
    }

    @PostMapping(value = "/image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadUserPhoto(
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication authentication
    ) {
        service.uploadUserPhoto(file, authentication);


        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable("idUser") Long idUser
    ) {
        return ResponseEntity.ok(service.findById(idUser));
    }

    @PostMapping("/updateUser")
    public Long updateUser(@RequestBody @Valid UpdateRequest request, Authentication authentication) {
        return service.updateUser(request, authentication);
    }

    @PostMapping("/updatePassword")
    public Long updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest, Authentication authentication) {
        return service.updatePassword(updatePasswordRequest, authentication);
    }

    @Autowired
    private SmsService smsService;

    @PostMapping("/sendUpdateConfirmationSMS")
    public String sendUpdateConfirmationSMS(Authentication authentication) {
        return smsService.sendUpdateConfirmationSMS(authentication);
    }

    @PostMapping("/sendUpdatePasswordSMS")
    public String sendUpdatePasswordSMS(@RequestBody UserUpdatePWDRequest user) {
        return smsService.sendUpdatePasswordSMS(user);
    }

    @PostMapping("/validateOtp")
    public boolean validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        return smsService.validateOtp(otpValidationRequest).equals("OTP is valid!");
    }

}
