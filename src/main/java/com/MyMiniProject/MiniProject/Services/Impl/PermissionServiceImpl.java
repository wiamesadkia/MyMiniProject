package com.MyMiniProject.MiniProject.Services.Impl;

import com.MyMiniProject.MiniProject.Entities.Permission;
import com.MyMiniProject.MiniProject.Exeptions.ResourceDuplicatedException;
import com.MyMiniProject.MiniProject.Exeptions.ResourceNotFoundException;
import com.MyMiniProject.MiniProject.Mappers.PermissionMapper;
import com.MyMiniProject.MiniProject.Models.Requests.PermissionRequest;
import com.MyMiniProject.MiniProject.Models.Responses.PermissionResponse;
import com.MyMiniProject.MiniProject.Repositories.PermissionRepository;
import com.MyMiniProject.MiniProject.Services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse add(PermissionRequest request) {
        Optional<Permission> findPermission = permissionRepository.findByNamePermission(request.getNamePermission());
        if (findPermission.isPresent()) {
            throw new ResourceDuplicatedException("Permission", "id", findPermission.get().getId().toString());
        } else {
            Permission permission = new Permission();
            permission.setNamePermission(request.getNamePermission());
            permissionRepository.save(permission);
            return PermissionMapper.INSTANCE.entityToResponse(permission);
        }
    }

    @Override
    public List<PermissionResponse> getAll() {
        return PermissionMapper.INSTANCE.listToResponseList(permissionRepository.findAll());
    }

    @Override
    public PermissionResponse update(PermissionRequest request, Long id) {
        Optional<Permission> findPermission = permissionRepository.findById(id);
        if(!findPermission.isPresent()){
            throw  new ResourceNotFoundException("permission","id",id.toString());
        }
        Permission permission = findPermission.get();
        permission.setNamePermission(request.getNamePermission());
        permissionRepository.save(permission);
        return PermissionMapper.INSTANCE.entityToResponse(permission);
    }

    @Override
    public void delete(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("permission", "id", id.toString()));

        permissionRepository.delete(permission);

    }

    @Override
    public PermissionResponse get(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("permission", "id", id.toString()));
        return PermissionMapper.INSTANCE.entityToResponse(permissionRepository.findById(id).get());
    }
}
