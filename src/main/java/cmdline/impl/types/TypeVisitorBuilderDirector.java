package cmdline.impl.types;

import cmdline.api.BaseParams;
import cmdline.api.CommonParams;
import cmdline.api.VisitorBuilderDirector;
import com.google.inject.Inject;
import visitors.api.TypesVisitorBuilder;
import visitors.api.Visitor;


    final class TypeVisitorBuilderDirector implements VisitorBuilderDirector{
        @Inject
        @TypesVisitorBuilder.types
        private TypesVisitorBuilder typesVisitorBuilder;

        @Override
        public VisitorBuilderDirector buildVisitor(BaseParams params) {
            typesVisitorBuilder.setSizeUnit(((CommonParams)params).getSizeUnit());
            return this;
        }

        @Override
        public Visitor getVisitor() {
            return typesVisitorBuilder.build();
        }
    }


