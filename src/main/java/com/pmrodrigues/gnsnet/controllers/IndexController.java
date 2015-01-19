package com.pmrodrigues.gnsnet.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import com.pmrodrigues.gnsnet.annotations.Tiles;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

@Resource
public class IndexController {

    private static final Logger logging = Logger.getLogger(IndexController.class);

    @Get("/index.do")
    @Tiles(definition = "index")
    public void index() {
        logging.debug("carregando a página inicial");
    }

}
