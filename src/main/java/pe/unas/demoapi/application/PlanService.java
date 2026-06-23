package pe.unas.demoapi.application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service

public class PlanService {
    @Value("${app.plan:BASICO}")
    private String plan;
    public int descuento(){
        return switch (plan.toUpperCase()){
            case "PREMIUN" -> 15;
            default -> 5;
        };

    }
}
