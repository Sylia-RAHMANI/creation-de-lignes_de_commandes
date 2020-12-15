package filesystem.impl;

import filesystem.api.Component;
import filesystem.api.ComponentType;

public final class FSFactory {

    private static final FSFactory INSTANCE = new FSFactory();
    private FSFactory(){}

    public static FSFactory getInstance(){return INSTANCE;}

    public Component createComponent(ComponentType type, java.io.File wrapped){
        if (type == ComponentType.DIRECTORY) {
            return new Directory(wrapped);
        }
        return new File(wrapped);
    }
}
