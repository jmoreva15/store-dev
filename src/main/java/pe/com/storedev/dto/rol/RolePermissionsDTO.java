package pe.com.storedev.dto.rol;

import lombok.*;
import pe.com.storedev.dto.permission.AssignPermissionDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionsDTO {
    private Long id;
    private String name;
    private Boolean active;
    private List<AssignPermissionDTO> permissions;
}
