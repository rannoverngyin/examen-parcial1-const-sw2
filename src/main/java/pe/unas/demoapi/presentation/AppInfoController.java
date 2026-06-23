package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.AppPropierties;

@RestController
public class AppInfoController {

    private final AppPropierties props;

    public AppInfoController(AppPropierties props) {
        this.props = props;
    }

    @GetMapping("/config/info")
    public String info() {
        return props.getNombre() + " - " + props.getVersion() + " - " + props.getInstitucion();
    }
}