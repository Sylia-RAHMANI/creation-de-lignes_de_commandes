package cmdline.impl.common;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;

/**
 * Validates the size unit the user indicates (as a string) in the command line,
 * and converts its into an actual {@link SizeUnit}.
 */
public final class SizeUnitValidator implements IStringConverter<SizeUnit>, IParameterValidator {
    @Override
    public void validate(String name, String value) {
        try {
            SizeUnit.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new ParameterException("Parameter " + name + " must be one of :  " + Arrays.toString(SizeUnit.values()));
        }
    }

    @Override
    public SizeUnit convert(String value) {
        return SizeUnit.valueOf(value);
    }
}
