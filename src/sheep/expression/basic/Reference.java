package sheep.expression.basic;

import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A reference to a given identifier.
 * The identifier may be of another cell or a built-in.
 */
public class Reference extends Expression {
    private String identifier;

    /**
     * Construct a new reference to an identifier.
     * @param identifier An identifier of a cell or a built-in.
     * Requires:
     * identifier != "", identifier != null
     */
    public Reference(String identifier) {
        if (identifier.isEmpty()) {
            throw new IllegalArgumentException("Requires: identifier != \"\", identifier != null");
        }
        this.identifier = identifier;
    }

    /**
     * Returns the identifier of the reference.
     * @return the identifier of the reference.
     */
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return "REFERENCE(" + this.identifier + ")";
    }

    @Override
    public boolean isReference() {
        return true;
    }

    /**
     * If two instances of reference are equal to each other.
     * Equality is defined by having the same identifier.
     * @param obj another instance to compare against.
     * @return true if the other object is a reference with the same identifier.
     */
    public boolean equals(Object obj) {
        Reference referenceObject = (Reference) obj;
        return Objects.equals(this.identifier, referenceObject.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }

    @Override
    public Set<String> dependencies() {
        return Collections.singleton(this.identifier);
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        if (!state.containsKey(this.identifier)) {
            return this;
        }
        return state.get(this.identifier).value(state);
    }

    @Override
    public long value()
            throws TypeError {
        throw new TypeError();
    }

    @Override
    public String render() {
        return this.identifier;
    }

}
