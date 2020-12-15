package cmdline.impl.common;

import cmdline.api.BaseParams;
import cmdline.api.CommonParams;
import cmdline.api.UnitParam;
import com.beust.jcommander.ParametersDelegate;

import java.nio.file.Path;
import java.util.List;

/**
 * Defines common parameters for command lines.
 * These parameters include base and unit parameters.
 */
public final class CommonParamsImpl implements CommonParams {

    @ParametersDelegate
    private final BaseParams baseParams = new BaseParamsImpl();

    @ParametersDelegate
    private final UnitParam unitParam = new UnitParamImpl();

    @Override
    public List<Path> getPaths() {
        return baseParams.getPaths();
    }

    @Override
    public SizeUnit getSizeUnit() {
        return unitParam.getSizeUnit();
    }

    @Override
    public boolean isHelp() {
        return baseParams.isHelp();
    }
}
