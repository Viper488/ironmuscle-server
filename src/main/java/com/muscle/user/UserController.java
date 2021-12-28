package com.muscle.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muscle.user.dto.*;
import com.muscle.user.entity.IronUser;
import com.muscle.user.response.BadgeResponse;
import com.muscle.user.response.UserResponse;
import com.muscle.user.service.BadgeService;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.muscle.user.util.JwtUtil.generateErrorBody;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final BadgeService badgeService;

    /**
     * Refresh token
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("Pobiera nowy token dostępu")
    @GetMapping("/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String refresh_token = authorizationHeader.substring("Bearer ".length());
                    String username = jwtUtil.extractUsername(authorizationHeader);

                    UserDetails userDetails = userService.loadUserByUsername(username);
                    String access_token = jwtUtil.createToken(request, userDetails, 1000 * 60 * 60 * 24);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);

                } catch (Exception e) {
                    response.setStatus(UNAUTHORIZED.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), jwtUtil.generateErrorResponse(UNAUTHORIZED, e.getMessage()));
                }
            } else {
                throw new RuntimeException("Refresh token is missing");
            }
    }

    /**
     * Get all badges
     * @return
     */
    @ApiOperation("Pobiera wszystkie odznaki")
    @GetMapping("/badges")
    ResponseEntity<List<BadgeResponse>> getBadges() {
        return ResponseEntity.ok(badgeService.getBadges());
    }

    /**
     * Show user badges
     * @param header
     * @return
     */
    @ApiOperation("Pobiera odznaki użytkownika")
    @GetMapping("/user/badges")
    ResponseEntity<List<BadgeResponse>> getUserBadges(@RequestHeader("Authorization") String header) {
        return ResponseEntity.ok(badgeService.getUserBadges(header));
    }

    /**
     * Show info about currently logged user
     * @param header
     * @return
     */
    @ApiOperation("Pobiera informacje o zalogowanym użytkowniku")
    @GetMapping("/myself")
    ResponseEntity<UserResponse> getMyself(@RequestHeader("Authorization") String header) {
        return ResponseEntity.ok(userService.getMyself(header));
    }

    /**
     * Change email
     * @param header
     * @param changeUserDetails
     */
    @ApiOperation("Zmienia mail użytkownika")
    @PutMapping("/myself")
    ResponseEntity<?> changeEmail(@RequestHeader("Authorization") String header, @RequestBody ChangeUserDetails changeUserDetails) {
        try {
            userService.changeEmail(header, changeUserDetails);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Lock user
     * @param id
     * @param changeUserDetails
     */
    @ApiOperation("Blokuje dostęp do konta użytkonika")
    @PutMapping("/user/lock")
    ResponseEntity<?> lockUser(@RequestParam Long id, @RequestBody ChangeUserDetails changeUserDetails) {
        try {
            userService.lockUser(id, changeUserDetails);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Send reset password email
     * @param email
     */
    @ApiOperation("Wysyła zapytanie o link do zmiany hasła")
    @PostMapping("/password/reset")
    ResponseEntity<?> requestPasswordReset(@RequestParam("email") String email) {
        try {
            userService.requestPasswordChange(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Change password
     * @param resetPasswordDto
     */
    @ApiOperation("Zmnienia zapomniane hasło na nowe")
    @PutMapping("/password/reset")
    ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            userService.resetPassword(resetPasswordDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        } catch (Exception e) {
            return ResponseEntity.status(UNAUTHORIZED).body(generateErrorBody(401, e));
        }
    }

    /**
     * Change password when logged
     * @param header
     * @param changePasswordDto
     */
    @ApiOperation("Zmienia hasło, przy użyciu starego hasła")
    @PutMapping("/password/change")
    ResponseEntity<?> changePassword(@RequestHeader("Authorization") String header, @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            userService.changePassword(header, changePasswordDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Change user icon
     * @param header
     * @param file
     * @return
     */
    @ApiOperation("Zmienia zdjęcie profilowe uzytkownika")
    @PutMapping("/user/icon")
    ResponseEntity<?> changeUserImage(@RequestHeader("Authorization") String header, @RequestBody MultipartFile file) {
        try {
            userService.changeUserIcon(header, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateErrorBody(400, e));
        }
    }

    /**
     * Get all users
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("Pobiera listę użytkowników")
    @GetMapping("/users")
    ResponseEntity<Map<String, Object>> getUsers(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "100") Integer size,
                                 @RequestParam(defaultValue = "") String query) {
        Pageable paging = PageRequest.of(page, size);
        Page<IronUser> userPage = userService.getPaginatedUsers(paging, query);

        List<IronUserImageDto> usersList = userPage.getContent()
                .stream().map(IronUser::dtoImage).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("users", usersList);
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
