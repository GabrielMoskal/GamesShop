package gabriel.games.controller.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms/gamedescription")
public class GameDescriptionController {

    @GetMapping
    public String descriptionPage() {
        return "cms/game_description";
    }
}
