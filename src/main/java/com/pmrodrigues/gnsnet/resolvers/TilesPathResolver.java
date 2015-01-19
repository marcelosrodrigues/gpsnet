package com.pmrodrigues.gnsnet.resolvers;

import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.DefaultPathResolver;

import com.pmrodrigues.gnsnet.annotations.CRUD;
import com.pmrodrigues.gnsnet.annotations.Tiles;

import static java.lang.String.format;

@Component
public class TilesPathResolver extends DefaultPathResolver {

	public TilesPathResolver(FormatResolver resolver) {
		super(resolver);
	}

	@Override
	public String pathFor(ResourceMethod method) {

        if (method.getMethod().isAnnotationPresent(Tiles.class)) {
            return method.getMethod().getAnnotation(Tiles.class).definition();
        } else if ( method.getResource().getType().isAnnotationPresent(CRUD.class) ){
            return format("%s-template",method.getMethod().getName().toLowerCase());
        } else {
            return super.pathFor(method);
        }

	}

}
