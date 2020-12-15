package cmdline.impl.langs;

import cmdline.api.BaseParams;
import cmdline.api.VisitorBuilderDirector;
import com.google.inject.Inject;
import visitors.api.LangsVisitorBuilder;
import visitors.api.Visitor;


    final class LangsVisitorBuilderDirector implements VisitorBuilderDirector {

        @Inject
        @LangsVisitorBuilder.Lags
        private LangsVisitorBuilder langsVisitorBuilder;

        @Override
        public VisitorBuilderDirector buildVisitor(BaseParams baseParams) {
            langsVisitorBuilder.setMediaTypes(((LangsParamImpl) baseParams).getMediaTypes());
            langsVisitorBuilder.setSizeUnit(((LangsParamImpl) baseParams).getSizeUnit());
            return this;
        }

        @Override
        public Visitor getVisitor() {
            return langsVisitorBuilder.build();
        }
    }


