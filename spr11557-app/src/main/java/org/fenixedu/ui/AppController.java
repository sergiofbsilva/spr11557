package org.fenixedu.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/spaces")
public class AppController {

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "home";
    }

}
