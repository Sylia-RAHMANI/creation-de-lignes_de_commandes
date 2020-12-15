package cmdline.impl.zip;

import cmdline.api.BaseParams;
import cmdline.api.VisitorBuilderDirector;
import com.google.inject.Inject;
import visitors.api.LangsVisitorBuilder;
import visitors.api.Visitor;
import visitors.api.ZipVisitorBuilder;


final class ZipVisitorBuilderDirector implements VisitorBuilderDirector {

    @Inject
    @LangsVisitorBuilder.Lags
    private ZipVisitorBuilder zipVisitorBuilder;

    @Override
    public VisitorBuilderDirector buildVisitor(BaseParams params) {
        zipVisitorBuilder.setMediaTypes(((ZipParamsImpl) params).getMediaTypes());
        zipVisitorBuilder.overwriteDestination(((ZipParamsImpl) params).isOverwriteDestination());
        return this;
    }

    @Override
    public Visitor getVisitor() {
        return zipVisitorBuilder.build();
    }
}
