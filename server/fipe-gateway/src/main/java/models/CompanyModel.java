package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyModel {

    private String companyCode;
    private String name;

}
