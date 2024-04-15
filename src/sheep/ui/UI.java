package sheep.ui;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UI {
    protected final SheetView view;
    protected final SheetUpdate updater;
    protected final List<OnChange> changeCallbacks = new ArrayList<>();
    protected final Map<String, Feature> features = new HashMap<>();

    protected record Feature(String name, Perform action) {

    }

    public UI(SheetView view, SheetUpdate updater) {
        this.view = view;
        this.updater = updater;
    }

    public void onChange(OnChange callback) {
        changeCallbacks.add(callback);
    }

    public void addFeature(String identifier, String name, Perform action) {
        this.features.put(identifier, new Feature(name, action));
    }

    public abstract void render();
}
