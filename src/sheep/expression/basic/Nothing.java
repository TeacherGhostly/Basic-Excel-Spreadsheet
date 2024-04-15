package sheep.expression.basic;

import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * An empty expression.
 */
public class Nothing extends Expression {

    @Override
    public Set<String> dependencies() {
        return Collections.emptySet();
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        return this;
    }

    @Override
    public long value() throws TypeError {
        throw new TypeError();
    }

    @Override
    public String render() {
        return "";
    }

    @Override
    public String toString() {
        return "NOTHING";
    }
}
