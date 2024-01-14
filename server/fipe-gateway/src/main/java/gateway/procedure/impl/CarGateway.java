package gateway.procedure.impl;

import gateway.procedure.GatewayService;
import models.CarModel;
import org.springframework.stereotype.Component;

@Component
public class CarGateway implements GatewayService<CarModel> {

    @Override
    public CarModel onExecute() {
        return null;
    }

}
