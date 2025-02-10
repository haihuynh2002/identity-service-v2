package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.PermissionRequest;
import com.devteria.identity_service.dto.request.RoleRequest;
import com.devteria.identity_service.dto.response.ApiResponse;
import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.service.PermissionService;
import com.devteria.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> delete(@PathVariable  String role) {
        return ApiResponse.<Void>builder().build();
    }
}
