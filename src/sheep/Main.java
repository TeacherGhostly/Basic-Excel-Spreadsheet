package sheep;

import sheep.core.*;
import sheep.expression.CoreFactory;
import sheep.expression.ExpressionFactory;
import sheep.fun.Fibonacci;
import sheep.fun.FibonacciConstants;
import sheep.fun.FunException;
import sheep.fun.Pascal;
import sheep.parsing.Parser;
import sheep.parsing.SimpleParser;
import sheep.sheets.DisplaySheet;
import sheep.sheets.FixedSheet;
import sheep.sheets.Sheet;
import sheep.sheets.SheetBuilder;
import sheep.ui.graphical.GUI;
import sheep.ui.UI;

/**
 * Execute the SheeP spreadsheet program.
 * This file is for you to execute your program,
 * it will not be marked.
 */
public class Main {

    /**
     * Start the spreadsheet program.
     * @param args Parameters to the program, currently not supported.
     * @throws FunException If a pre-populator fails to insert an expression.
     */
    public static void main(String[] args) throws FunException {
        // Stage 0: Completion of core and FixedSheet.
//        FixedSheet simple = new FixedSheet();
  //      render(simple, simple);

        // Stage 1: Completion of constant expressions and DisplaySheet.
//        ExpressionFactory factory = new CoreFactory();
//        Parser parser = new SimpleParser(factory);

//        DisplaySheet sheet = new DisplaySheet(parser, factory.createEmpty(), 20, 10);
//        render(sheet, sheet);

        // Stage 1a: Pre-populate sheets (just for fun).
//        new FibonacciConstants(20).draw(sheet);

        // Stage 2: Completion of basic Sheet functionality.
        ExpressionFactory factory = new CoreFactory();
        Parser parser = new SimpleParser(factory);

        Sheet sheet = new SheetBuilder(parser, factory.createEmpty())
                .includeBuiltIn("life", factory.createConstant(42))
                .empty(20, 10);
        render(sheet, sheet);

        // Stage 2a: Pre-populate sheets (just for fun).
        new Fibonacci(20).draw(sheet);
        new Pascal(4, 2).draw(sheet);
    }

    private static UI render(SheetView view, SheetUpdate updater) {
        UI ui = new GUI(view, updater);
        ui.render();
        return ui;
    }
}
