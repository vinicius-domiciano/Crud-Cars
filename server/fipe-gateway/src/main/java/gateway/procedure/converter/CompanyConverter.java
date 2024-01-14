package gateway.procedure.converter;

import gateway.procedure.dto.CompanyDto;
import models.CompanyModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter implements Converter<CompanyDto, CompanyModel> {

    @Override
    public CompanyModel convert(CompanyDto companyDto) {
        return CompanyModel.builder()
                .name(companyDto.getNome())
                .companyCode(companyDto.getCodigo())
                .build();
    }

}
