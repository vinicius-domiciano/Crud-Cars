package gateway.procedure.impl;

import gateway.procedure.GatewayService;
import gateway.procedure.converter.CompanyConverter;
import models.CompanyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyGateway implements GatewayService<CompanyModel> {

    private final CompanyConverter converter;

    @Autowired
    public CompanyGateway(CompanyConverter converter) {
        this.converter = converter;
    }

    @Override
    public CompanyModel onExecute() {
        return null;
    }

}
