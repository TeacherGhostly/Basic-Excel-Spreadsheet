package sheep.ui;


/**
 * A callback used whenever spreadsheet cell is updated.
 * This can be useful for triggering global events such as automatic saving.
 */
public interface OnChange {
    void change();
}
