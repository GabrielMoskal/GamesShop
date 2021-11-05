package gabriel.games.controller.cms;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cms/gamedescription", produces = "application/json")
@CrossOrigin("*")
public class GameDescriptionController {

    @GetMapping
    public String description() {
        return "cms/game_description";
    }
}
