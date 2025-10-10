package pe.com.storedev.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignPermissionDTO {
    private Long id;
    private String name;
    private Boolean assigned;
}
