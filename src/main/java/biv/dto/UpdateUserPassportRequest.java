package biv.dto;

import biv.domain.PassportRu;
import biv.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPassportRequest {

    private UserAccount user;
    private PassportRu passport;
}
